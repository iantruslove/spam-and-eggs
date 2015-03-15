(ns email-generator.markov
  (:require [email-generator.config :refer [config]]))

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
  (assert (transition-freqs ".")
          "There must be a start and end point - i.e. a period.")
  (into {} (map (fn [[term freqs]]
                  [term (transition-fn freqs)])
                transition-freqs)))

(defn build-sentence
  "Lazily uses the Markov generator represented by the {\"term\" fn}
  map to generate a sentence. Ensures that the last string in the
  sentence seq is a period."
  [transition-fns]
  (concat (vec (take-while (fn [term] (not= "." term))
                           (iterate (fn [prior-term]
                                      ((transition-fns prior-term)))
                                    ((transition-fns ".")))))
          '(".")))

(comment
  (apply println
         (interleave
          (let [transition-fns (-> (config :data :markov-transition-counts :testing)
                                   normalize-transition-counts
                                   build-transition-fns)]
            (repeatedly 20 (fn []
                             (clojure.string/join " " (build-sentence transition-fns)))))
          (repeat "\n"))
         )
  )
