(ns limetal.db.inter
  (:use [korma.db]
        [korma.core :exclude [update]]
        [korma.core :rename {update kupdate}])
  ;  (:require [korma.core :as kc])
  )

(def current-path (System/getProperty "user.dir"))

(defdb db (mysql {:user "root"
                  :password "wgq123"
                  :host "127.0.0.1"
                  :db "blog"}
            ))

(defentity weixin (database db))
