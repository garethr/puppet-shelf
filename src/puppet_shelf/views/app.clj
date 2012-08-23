(ns puppet-shelf.views.app
  (:require [noir.response :as resp]
            [redis.core :as redis])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]
        [clojure.data.json :only (read-json)]
        [hiccup.page :only [include-css html5]]))

(defmacro dbg[x]
  `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

(defpartial layout [& content]
  (html5
    [:head
      [:title "Puppet Shelf"]
      (include-css "/css/reset.css")
      (include-css "/css/main.css")]
    [:body
      [:div#wrapper
        content]]))

(defn scale-time [input]
  (cond (< input 1) 1
        (< input 60) 2
        (< input 120) 3
        (< input 300) 4
        :else 5))

(def redis-config
  {:host "127.0.0.1" :port 6379 :db 0})

(defn scale-failures [input]
  (cond (< input 1) 0
        (>= input 1) 5))

(defpartial display-host [host-name]
  (try
    (let [failures (redis/lindex (str "failures:" host-name) 0)
          time-taken (redis/lindex (str "time:" host-name) 0)]
      [:li
       [:span {:class (str "scale" (scale-time (Float/parseFloat time-taken)))} time-taken "s"]
       [:span {:class (str "failures scale" (scale-failures (Integer/parseInt failures)))} failures]])
    (catch NullPointerException e "")))

(defpartial display-host-list [hosts]
  [:ul
    (map display-host hosts)])

(defpage "/" []
        (redis/with-server redis-config
          (do
            (let [hosts (redis/smembers "hosts")]
              (layout
                (display-host-list hosts))))))

(defpage "/hosts" []
        (resp/redirect "/"))

(defpage "/hosts/:title" {title :title}
        (redis/with-server redis-config
          (do
            (layout
              (display-host-list [title])))))

(defpage [:post "/reports"] {:as report-json}
        (redis/with-server redis-config
          (do
            (let [report (read-json (:req-body report-json))]
              (redis/sadd "hosts" (:host report))
              (redis/lpush (str "time:" (:host report)) (:time report))
              (redis/lpush (str "failures:" (:host report)) (:failures report))
              (str "OK")
        ))))
