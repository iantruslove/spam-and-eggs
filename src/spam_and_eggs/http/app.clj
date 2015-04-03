(ns spam-and-eggs.http.app
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [spam-and-eggs.http.api :as api]
            [spam-and-eggs.http.html :as html]))

(def handler (routes #'html/handler
                     #'api/handler
                     (route/not-found "Page not found")))
