(defproject puppet-shelf "0.1.0"
            :description "Puppet Shelf Dashboard"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.3.0-beta10"]
                           [org.clojure/data.json "0.1.2"]
                           [org.clojars.tavisrudd/redis-clojure "1.3.1"]]
            :dev-dependencies [[lein-daemon "0.4.0"]
                               [noir-test2 "1.0.0-SNAPSHOT"]
                               [lein-test-out "0.1.0"]]
            :daemon {:puppet-shelf {:ns puppet-shelf.server
                                       :pidfile "shelf.pid"}}
            :main puppet-shelf.server)

