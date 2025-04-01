(ns worker.core
  (:require [utils.core :as utils])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (utils/create-table)
  (println "Hello, World!"))
