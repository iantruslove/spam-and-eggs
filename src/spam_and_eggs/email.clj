(ns spam-and-eggs.email
  (:require [clojure.string :as string]
            [spam-and-eggs.config :as config]
            [spam-and-eggs.markov :as markov])
  (:import (java.util Date UUID)))

;; TODO: support other patterns
(defn display-name [persona]
  (str (:first-name persona) " " (:last-name persona)))

;; TODO: support other patterns
(defn email-address [persona]
  (-> (str (:first-name persona) "." (:last-name persona) "@" (:email-domain persona))
      .toLowerCase))

(defn full-email-address [persona]
  (str (display-name persona) " <" (email-address persona) ">"))

(defn personal-info-map [persona]
  (merge {:email-address (full-email-address persona)}
         (select-keys persona [:first-name :last-name])))

(defn uuid [] (str (UUID/randomUUID)))

(defn email [from-persona to-persona]
  (let [markov-model (config/config :data :markov-transition-counts
                             (:text-generation-model from-persona))
        to (full-email-address to-persona)
        subject (first (markov/generate-sentences 1 markov-model))
        date (Date.)
        message-id (uuid)
        body (string/join " " (markov/generate-sentences 10 markov-model))]
    {:from (full-email-address from-persona)
     :to to
     :subject subject
     :date date
     :message-id message-id
     :body body}))
