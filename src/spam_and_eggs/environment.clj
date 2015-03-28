(ns spam-and-eggs.environment)

(defn env-type
  "Returns whether the run environment is `:prod` or `:dev`."
  []
  (if (System/getenv "DYNO") ;; Heroku
    :prod
    :dev))
