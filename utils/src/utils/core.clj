(ns utils.core
  (:require [clojure.java.jdbc :as jdbc])
  (:gen-class))

(def db {:classname "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname "../tasks.db"})

(defn create-table []
  (jdbc/execute! db
       ["CREATE TABLE IF NOT EXISTS
                tasks (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  start_page TEXT NOT NULL,
                  end_page TEXT NOT NULL,
                  status TEXT NOT NULL DEFAULT 'tbd'
                  )"]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
