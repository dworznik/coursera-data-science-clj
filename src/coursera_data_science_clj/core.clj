(ns coursera_data_science_clj.core
  (:use [clojure.java.io :only [reader writer]])
  (:use [clojure.string :as str :only [split join]])
  (:use coursera_data_science_clj.sentiment)
  (:use coursera_data_science_clj.file))


(defn exec-tweet-sentiments [sentiments input output]
  (let [tweet-sentiment (tweet-sentiment-builder (load-sentiments sentiments))]
    (with-open [rdr (reader input) wrt (writer output)]
      (doseq [line (line-seq rdr)]
        (.write wrt (str (tweet-sentiment (tweet-text line)) "\n"))))))

(defn create-term-sentiment-dict [sentiments tweets]
  (with-open [rdr (reader tweets)]
    (term-sentiment-dict (map tweet-text (line-seq rdr)) (load-sentiments sentiments))))

(defn exec-term-sentiments [sentiments tweets output]
  (let [dict (create-term-sentiment-dict sentiments tweets)]
    (with-open [wrt (writer output)]
      (doseq [[term score] dict]
        (.write wrt (str term ":" score "\n"))))))


(defn -main [job dict tweets output]
  (case job
    "tweet-sentiments" (exec-tweet-sentiments dict tweets output)
    "term-sentiments" (exec-term-sentiments dict tweets output)
    ))
