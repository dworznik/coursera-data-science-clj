(ns coursera_data_science_clj.sentiment
  (:use [clojure.string :as str :only [split join]]))

(defn tweet-words
  "Splits the tweet into lowercase words, discards non-word characters"
  [text]
  (if (not (empty? text))
    (map str/lower-case (str/split text #"\W+"))))

(defn tweet-sentiment-builder
  "Creates a tweet sentiment function with the sentiment dictionary"
  [sentiments]
  (fn [text]
    (apply + (map #(sentiments % 0) (tweet-words text)))))


(defn tweet-sentiment-ratio
  "Calculates sentiment ratio pos/neg"
  [tweet sentiments]
  (let [word-sentiments (map #(sentiments % 0) (tweet-words tweet))]
    (let [{pos 1 neg -1} (group-by #(compare % 0) word-sentiments)]
      (let [p (count pos) n (count neg)]
        (if (= 0 n) p (/ (float p) n))))))

(defn update-scores
  "Updates sentiment score predictions based on the tweet sentiment ratio"
  [tweet scores sentiments]
  (let [sentiment-ratio (tweet-sentiment-ratio tweet sentiments) words (tweet-words tweet)]
    (letfn [(update [scores words]
              (if (empty? words) scores
                (recur (if (contains? sentiments (first words)) scores (merge-with + scores {(first words) sentiment-ratio})) (rest words))))]
      (update scores words)
      )))


(defn term-sentiment-builder [tweets sentiments]
  "Creates a term sentiment function based on sentiment dictionary and tweet set"
  (letfn [(builder [scores tweets]
            (if (empty? tweets) (fn [term] (sentiments term (scores term 0)))
              (recur (update-scores (first tweets) scores sentiments) (rest tweets))))]
    (builder {} tweets)))

(defn term-sentiment-dict [tweets sentiments]
  "Creates term sentiment dictionary based on sentiment dictionary and tweet set"
  (letfn [(builder [scores tweets]
            (if (empty? tweets) scores
              (recur (update-scores (first tweets) scores sentiments) (rest tweets))))]
    (builder {} tweets)))


