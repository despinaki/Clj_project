(defproject cloj-first-project "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [compojure "1.6.2"]
                 [ring-cors "0.1.13"]
                 [ring/ring-json "0.5.0"]
                 [clj-http "3.11.0"]
                 [environ "1.1.0"]
                 [org.apache.kafka/kafka-clients "2.4.1"]
                 [org.apache.kafka/kafka_2.12 "2.4.1"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [org.testcontainers/testcontainers "1.13.0"]
                 [org.testcontainers/kafka "1.13.0"]]
                
  :main ^:skip-aot cloj-first-project.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
