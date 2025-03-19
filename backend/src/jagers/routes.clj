(ns jagers.routes
  (:require [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [cheshire.core :as json]
            [selmer.parser :as selmer]
            [jagers.templates :as jtemp]))

(defroutes app
  (GET "/greet" []
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string {:message "Hello, World"})})
  (GET "/html" req
    (let [user (get-in req [:query-params "user"] "Bobby")]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (jtemp/render-template "greet.html" {:name user})})))


