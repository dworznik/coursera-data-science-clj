(ns coursera_data_science_clj.file
  (:use [clojure.java.io :only [reader writer]])
  (:use [clojure.string :as str :only [split join]])
  (:use [clojure.data.json :as json]))

(defn tweet-text
  "Extracts tweet's text from Twitter stream JSON"
  [line]
  ((json/read-str line) "text"))

(defn parse-int
  "Extracts and parses an integer from the string"
  [s]
  (Integer. (re-find #"\d+" s)))

(defn- read-dict
  [filename]
  (with-open [rdr (reader filename)]
    (flatten (doall (map #(let [[w s] (split % #"\t")] [w (parse-int s)]) (line-seq rdr))))
    ))

(defn load-sentiments
  "Loads sentiment dictionary from a file into a hashmap"
  [filename] (apply hash-map (read-dict filename)))
