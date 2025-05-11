(ns api.routes
  (:require [compojure.core :refer [defroutes GET POST]]
            [cheshire.core :as json]
            [api.templates :as jtemp]
            [api.scrape :as scrape]
            [clojure.string :as str]))

(defroutes app
  (GET "/favicon.ico" []
    {:status 404})
  (GET "/" []
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (jtemp/render-template "base.html" {})})
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
          links (scrape/get-wiki-links html)]
      {:status 200
       :headers {"Content-Type" "application/json"}
       :body (json/generate-string links)}))
  (GET "/wiki-links-by-id" req 
       (let [id (get-in req [:query-params "id"] "1")
             html (scrape/fetch-wiki-page-by-id id)
             links (scrape/get-wiki-links html)]
         {:status 200
          :headers {"Content-Type" "application/json"}
          :body (json/generate-string links)}))
  (POST "/task" req 
        (let [body (:body req)]
          )
          {:status 200
           :headers {"Content-Type" "application/json"}
           :body (json/generate-string {:task req})})
  (GET "/wiki-by-id" req 
       (let [id (get-in req [:query-params "id"] "1")
             html (scrape/fetch-wiki-page-by-id id)
             picture  (scrape/parse-wiki-box-picture html)
             first-paragraph (scrape/get-first-paragraph html)
             ;; first word of the first-paragraph
             title (scrape/parse-wiki-title html)
             dense-page {
                         :title title
                         :picture picture
                         :description first-paragraph
                         }
             ]
         {:status 200
          :headers {"Content-Type" "application/json"}
          :body (json/generate-string dense-page)}))
  )

