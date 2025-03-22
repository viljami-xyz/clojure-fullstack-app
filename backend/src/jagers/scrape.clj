(ns jagers.scrape
  (:require [clj-http.client :as http])
  (:import [org.jsoup Jsoup]))

(defn fetch-wiki-page [title]
  (let [url (str "https://en.wikipedia.org/wiki/" title)
        response (http/get url {:as :text :charset "UTF-8" :cookie-policy :none})]
    (if (= 200 (:status response))
      (:body response)
      (throw (Exception. (str "Failed to fetch page: " title))))))

(defn parse-wiki-page [html]
  (let [doc (Jsoup/parse html )
        title (.title doc)
        paragraphs (map #(.text %) (.select doc "p"))]
    {:title title
     :content paragraphs}))

