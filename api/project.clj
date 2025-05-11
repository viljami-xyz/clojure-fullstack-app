(defproject api "0.1.0-SNAPSHOT"
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
                 [utils "0.1.0-SNAPSHOT"]
                 [cheshire "5.11.0"]]

  :plugins [[lein-ring "0.12.6"]]

  :ring {:handler api.api/app}  ;; Update if needed

  :aliases {"run-m" ["trampoline" "run" "-m" "api.api"]
            "run-x" ["trampoline" "exec" "-ns" "api.api" "-e" "(greet {:name \"Clojure\"})"]
            "test" ["test"]}

  :main api.core
  :source-paths ["src/api", "../utils/src"]
  :profiles {:dev {:main api.core/-dev-main
                   :dependencies [[org.clojure/test.check "1.1.1"]]
                   :source-paths ["src" "resources"]
                   :test-paths ["test"]}

             :build {:dependencies [[io.github.clojure/tools.build "0.10.5"]]
                     :source-paths ["src"]}})

