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
