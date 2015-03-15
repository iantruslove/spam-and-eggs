(ns spam-and-eggs.webserver
  (:require [ring.adapter.jetty :as jetty]))

(defn init [cfg]
  {:pre [(:port cfg) (:handler cfg)]}
  (assoc cfg :jetty-instance nil))

(defn extract-jetty-cfg [cfg]
  {:port (:port cfg)
   :join? false})

(defn start [cfg]
  (when (not (:jetty-instance cfg))
    (assoc cfg
      :jetty-instance
      (jetty/run-jetty (:handler cfg) (extract-jetty-cfg cfg)))))

(defn stop [cfg]
  (if (:jetty-instance cfg)
    (do
      (.stop (:jetty-instance cfg))
      (assoc cfg :jetty-instance nil))
    cfg))
