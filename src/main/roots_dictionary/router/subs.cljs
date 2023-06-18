(ns roots-dictionary.router.subs
  (:require [re-posh.core :as re-posh]))

(re-posh/reg-pull-sub
 ::current-page
 '[:db/id :router/current-route])

(comment
  @(re-posh/subscribe [::current-page [:db/ident :app/router]])

  )
