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
  (-> (str (:first-name persona) "." (:last-name persona)
           "@" (:email-domain persona))
      .toLowerCase))

(defn full-email-address [persona]
  (str (display-name persona) " <" (email-address persona) ">"))

(defn personal-info-map [persona]
  (merge {:email-address (full-email-address persona)}
         (select-keys persona [:first-name :last-name])))

(defn uuid [] (str (UUID/randomUUID)))

(defn para [markov-model num-sentences]
  (string/join " " (markov/generate-sentences num-sentences markov-model)))

(defn body [markov-model num-paras num-sentences-per-para]
  (string/join "\n\n" (map (partial para markov-model)
                           (repeat num-paras num-sentences-per-para))))

(defn email [from-persona to-persona]
  (let [markov-model (markov/read-model (:text-generation-model from-persona))
        to (full-email-address to-persona)
        subject (para markov-model 1)
        date (Date.)
        message-id (uuid)
        body-text (body markov-model 4 3)]
    {:from (full-email-address from-persona)
     :to to
     :subject subject
     :date date
     :message-id message-id
     :body body-text}))
