(ns spam-and-eggs.markov
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log])
  (:import (java.io PushbackReader)
           (java.lang Character)))

(def transition-count-map )

(defn sum-map-vals [m]
  (reduce (fn [acc [_ v]] (+ acc v))
          0
          m))

(defn normalize-transition-counts [transition-count-map]
  (into {}
        (map (fn [[term transition-counts]]
               [term (let [num-transitions (sum-map-vals transition-counts)]
                       (map (fn [[term count]]
                              [(/ count num-transitions) term])
                            transition-counts))])
             transition-count-map)))

(defn transition-fn [outcome-freqs]
  (fn [] (let [n (rand)]
           (reduce (fn [last-v [next-v term]]
                     (assert (<= 0 n 1))
                     (assert (<= 0 (+ last-v next-v) 1))
                     (if (<= last-v n (+ last-v next-v))
                       (reduced term)
                       (+ last-v next-v)))
                   0
                   outcome-freqs))))

(defn build-transition-fns [transition-freqs]
  (assert (transition-freqs "")
          "There must be a start and end point - i.e. an empty string")
  (into {} (map (fn [[term freqs]]
                  [term (transition-fn freqs)])
                transition-freqs)))

(defn build-sentence-token-seq
  "Lazily uses the Markov generator represented by the {\"term\" fn}
  map to generate a sentence's worth of tokens."
  [transition-fns]
  (letfn [(generator [word transition-fns]
            (cons word (lazy-seq
                        (if (.endsWith word ".")
                          nil
                          (if-let [transition-fn (transition-fns word)]
                            (generator (transition-fn) transition-fns)
                            (log/info (str "Generator fn not found for \"" word
                                           "\" - last word in input data?")))))))]
    (rest (generator "" transition-fns))))

(defn initial-caps [word]
  (apply str (Character/toUpperCase (first word)) (rest word)))

(defn token-seq-to-sentence [token-seq]
  (reduce (fn [sentence token]
            (str sentence " " token))
          (initial-caps (first token-seq))
          (rest token-seq)))

(defn generate-sentences [n markov-model]
  (let [transition-fns (-> markov-model
                           normalize-transition-counts
                           build-transition-fns)]
    (repeatedly n #(-> (build-sentence-token-seq transition-fns)
                       token-seq-to-sentence))))

(defn read-model [model]
  (-> (str "models/" (name model) ".edn")
      io/resource
      io/reader
      PushbackReader.
      edn/read))

(comment
  (generate-sentences 20 (read-model :business))
  (generate-sentences 20 (read-model :h2g2))
  )
