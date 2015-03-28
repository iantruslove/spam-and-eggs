(defproject spam-and-eggs "0.1.0-SNAPSHOT"
  :description "If it looks like spam, and it tastes like spam..."
  :url "http://spam-and-eggs.heroku.com"
  :min-lein-version "2.0.0"
  :main spam-and-eggs.app
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :uberjar-name "spam-and-eggs.jar"
  :profiles {:uberjar {:aot :all}
             :analyzer {:main spam-and-eggs.tools.analyzer}}
  :aliases {"analyze" ["with-profile" "analyzer" "run"]}
  :dependencies [[cheshire "5.4.0"]
                 [clj-http "1.0.1"]
                 [compojure "1.3.2"]
                 [hbs "0.8.0"]
                 [org.apache.lucene/lucene-core "5.0.0"]
                 [org.apache.lucene/lucene-analyzers-common "5.0.0"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ring "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [sonian/carica "1.1.0" :exclusions [[cheshire]]]])
