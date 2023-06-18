(ns roots-dictionary.events
  (:require [roots-dictionary.db :as db]
            [re-posh.core :as re-posh]))

(re-posh/reg-event-ds
 ::initialize-db
 (fn [_ _]
   db/initial-db))

(re-posh/dispatch-sync [::initialize-db])
