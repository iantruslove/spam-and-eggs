(ns spam-and-eggs.http.html
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [selmer.parser :as selmer]
            [spam-and-eggs.email :as email]
            [spam-and-eggs.environment :as environment]
            [spam-and-eggs.persona :as persona]))

(def set-template-path! (delay (selmer/set-resource-path!
                                (io/resource "templates"))))

@set-template-path!

(defn index-page-handler [_]
  (-> (selmer/render-file "index.html"
                          {:people (->> (persona/generate-personas 5)
                                        (map email/personal-info-map))
                           :email (apply email/email
                                         (persona/generate-personas 2))
                           :env (environment/env-type)})
      resp/response
      (resp/header "content-type" "text/html;charset=UTF-8")))

(defroutes handler
  (GET "/" [] index-page-handler)
  (GET "/index.html" [] index-page-handler)
  (route/resources "/" {:root "web"}))
