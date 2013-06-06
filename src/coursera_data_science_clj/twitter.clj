(ns coursera_data_science_clj.twitter
  (:use [clojure.java.io :only [reader writer]])
  (:use [coursera_data_science_clj.file :only [parse-int]])
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [oauth.client :as oauth]
            [clj-yaml.core :as yaml]))


(def oauth-conf (yaml/parse-string (slurp ".oauth.yml")))

(def consumer (oauth/make-consumer (:consumer-key oauth-conf)
                (:consumer-secret oauth-conf)
                "http://api.twitter.com/oauth/request_token"
                "http://api.twitter.com/oauth/access_token"
                "http://api.twitter.com/oauth/authorize"
                :hmac-sha1 ))

(def stream-url "https://stream.twitter.com/1/statuses/sample.json")

(def credentials (oauth/credentials consumer
                   (:access-token oauth-conf)
                   (:access-token-secret oauth-conf)
                   :GET stream-url))

(println credentials)

(def res (http/get stream-url {:as :stream :query-params credentials}))

(defn writer-with-cond [cond fnext]
  (letfn [(write [n line lines wrt]
            (if (cond n)
              (doseq []
                (.write wrt (str line "\n"))
                (recur (fnext n) (first lines) (rest lines) wrt))))]
    (fn [n reader filename]
      (let [lines (line-seq reader)]
        (with-open [w (writer filename)]
          (write n (first lines) lines w)
          )))
    )
  )

(def write-n-lines (writer-with-cond #(> % 0) dec))

(defn write-for-secs [n reader filename]
                      (let [t (+ (* n 1000) (.getTime (java.util.Date.)))]
                        ((writer-with-cond #(<= % t) (fn [x] (.getTime (java.util.Date.)))) t reader filename)))


(defn -main [secs output]
  (write-for-secs (parse-int secs) (reader (res :body )) output))



