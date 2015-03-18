(ns spam-and-eggs.test.email
  (:require [spam-and-eggs.email :refer :all]
            [spam-and-eggs.persona :as persona]
            [clojure.test :refer :all]))

(deftest test-email
  (let [[from to] (persona/generate-personas 2)
        e (email from to)]
    (is (every? e #{:to :subject :date :message-id :body}))))
