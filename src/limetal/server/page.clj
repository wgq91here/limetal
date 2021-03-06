(ns limetal.server.page
  (:require [limetal.layout :as layout]
            ;            [limetal.routes.server :as route]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :refer [response file-response]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [limetal.routes.server :refer [command_register]])
  (:use [taoensso.timbre :only [trace debug info warn error fatal]]
        [ring.middleware.anti-forgery])
  (:import [java.io File FileInputStream FileOutputStream]))

(defn page [req]
  {:list [{:s "this si" :s1 "s1"} {:s "page"}]})

(defn pages [req]
  {:list [{:s "ss" :s1 "s1"} {:s "pages"}]})

(command_register :page page "this is page")
(command_register :pages pages "this is pages")

(debug "loaded page command.")