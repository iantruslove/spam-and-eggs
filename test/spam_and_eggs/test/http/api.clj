(ns spam-and-eggs.test.http.api
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [spam-and-eggs.http.api :refer :all]
            [spam-and-eggs.test.http.support :refer [with-response]]))

(deftest test-api
  (testing "email-addresses"
    (with-response [resp handler :get "/api/email-addresses" {"n" 10}]
      (is (= 200 (:status resp)))
      (let [body (json/parse-string (:body resp) true)]
        (is (= 10 (:num-addresses body)))
        (is (= 10 (count (:addresses body)))))))

  (testing "email"
    (with-response [resp handler :get "/api/emails"]
      (is (= 200 (:status resp)))
      (let [body (json/parse-string (:body resp) true)]
        (is (every? (-> body :emails first)
                    #{:from :to :subject :date :message-id :body}))))))
