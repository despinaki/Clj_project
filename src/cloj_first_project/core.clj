(ns cloj-first-project.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]])
  (:gen-class))

(defn average [request] 
(let [body (:body request) firstRead (first (:readings body)) lastRead (last (:readings body))]
{ :mpan (:mpan body),
  :startTime (-> firstRead :readingTime),
  :endTime (-> lastRead :readingTime),
  :averageUsagePerHour (/ (- (-> lastRead :value) (-> firstRead :value)) 
  (/ (- (.getTime (read-string (str "#inst \""(-> lastRead :readingTime)"\"")))
  (.getTime (read-string (str "#inst \""(-> firstRead :readingTime)"\"")))) 1000 60 60)) }))

(defroutes app-routes
  (GET "/" [] "<h1>Hello Despoina</h1>")
  (POST "/echo" request (do (println (:body request)) (response (:body request))))
  (POST "/average" request (response (average request)))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      wrap-json-response
      (wrap-json-body {:keywords? true})))

(defn handler [request]
  {:status 200
  :headers {"Content-Type" "text/html"}
  :body "Hello World"})

(defn- start-jetty []
  (jetty/run-jetty (-> app (wrap-cors :access-control-allow-origin [#".*"] :access-control-allow-methods [:get :post])) {:port  3001 :join? false}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello World")
  (start-jetty))




