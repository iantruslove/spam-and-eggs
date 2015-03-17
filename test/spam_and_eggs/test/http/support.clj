(ns spam-and-eggs.test.http.support
  (:require [clojure.string :as string]))

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
