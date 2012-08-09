(ns puppet-shelf.server
  (:require [noir.server :as server]))

(server/load-views "src/puppet_shelf/views/")

(defn add-body [handler]
  (fn [req]
    (let [neue
      (update-in req [:params] assoc :req-body (slurp (:body req)))]
        (handler neue))))

(server/add-middleware add-body)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'puppet-shelf})))



