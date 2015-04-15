(defproject spam-and-eggs "0.1.0-SNAPSHOT"
  :description "If it looks like spam, and it tastes like spam..."
  :url "http://spam-and-eggs.heroku.com"
  :min-lein-version "2.0.0"
  :main spam-and-eggs.app
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :uberjar-name "spam-and-eggs.jar"
  :profiles {:uberjar {:aot :all
                       :prep-tasks ["javac" "compile" "resource"]
                       :resource {:resource-paths ["doc"]
                                  :target-path "resources/web/doc"}}
             :analyzer {:main spam-and-eggs.tools.analyzer}}
  :aliases {"analyze" ["with-profile" "analyzer" "run"]}
  :plugins [[lein-resource "14.10.1"]]
  :dependencies [[cheshire "5.4.0"]
                 [clj-http "1.0.1"]
                 [compojure "1.3.2"]
                 [hbs "0.8.0"]
                 [org.apache.lucene/lucene-core "5.0.0"]
                 [org.apache.lucene/lucene-analyzers-common "5.0.0"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.7.0"]
                 [ring "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [sonian/carica "1.1.0" :exclusions [[cheshire]]]
                 ;; Logging
                 [org.slf4j/slf4j-api "1.7.7"]
                 [org.slf4j/jul-to-slf4j "1.7.7"]
                 [org.slf4j/jcl-over-slf4j "1.7.7"]
                 [org.slf4j/log4j-over-slf4j "1.7.7"]
                 [ch.qos.logback/logback-core "1.1.2"]
                 [ch.qos.logback/logback-classic "1.1.2"]
                 [org.clojure/tools.logging "0.2.6"]]
  :exclusions [org.slf4j/slf4j-log4j12
               org.slf4j/slf4j-api
               org.slf4j/slf4j-nopn])
