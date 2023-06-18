(ns roots-dictionary.events.router
  (:require [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]))

(re-frame/reg-fx
 :push-router-state
 (fn [route]
   (apply rfe/push-state route)))
