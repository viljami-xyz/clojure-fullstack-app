(ns api.core 
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.refresh :as refresh]
            [api.routes :refer [app]]))


(defn -main []
  (jetty/run-jetty (-> #'app
                       wrap-reload 
                       wrap-params)
                   {:port 3001 :join? false}))

(defn -dev-main []
  (jetty/run-jetty (-> #'app
                       (wrap-reload {:dirs ["src" "resources"]})
                       (refresh/wrap-refresh #'app)
                       wrap-params)
                   {:port 3001 :join? false}))
