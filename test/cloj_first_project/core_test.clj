(ns cloj-first-project.core-test
  (:require [clojure.test :refer :all]
            [cloj-first-project.core :refer :all]
            [clj-http.client :as client]
            [ring.adapter.jetty :as jetty]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))
; ********************************* RANDOM TESTS FOR PRACTICE PURPOSE ***************************************************
; test that pr-str and clojure.edn/read-string are inverses of each other
(deftest pr-str-read-string-are-inverses
  (testing "pr-str and clojure.edn/read-string are inverses of each other"
    (is (= ["a" "b" "c"] (read-string (pr-str ["a" "b" "c"]))))
    (is (= [1 1 3] (read-string (pr-str [1 1 3])))) 
    (is (= {:a 1 :b 2} (read-string (pr-str {:a 1 :b 2}))))
  )
)
; make a random vector 10 instances long
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
; ******************************************************************************************************************************

(declare test-port)
(defn with-server
  [f]
  (let [server (jetty/run-jetty app {:port 0 :join? false}) ;app comes from core.clj
        port (-> server .getConnectors first .getLocalPort)]
    (with-redefs [test-port port] ;with-redefs sets test-port to the value of port temporarily.
      (try
        (f)
        (finally
          (.stop server))))))
          
; (use-fixtures :once with-server)

(defn url [relative]
  (str "http://localhost:" test-port relative))

(defn post [relative] (client/post (url relative) ;client/post is an imported function
  { :body (slurp "test/cloj_first_project/resources/requests.json")
    :content-type :json
    :accept :json}))

(with-server #(post "/echo")) ;same as (with-server (partial echo-post "/echo")) in the sense that it doesn't invoke echo-post, just feeding the arg in.

(deftest echo-post
  (testing "A post request at the /echo endpoint returns the request body"
    (is (= (get (with-server #(post "/echo")) :body) (clojure.string/replace (slurp "test/cloj_first_project/resources/requests.json") #"\r|\n" {"\r" "" "\n" ""})))))
;test /average too