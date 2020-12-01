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
                 [ring/ring-json "0.5.0"]]
                
  :main ^:skip-aot cloj-first-project.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
