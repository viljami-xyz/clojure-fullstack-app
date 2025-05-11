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
                  start_page INTEGER NOT NULL,
                  end_page INTEGER NOT NULL,
                  status TEXT NOT NULL DEFAULT 'tbd'
                  )"]))

(defn insert-task [start-page end-page]
  (jdbc/insert! db
       "tasks"
       {:start_page start-page
        :end_page end-page}))

