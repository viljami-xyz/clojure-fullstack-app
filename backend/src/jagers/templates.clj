(ns jagers.templates
  (:require [selmer.parser :as selmer]))

;; Ensure that the template is in the resources directory
(selmer/set-resource-path! "templates")
(selmer.parser/cache-off!)

(defn render-template [template data]
  (let [output (selmer/render-file template data)]
    (println (take 5 (:content data)))
    output))
