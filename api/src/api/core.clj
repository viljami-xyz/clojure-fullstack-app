(ns api.core 
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.refresh :as refresh]
            [ring.middleware.resource :refer [wrap-resource]]
            [api.routes :refer [app]]))


(defn -main []
  (jetty/run-jetty (-> #'app
                       wrap-reload 
                       wrap-params)
                   {:port 3001 :join? false}))

(defn -dev-main []
  (jetty/run-jetty (-> #'app
                       (wrap-reload {:dirs ["src" "resources"]})
                       (wrap-resource "public")
                       (refresh/wrap-refresh #'app)
                       wrap-params)
                   {:port 3001 :join? false}))
