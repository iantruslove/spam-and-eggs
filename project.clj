(defproject spam-and-eggs "0.1.0-SNAPSHOT"
  :description "If it looks like spam, and it tastes like spam..."
  :url "http://spam-and-eggs.heroku.com"
  :min-lein-version "2.0.0"
  :main spam-and-eggs.app
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :uberjar-name "spam-and-eggs.jar"
  :profiles {:uberjar {:aot :all}}
  :dependencies [[cheshire "5.4.0"]
                 [compojure "1.3.2"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.7.0"]
                 [ring "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [selmer "0.8.2"]
                 [sonian/carica "1.1.0" :exclusions [[cheshire]]]])
