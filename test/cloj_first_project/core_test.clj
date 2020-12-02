(ns cloj-first-project.core-test
  (:require [clojure.test :refer :all]
            [cloj-first-project.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

; test that pr-str and clojure.edn/read-string are inverses of each other
(deftest pr-str-read-string-are-inverses
  (testing "pr-str and clojure.edn/read-string are inverses of each other"
    (is (= ["a" "b" "c"] (read-string (pr-str ["a" "b" "c"]))))
    (is (= [1 1 3] (read-string (pr-str [1 1 3])))) 
    (is (= {:a 1 :b 2} (read-string (pr-str {:a 1 :b 2}))))
  )
)

(def number-vector (shuffle (take 10 (random-sample 0.01 (range)))))
; find average delta 
(defn average-delta 
  "Return the average delta of all the numbers in a collection"
  [ordered-collection]
  (loop [i 0
         diff []]
      ; when i reaches the length of the collection (minus 1 bc of 0-indexing), return the average of deltas
      (if (= i (- (count ordered-collection) 1))
        (/ (reduce + diff) (count diff))
      ; otherwise get the difference of two consecutive numbers in the collection and store it in diff vector, and increase i
      ; the order of the recur args matters
      (do (println diff) (recur (inc i) (conj diff (- (get ordered-collection (+ i 1)) (get ordered-collection i))))))))

(deftest average-delta-test
  (testing "average-delta function returns the average delta of all numbers in an ordered collection"
    (is (= (average-delta [-3 5 1 9]) 4))
    (is (= (average-delta [8 5 0 6]) -2/3))))
; OR 
(deftest average-delta-test-parametrised
  (testing "average-delta function returns the average delta of all numbers in an ordered collection"
  (doseq [[input result] [[[-3 5 1 9] 4] [[8 5 0 6] -2/3]]]
          (is (= (average-delta input) result)))
    ))
