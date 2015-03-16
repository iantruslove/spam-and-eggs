(ns spam-and-eggs.test.rest
  (:require [spam-and-eggs.rest :refer :all]
            [cheshire.core :as json]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(defn generate-request
  ([method path query-string]
   (merge (generate-request method path)
          (when query-string {:query-string query-string})))
  ([method path]
   {:server-port 80
    :server-name "localhost"
    :remote-addr "192.168.1.1"
    :scheme :http
    :request-method method
    :uri path
    ;; :body "data"}
    :headers {;;e.g. "foo" "bar"
              }}))

(defmacro with-response
  [[response-binding handler method path & [query-params]] & body]
  (let [params-string (when query-params
                        (string/join "&"
                                     (map (fn [[k v]] (str k "=" v))
                                          query-params)))]
    `(let [~response-binding (~handler (generate-request ~method ~path ~params-string))]
       ~@body)))

(deftest test-root-200
  (with-response [resp handler :get "/"]
    (is (= 200 (:status resp)))
    (is (:body resp))))

(deftest test-404
  (with-response [resp handler :get "/foo/bar/baz"]
    (is (= 404 (:status resp)))))

(deftest test-api
  (testing "email-addresses"
    (with-response [resp handler :get "/api/email-addresses" {"n" 10}]
      (is (= 200 (:status resp)))
      (let [body (json/parse-string (:body resp) true)]
        (is (= 10 (:num-addresses body)))
        (is (= 10 (count (:addresses body))))))))
