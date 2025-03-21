(ns jagers.routes
  (:require [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [cheshire.core :as json]
            [selmer.parser :as selmer]
            [jagers.templates :as jtemp]
            [jagers.scrape :as scrape]))

(defroutes app
  (GET "/greet" []
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string {:message "Hello, World"})})
  (GET "/html" req
    (let [user (get-in req [:query-params "user"] "Bobby")]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (jtemp/render-template "greet.html" {:name user})}))
  (GET "/wiki-json/:title" [title]
    (let [html (scrape/fetch-wiki-page title)
          page (scrape/parse-wiki-page html)]
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body (json/generate-string page)}))

  (GET "/wiki/:title" [title]
    (let [html (scrape/fetch-wiki-page title)
          page (scrape/parse-wiki-page html)
          js (jtemp/render-template "wiki.html" {:title (:title page)
                                                 :content (:content page)})]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body js})))


