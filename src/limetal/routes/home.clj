(ns limetal.routes.home
  (:require [limetal.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :refer [response file-response]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io])
  (:use [taoensso.timbre :only [trace debug info warn error fatal]]
        [ring.middleware.anti-forgery])
  (:import [java.io File FileInputStream FileOutputStream]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn demo-page []
  (layout/render "demo.html" {:csrf-token *anti-forgery-token*}))

(defn demo-page-get [{params :params}]
  (info params)
  (layout/render "demo.html" {:info params}))

;
(def resource-path "./tmp/")

(defn file-path [path & [filename]]
  (java.net.URLDecoder/decode
    (str path File/separator filename)
    "utf-8"))
;

(defn demo-page-post [path {params :params}]
  (info (str "post : " params))
  (info params)
  (if (not (nil? (:post_file params)))
    (try
      (let [postfile (:post_file params)
            tempfile (:tempfile postfile)
            filename (:filename postfile)
            size (:size postfile)]
        (if (> size 0)
          (with-open [in (new FileInputStream tempfile)
                      out (new FileOutputStream (file-path path filename))]
            (let [source (.getChannel in)
                  dest (.getChannel out)]
              (.transferFrom dest source 0 (.size source))
              (.flush out)))))))
  (-> (dissoc params :post_file)
    response
    (assoc :headers {"Content-Type" "application/json"}))
  ;  {:status 200
  ;   :headers {"Content-Type" "application/json"}
  ;   :body params}
  ;    (layout/render "demo.html" {:info params})

  )

(defn ping [{session :session}]
  (str "pong " (:user session)))

(defn set-user! [id {session :session}]
  (-> (str "User set to: " id)
    response
    (assoc :headers {"Content-Type" "text/plain"})
    (assoc :session (assoc session :user id))))

(defn remove-user! [{session :session}]
  (-> (response "")
    (assoc :session (dissoc session :user))))

(defn clear-session! []
  (dissoc (response "") :session))


(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/demo" [] (demo-page))
  (POST "/demo/post" req (demo-page-post resource-path req))
  (GET "/demo/get" req (demo-page-get req))
  (GET "/demo/get/:id" req (demo-page-get req))
  (GET "/demo/files/:filename" [filename]
    (file-response (str resource-path filename)))
  (GET "/ping" req (ping req))
  (GET "/login/:id" [id :as req] (set-user! id req))
  (GET "/remove" req (remove-user! req))
  (GET "/logout" req (clear-session!)))

