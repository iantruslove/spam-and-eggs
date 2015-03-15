(ns spam-and-eggs.rest
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.resource :as resource]))

(defroutes handler
  (GET "/" [] (resp/resource-response "index.html" {:root "web"}))
  (route/resources "/" {:root "web"})
  (route/not-found "Page not found"))
