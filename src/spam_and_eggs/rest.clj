(ns spam-and-eggs.rest
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as ring-json]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.params :as params]
            [ring.middleware.resource :as resource]
            [ring.util.response :as resp]
            [spam-and-eggs.email :as email]
            [spam-and-eggs.persona :as persona]))

(defn wrap-trailing-newline [handler]
  (fn [req]
    (when-let [resp (handler req)]
      (update-in resp [:body] (fn [s] (str s "\n"))))))

(defroutes html-routes
  (GET "/" [] (resp/resource-response "index.html" {:root "web"}))
  (route/resources "/" {:root "web"}))

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

(def api-routes
  (-> (routes (context "/api" []
                (GET "/email-addresses" req (get-email-addresses-handler req))))
      (ring-json/wrap-json-response {:pretty true})
      wrap-trailing-newline
      keyword-params/wrap-keyword-params
      params/wrap-params))

(def handler (routes html-routes
                     api-routes
                     (route/not-found "Page not found")))
