(ns jagers.routes
  (:require [compojure.core :refer [defroutes GET POST PUT]]
            [cheshire.core :as json]
            [selmer.parser :as selmer]
            [jagers.templates :as jtemp]
            [jagers.scrape :as scrape]))

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
  (GET "/wiki/:title" [title]
    (let [html (scrape/fetch-wiki-page title)
          page (scrape/parse-wiki-page html)
          js (jtemp/render-template "wiki.html" {:title "Bobby"
                                                 ;;(:title page)
                                                 :content (:content page)})]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body js})))


