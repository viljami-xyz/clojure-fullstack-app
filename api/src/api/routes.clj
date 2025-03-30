(ns api.routes
  (:require [compojure.core :refer [defroutes GET POST PUT]]
            [cheshire.core :as json]
            [api.templates :as jtemp]
            [api.scrape :as scrape]))

(defroutes app
  (GET "/favicon.ico" []
    {:status 404})
  (GET "/greet" []
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string {:message "Hello, World"})})
  (GET "/html" req
    (let [user (get-in req [:query-params "user"] "Bobby")]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (jtemp/render-template "greet.html" {:name user})}))
  (GET "/wiki" req
    (let [search (get-in req [:query-params "search"] "Clojure")
          html (scrape/fetch-wiki-page search)
          page (scrape/parse-wiki-page html)
          js (jtemp/render-template "wiki.html" {:title (:title page)
                                                 :content (:content page)})]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body js}))
  (GET "/wiki-links" req
       (let [search (get-in req [:query-params "search"] "Clojure")
             html (scrape/fetch-wiki-page search)
             links (scrape/get-wiki-links html)
             ]
         {:status 200
          :headers {"Content-Type" "application/json"}
          :body (json/generate-string links)}))
  
  )

