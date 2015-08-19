(ns limetal.core
  (:require [limetal.handler :refer [app init destroy parse-port]]
            [org.httpkit.server :as http-kit]
            [taoensso.timbre :as timbre]
            [environ.core :refer [env]])
  (:gen-class))

(defn http-port [[port]]
  (parse-port (or port (env :port) 3000)))


(defonce server (atom nil))


(defn start-server [port]
  (init)
  (reset! server
          (http-kit/run-server
            app
            {:port port})))

(defn stop-server []
  (when @server
    (destroy)
    (@server :timeout 100)
    (reset! server nil)))

(defn start-app [port]
  (let [port (http-port port)]
    (.addShutdownHook (Runtime/getRuntime) (Thread. stop-server))
    (timbre/info "server is starting on port " port)
    (start-server port)))

(defn -main [& args]
  (start-app args))
