(ns puppet-shelf.test.app
  (:use [noir.core]
        [noir.util.test2]
        [clojure.test]
        [puppet-shelf.views.app]))

(deftest test-host-redirect
  (-> (send-request [:get "/hosts"])
    (has-status 302)))

(deftest test-host-page
  (-> (send-request [:get "/hosts/webexamplecom"])
    (has-status 200)))

(deftest test-homepage-rendering
  (-> (send-request [:get "/"])
    (has-status 200)))

(deftest test-scale-time
  (is (= 1 (scale-time 0.1)))
  (is (= 2 (scale-time 32)))
  (is (= 3 (scale-time 95)))
  (is (= 4 (scale-time 279)))
  (is (= 5 (scale-time 600))))

(deftest test-scale-failures
  (is (= 0 (scale-failures 0)))
  (is (= 5 (scale-failures 1)))
  (is (= 5 (scale-failures 10))))

(deftest test-redis-config
  (is (= "127.0.0.1" (:host redis-config)))
  (is (= 6379 (:port redis-config))))
