(ns coursera_data_science_clj.file-test
  (:use clojure.test
        coursera_data_science_clj.file))


(deftest parse-int-test
  (testing "Parsing integers")
  (is (= 42 (parse-int "42!!!"))))

(deftest load-sentiments-test
  (testing "Loading sentiment scores from a file")
  (is (= {"one" 1 "two" 2 "three" 3} (load-sentiments "test/test-dict.txt"))))
