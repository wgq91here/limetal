(ns limetal.routes.server
  (:require [limetal.layout :as layout]
            ;            [limetal.server.page :as page]
            ;            [limetal.server.componse :as componse]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :refer [response file-response]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io])
  (:use [taoensso.timbre :only [trace debug info warn error fatal]]
        [ring.middleware.anti-forgery])
  (:import [java.io File FileInputStream FileOutputStream]))

(def version "0.1")

(def LADDER_COMMANDS (atom {}))
(def HELP_COMMANDS (atom {}))

;(reset! LADDER_COMMANDS `{:create})
;(conj :model @LADDER_COMMANDS)
;(reset! LADDER_COMMANDS (conj @LADDER_COMMANDS :model))
;`(~(cc "s" "sss"))

;(defmacro def-command-route [name & body]
;  `(defn ~name (~@body)))

(declare answer)

;(defmacro command_register [command args & body]
;  (if-not (contains? @LADDER_COMMANDS (keyword command))
;    (let [cm (str command)
;          cm_fn `(defn ~command [~args]
;                   (-> ~@body
;                     (answer {:command ~cm})))]
;      (reset! LADDER_COMMANDS (conj @LADDER_COMMANDS {(keyword command) cm_fn}))
;      )))

(defn command_register [command fn & common]
  (let [c (keyword command)
        m (first common)
        d (if (nil? m) "none" (str m))]
    ;    (if (not (contains? @LADDER_COMMANDS (keyword command)))
    (do
      (reset! LADDER_COMMANDS (conj @LADDER_COMMANDS {(keyword command) fn}))
      (reset! HELP_COMMANDS (conj @HELP_COMMANDS {(keyword command) d}))
      )
    ))
;  )

(defn answer [c j]
  (-> (merge c j)
    response
    (assoc :headers {"Content-Type" "application/json"})))

(command_register :help (fn [req] {:list (map #(hash-map (key %) (val %)) @HELP_COMMANDS)}) "list all commands.")

(defn command_route [req]
  (let [params (:params req)
        command (str (clojure.string/lower-case (:command params)))]
    (info (str "Command -> " (:command params)))
    (info (str "Command req -> " params))
    (let [cm (keyword command)]
      (if (contains? @LADDER_COMMANDS cm)
        (let [f (get @LADDER_COMMANDS cm)]
          (->
            (f req)
            (answer {:command cm}))
          )
        (answer {:command cm} {:message "no found command!" :state :error})))
    ;    (case command
    ;      "version" (answer {:command 'help'} {:message (str "version " version)})
    ;      "help" (c_help command req)
    ;      "create" (c_create command req)
    ;      "page" (c_page command req)
    ;      "pages" (c_pages command req)
    ;      "compone" (c_compone command req)
    ;      "compones" (c_compones command req)
    ;      "model" (do
    ;                (add_command model {} (c_model req))
    ;                (model {}))
    ;      (answer {:command command} {:message "no found command!" :state :error})
    ;      )
    )
  )

;(defn command_route [req]
;  (let [params (:params req)
;        command (str (clojure.string/lower-case (:command params)))]
;    (info (:command params))
;    (case command
;      "version" (answer {:command 'help'} {:message (str "version " version)})
;      "help" (c_help command req)
;      "create" (c_create command req)
;      "page" (c_page command req)
;      "pages" (c_pages command req)
;      "compone" (c_compone command req)
;      "compones" (c_compones command req)
;      "model" (do
;                (add_command model {} (c_model req))
;                (model {}))
;      (answer {:command command} {:message "no found command!" :state :error})
;      )))


(defroutes server-routes
  (POST "/c/:version/:command" req (command_route req))
  (GET "/c/:version/:command" req (command_route req)))
