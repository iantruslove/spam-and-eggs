(ns spam-and-eggs.test.markov
  (:require [spam-and-eggs.markov :refer :all]
            [clojure.test :refer :all]))

(deftest test-sentence-seq-constuction
  (is (= '("a" "b.") (build-sentence-token-seq {"" (constantly "a")
                                                "a" (constantly "b.")
                                                "b." (constantly "X")})))
  (is (= '("a" "b?") (build-sentence-token-seq {"" (constantly "a")
                                                "a" (constantly "b?")
                                                "b?" (constantly "X")})))
  (is (= '("a" "b!") (build-sentence-token-seq {"" (constantly "a")
                                                "a" (constantly "b!")
                                                "b!" (constantly "X")}))))
