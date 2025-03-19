(ns jagers.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [jagers.routes :refer [app]]))

(defn -main []
  (jetty/run-jetty (-> app
                       wrap-reload
                       wrap-params)
                   {:port 3001 :join? false}))
