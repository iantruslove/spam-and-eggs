(ns email-generator.config
  (:require [carica.core :refer [configurer resources]]))

(def config (configurer (resources "config.edn")))
