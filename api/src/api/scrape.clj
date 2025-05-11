(ns api.scrape
  (:require [clj-http.client :as http]
            [clojure.string :as str])
  (:import [org.jsoup Jsoup]))

(defn fetch-wiki-page [title]
  (let [url (str "https://en.wikipedia.org/wiki/" title)
        response (http/get url {:cookie-policy :none})]
    (if (= 200 (:status response))
      (:body response)
      (throw (Exception. (str "Failed to fetch page: " title))))))

(defn fetch-wiki-page-by-id [id]
  (let [url (str "https://en.wikipedia.org/?curid=" id)
        response (http/get url {:cookie-policy :none})]
    (if (= 200 (:status response))
      (:body response)
      (throw (Exception. (str "Failed to fetch page by ID: " id))))))

(defn parse-wiki-page [html]
  (let [doc (Jsoup/parse html "UTF-8")
        title (.title doc)
        paragraphs (map #(.text %) (.select doc "p"))]
    {:title title
     :content paragraphs}))

(defn remove-duplicate-wiki-links [items]
  (let [seen (atom #{})]
    (filter (fn [item]
              (let [href (:href item)]
                (if (contains? @seen href)
                  false
                  (do (swap! seen conj href)
                      true))))
            items)))

(defn get-wiki-links [html]
  (let [doc (Jsoup/parse html "UTF-8")
        links (->> (.select doc "p a[href]")
                   (map #(hash-map :text (.text %) :href (.attr % "href")))
                   (filter #(str/starts-with? (:href %) "/wiki/"))
                   (remove-duplicate-wiki-links))]
    links))

(defn parse-wiki-box-picture [html]
  (let [doc (Jsoup/parse html "UTF-8")
        box-image (.select doc ".infobox img")]
    (if (not-empty box-image)
      (let [src (.attr (first box-image) "src")]
        (str "https:" src))
      nil)))

(defn parse-wiki-title [html]
  (let [doc (Jsoup/parse html "UTF-8")
        title (.title doc)]
    (if (str/blank? title)
      (throw (Exception. "No title found"))
      (str/replace title #" - Wikipedia" ""))))

(defn get-first-paragraph [html]
  ;; Extract the first non-empty and not inside a table paragraph 
  (let [no-tables (str/replace html #"(?s)<table.*?>.*?</table>" "")
        doc (parse-wiki-page no-tables)
        paragraphs (filter #(not (str/blank? %)) (:content doc))
        first-paragraph (first paragraphs)
        no-citations (str/replace first-paragraph #"\[.*?\]" "")]
    (if (str/blank? no-citations)
      (throw (Exception. "No valid first paragraph found"))
      (str/replace no-citations #"(?s)<.*?>" ""))))
