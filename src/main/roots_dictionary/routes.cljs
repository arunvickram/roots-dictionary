(ns roots-dictionary.routes
  (:require [roots-dictionary.views :as views]
            [roots-dictionary.pages.home.views :as home-page]
            [re-posh.core :as re-posh]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.malli :as rcm]))


(def routes
  [["/"
    [""
     {:name ::home-page
      :view home-page/view
      :controllers
      [{;; per page set up controller functions to fetch data
        :start (fn [& params] (js/console.log "Entering home page"))

        ;; tear down
        :stop (fn [& params] (js/console.log "Leaving home page"))}]}]
    #_["word/:word"
     {:name ::word-entry-display
      :view views/dictionary-entry-page
      :parameters {:path [:map [:word string?]]}
      :controllers
      [{:start (fn [& params] (js/console.log "Entering word display page"))
        :stop (fn [& params] (js/console.log "Leaving word display page"))}]}]]])

(defn on-navigate [new-match]
  (when new-match
    (re-posh/dispatch [::navigated new-match])))

(def router
  (rf/router
   routes
   {:data {:coercion rcm/coercion}}))

(defn init-routes! []
  (prn "Initializing routes")
  (rfe/start!
   router
   on-navigate
   {:use-fragment true}))
