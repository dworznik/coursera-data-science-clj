(ns coursera_data_science_clj.sentiment-test
  (:use clojure.test
        coursera_data_science_clj.sentiment))


(def sentiments {"good" 1 "better" 2 "best" 3 "bad" -1 "worse" -2 "worst" -3 "meh" 0})

(def tweet-sentiment
  (tweet-sentiment-builder sentiments))

(deftest tweet-words-test
  (testing "Split the tweet")
  (is (= '("hana" "dul" "set") (tweet-words "Hana, dul... set!!!"))))

(deftest tweet-sentiment-test
  (testing "Calculate tweet sentiment"
    (is (= 1 (tweet-sentiment "good")))
    (is (= 0 (tweet-sentiment "good bad")))
    (is (= 0 (tweet-sentiment "meh    good bad")))
    (is (= 1 (tweet-sentiment "good!!!!!1111")))
    (is (= 6 (tweet-sentiment "better, better, better... meh")))
    (is (= -5 (tweet-sentiment "@worse - worst")))
    (is (= -3 (tweet-sentiment "just the worst kind")))))

(def tweets
  '("good better unknown" ; +2 -0
     "unknown worse best worst" ; +1 -2
     ))

(def term-sentiment (term-sentiment-builder tweets sentiments))

(deftest tweet-sentiment-ratio-test
  (testing "")
  (is (= 2 (tweet-sentiment-ratio (first tweets) sentiments)))
  (is (= 0.5 (tweet-sentiment-ratio (second tweets) sentiments))))

(deftest update-scores-test
  (is (= {"unknown" 2} (update-scores (first tweets) {} sentiments)))
  (is (= {"unknown" 0.5} (update-scores (second tweets) {} sentiments))))


(deftest term-sentiment-test
  (testing "Term sentiment")
  (is (= 2.5 (term-sentiment "unknown"))))
