(ns spam-and-eggs.email)

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
