(ns jagers.templates
  (:require [selmer.parser :as selmer]))

;; Ensure that the template is in the resources directory
(selmer/set-resource-path! "templates")
(selmer.parser/cache-off!)

(defn render-template [template data]
  (selmer/render-file template data))
