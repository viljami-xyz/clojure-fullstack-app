(defproject jagers "0.1.0-SNAPSHOT"
  :description "Simple http server, with scraping and templating"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [ring/ring-core "1.11.0"]
                 [ring/ring-json "0.5.1"]
                 [ring/ring-devel "1.11.0"]
                 [ring/ring-jetty-adapter "1.11.0"]
                 [ring-refresh/ring-refresh "0.1.3"]
                 [compojure "1.7.0"]
                 [selmer "1.12.50"]
                 [clj-http "3.12.3"]
                 [org.jsoup/jsoup "1.17.2"]
                 [org.slf4j/slf4j-simple "2.0.7"]
                 [cheshire "5.11.0"]]

  :plugins [[lein-ring "0.12.6"]]

  :ring {:handler jagers.backend/app}  ;; Update if needed

  :aliases {"run-m" ["trampoline" "run" "-m" "jagers.backend"]
            "run-x" ["trampoline" "exec" "-ns" "jagers.backend" "-e" "(greet {:name \"Clojure\"})"]
            "test" ["test"]}

  :main jagers.core
  :source-paths ["src"]
  :profiles {:dev {:main jagers.core/-dev-main
                   :dependencies [[org.clojure/test.check "1.1.1"]]
                   :source-paths ["src" "resources"]
                   :test-paths ["test"]}

             :build {:dependencies [[io.github.clojure/tools.build "0.10.5"]]
                     :source-paths ["src"]}})

