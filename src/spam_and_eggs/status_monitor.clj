(ns spam-and-eggs.status-monitor
  (:require [clj-http.client :as http]
            [clojure.tools.logging :as log]
            [spam-and-eggs.config :as config]))

(defn blocking-status-check [keep-running]
  (let [url (config/config :app :status-check :url)
        _ (log/info "Checking status of" url)
        status (try (:status (http/get url {:throw-exceptions false}))
                    (catch Throwable t
                      (log/warn t "Problem getting status.")
                      :some-error))]
    (log/info (str "Status: " status ". Going back to sleep now.")))
  (Thread/sleep (* 1000 (config/config :app :status-check :delay-secs)))
  (if-not @keep-running
    (log/info "Stopping status checker.")
    (recur keep-running)))

(defn run-status-check [{:keys [run] :as cfg}]
  (future (blocking-status-check run)))

(defn init []
  {:run (atom nil)})

(defn start [cfg]
  (reset! (:run cfg) true)
  (future
    (Thread/sleep 5000)
    (run-status-check cfg))
  cfg)

(defn stop [cfg]
  (reset! (:run cfg) false)
  cfg)
