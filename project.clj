(defproject martin "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ring/ring-core "1.5.0"]
                 [metosin/compojure-api "1.1.3"]
                 [org.slf4j/slf4j-log4j12 "1.7.21"]
                 [ring-cors "0.1.8"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jdmk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :ring {:handler martin.core/handler :port 8000}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-devel "1.5.0"]]
                   :plugins [[lein-ring "0.9.7"]]}})