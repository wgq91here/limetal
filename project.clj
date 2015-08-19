(defproject limetal "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [selmer "0.8.4"]
                 [com.taoensso/timbre "4.0.2"]
                 [com.taoensso/tower "3.0.2"]
                 [markdown-clj "0.9.67"]
                 [environ "1.0.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-session-timeout "0.1.0"]
                 [ring "1.4.0"
                  :exclusions [ring/ring-jetty-adapter]]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.3"]
                 [bouncer "0.3.3"]
                 [prone "0.8.2"]
                 [org.clojure/tools.nrepl "0.2.10"]
                 [buddy "0.6.0"]
                 [com.novemberain/monger "2.1.0"]
                 [korma "0.4.2"]
;                 [org.clojure/java.jdbc "0.4.1"]
                 [org.clojure/core.cache "0.6.4"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [mysql/mysql-connector-java "5.1.6"]
;                 [org.xerial/sqlite-jdbc "3.8.10.2"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [http-kit "2.1.19"]]

  :min-lein-version "2.0.0"
  :uberjar-name "limetal.jar"
  :jvm-opts ["-server"]

  :main limetal.core

  :plugins [[lein-environ "1.0.0"]
            [lein-ancient "0.6.5"]]
  :profiles {:uberjar {:omit-source true
                       :env {:production true}
                       :aot :all}
             :dev {:dependencies [[ring-mock "0.1.5"]
                                  [ring/ring-devel "1.4.0"]
                                  [pjstadig/humane-test-output "0.7.0"]]


                   ;;when set the application start the nREPL server on load

                   :repl-options {:init-ns limetal.core}
                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]
                   :env {:dev true
                         :nrepl-port 7001}}})
