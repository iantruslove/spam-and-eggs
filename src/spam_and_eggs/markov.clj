(ns spam-and-eggs.markov)

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

(defn build-sentence-token-seq
  "Lazily uses the Markov generator represented by the {\"term\" fn}
  map to generate a sentence's worth of tokens. Ensures that the last
  token in the sentence seq is a period."
  [transition-fns]
  (concat (vec (take-while (fn [term] (not= "." term))
                           (iterate (fn [prior-term]
                                      ((transition-fns prior-term)))
                                    ((transition-fns ".")))))
          '(".")))

(defn punctuation? [token]
  (cond
   (= "." token) true
   (= "," token) true
   :default false))

(defn initial-caps [word]
  (apply str (.toUpperCase (.toString (first word))) (rest word)))

(defn token-seq-to-sentence [token-seq]
  (reduce (fn [sentence token]
            (if (punctuation? token)
              (str sentence token)
              (str sentence " " token)))
          (initial-caps (first token-seq))
          (rest token-seq)))

(defn generate-sentences [n markov-model]
  (let [transition-fns (-> markov-model
                           normalize-transition-counts
                           build-transition-fns)]
    (repeatedly n #(-> (build-sentence-token-seq transition-fns)
                       token-seq-to-sentence))))

(comment
  (use '[spam-and-eggs.config])
  (require '[clojure.string])
  (println (clojure.string/join "\n"
                                (generate-sentences 20 (config :data :markov-transition-counts :testing))))
  )
