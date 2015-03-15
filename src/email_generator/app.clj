(ns email-generator.app
  (:require [email-generator.rest :as rest]
            [email-generator.webserver :as webserver]
            [clojure.tools.logging :as log]))

(defonce system (atom {}))

(defn init [system]
  (assoc system
    ::port (:port system)
    ::db-url (:db-url system)
    ::state :initialized))

(defn start [system]
  (let [db-url (::db-url system)
        port (::port system)]
    (log/info "Starting on port" port)
    (assoc system
      ::webserver (-> {:port port :handler #'rest/handler}
                      webserver/init
                      webserver/start)
      ::state :started)))

(defn stop [system]
  (-> system
      (update-in [::webserver] webserver/stop)
      (assoc ::state :stopped)))

(defn -main [& [port]]
  (let [port (Integer. (or port (System/getenv "PORT") "8080"))
        running-system (init {:port port})]
    (reset! system running-system)
    (swap! system start)))

(defn restart! []
  (swap! system (comp start stop)))

(defn start! []
  "Idempotently and safely starts the server"
  (swap! system (fn [system]
                  (if (not= :started (::state system))
                    (start system)
                    system))))

(comment
  (-main 3030)
  )
