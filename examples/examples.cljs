(ns cljs-asynchronize.example
  (:require [cljs.nodejs :as node]
            [cljs.core.async])
  (:require-macros [cljs-asynchronize.macros :as dm :refer [asynchronize]]
                   [cljs.core.async.macros :as am :refer [go]]))

(def fs (node/require "fs"))

(defn print-files [& files]
  (asynchronize
    (def contents (map #(.readFile fs % "utf8" ...) files))
    (doseq [content contents] (console/log content))))

(defn print-three-files [f1 f2 f3]
  (asynchronize
    (def f1-content (.readFile fs f1 "utf8" ...))
    (def f2-content (.readFile fs f2 "utf8" ...))
    (def f3-content (.readFile fs f3 "utf8" ...))
    (console/log f1-content)
    (console/log f2-content)
    (console/log f3-content)))

(defn test-nested [file]
  (asynchronize
    (def contents (.readFile fs (.readFile fs (.readFile fs file "utf8" ...) "utf8" ...) "utf8" ...))
    (console/log contents)))

(defn test []
  (print-three-files "file1.txt" "file2.txt" "file3.txt")
  (test-nested "file1.txt"))

(set! *main-cli-fn* test)