(ns spam-and-eggs.http.html
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [hbs.core :as handlebars]
            [hbs.helper :as helper]
            [ring.util.response :as resp]
            [spam-and-eggs.email :as email]
            [spam-and-eggs.environment :as environment]
            [spam-and-eggs.persona :as persona]))

(def set-template-path! (delay (handlebars/set-template-path! "" ".hbs")))

@set-template-path!

(helper/defhelper breaklines [ctx options]
  (-> ctx
      ;;(string/replace #"(\r\n|\r|\n)(\r\n|\r|\n)+" "</p>\n<p>")
      ;;(string/replace #"(\r\n|\r|\n)" "<br>\n")
      (string/replace #"(\r\n|\r|\n)" "<br>\n")))

(defn index-page-handler [_]
  (-> (handlebars/render-file "templates/index"
                              {:people (->> (persona/generate-personas 5)
                                            (map email/personal-info-map))
                               :emails [(apply email/email
                                               (persona/generate-personas 2))]
                               :prod (= :prod (environment/env-type))})
      resp/response
      (resp/header "content-type" "text/html;charset=UTF-8")))

(defroutes handler
  (GET "/" [] index-page-handler)
  (GET "/index.html" [] index-page-handler)
  (route/resources "/" {:root "web"}))
