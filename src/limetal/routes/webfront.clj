(ns limetal.routes.webfront
  (:require [limetal.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :refer [response file-response]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io])
  (:use [taoensso.timbre :only [trace debug info warn error fatal]]
        [ring.middleware.anti-forgery])
  (:import [java.io File FileInputStream FileOutputStream]))



(defn web-home-page []
  (layout/render
    "web/home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))


(defroutes webfront-routes
  (GET "/web/" [] (web-home-page)))
