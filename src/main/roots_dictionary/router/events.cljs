(ns roots-dictionary.router.events
  (:require [re-frame.core :as re-frame]
            [re-posh.core :as re-posh]
            [datascript.core :as ds]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]
            [roots-dictionary.db]))

(re-frame/reg-fx
 :router/push-state
 (fn [route]
   (apply rfe/push-state route)))

(re-posh/reg-event-fx
 ::push-state
 (fn [_ [_ & route]]
   {:router/push-state route}))

(re-posh/reg-event-ds
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match (:router/current-route (ds/pull db '[*] [:db/ident :app/router]))
         controllers (rfc/apply-controllers (:controllers old-match) new-match)
         new-match (assoc new-match :controllers controllers)]
     [[:db/add [:db/ident :app/router] :router/current-route new-match]])))

(comment

  )
