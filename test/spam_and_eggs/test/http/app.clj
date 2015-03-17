(ns spam-and-eggs.test.http.app
  (:require [clojure.test :refer :all]
            [spam-and-eggs.http.app :refer :all]
            [spam-and-eggs.test.http.support :refer [with-response]]))

(deftest test-root-200
  (with-response [resp handler :get "/"]
    (is (= 200 (:status resp)))
    (is (:body resp))))

(deftest test-404
  (with-response [resp handler :get "/foo/bar/baz"]
    (is (= 404 (:status resp)))))
