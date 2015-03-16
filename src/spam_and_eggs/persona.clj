(ns spam-and-eggs.persona
  (:require [spam-and-eggs.config :refer [config]]
            [clojure.test.check.generators :as gen]))

(defn generatorize-freq-pair-values [pairs]
  (map (fn [[k v]] [k (gen/return v)]) pairs))

(defn frequency-generatorize [pairs]
  (gen/frequency (generatorize-freq-pair-values pairs)))


(def first-name-generator (gen/one-of [(frequency-generatorize
                                        (config :data :male-first-names))
                                       (frequency-generatorize
                                        (config :data :female-first-names))]))

(def last-name-generator (frequency-generatorize (config :data :last-names)))

(def email-domain-generator (gen/elements (config :data :domains :default)))

(defn persona [first-name last-name email-domain prolificness]
  {:first-name first-name
   :last-name last-name
   :email-domain email-domain
   :email-address-format :first-dot-last
   :email-pretty-name :quoted-first-last
   :email-body-persona :business
   :email-body-length :short
   :prolificness prolificness})

(defn generate-personas [n]
  (gen/sample (gen/fmap (partial apply persona)
                        (gen/tuple first-name-generator
                                   last-name-generator
                                   email-domain-generator
                                   gen/s-pos-int))
              n))
