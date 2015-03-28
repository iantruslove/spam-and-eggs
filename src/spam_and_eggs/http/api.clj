(ns spam-and-eggs.http.api
  (:require [compojure.core :refer :all]
            [ring.middleware.json :as ring-json]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.params :as params]
            [ring.util.response :as resp]
            [spam-and-eggs.email :as email]
            [spam-and-eggs.persona :as persona]))

(defn wrap-trailing-newline [handler]
  (fn [req]
    (when-let [resp (handler req)]
      (update-in resp [:body] (fn [s] (str s "\n"))))))

(defn param-as-int [req key default]
  (try
    (Integer/parseInt (get-in req [:params key]))
    (catch NumberFormatException _
      default)))

(defn ranged-int [floor n ceil]
  (max floor (min ceil n)))

(defn get-email-addresses-handler [req]
  (let [num-addresses
        (ranged-int 1 (param-as-int req :n 10) 20)]
    (resp/response
     {:num-addresses num-addresses
      :addresses (->> (persona/generate-personas num-addresses)
                      (map email/personal-info-map))})))

(defn get-emails-handler [_]
  (let [email (apply email/email (persona/generate-personas 2))]
    (resp/response
     {:num-emails 1
      :emails [email]})))

(def handler
  (-> (routes (context "/api" []
                (GET "/_status" req (resp/response {:status :ok}))
                (GET "/email-addresses" req (get-email-addresses-handler req))
                (GET "/emails" req (get-emails-handler req))))
      (ring-json/wrap-json-response {:pretty true})
      wrap-trailing-newline
      keyword-params/wrap-keyword-params
      params/wrap-params))
