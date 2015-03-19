(ns spam-and-eggs.test.tools.analyzer
  (:require [spam-and-eggs.tools.analyzer :refer :all]
            [clojure.test :refer :all])
  (:import (java.io ByteArrayInputStream)))

(deftest test-analyze
  (let [bais (-> "this is just a test. this is good."
                 .getBytes
                 ByteArrayInputStream.)
        analysis (analyze bais)]
    (is (= {"" {"this" 2}
            "this" {"is" 2}
            "is" {"just" 1
                  "good." 1}
            "just" {"a" 1}
            "a" {"test." 1}
            "test." {"this" 1}}
           analysis))))
