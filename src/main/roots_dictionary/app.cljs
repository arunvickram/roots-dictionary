(ns roots-dictionary.app
  (:require [reagent.dom :as dom]
            [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.malli :as rcm]
            [roots-dictionary.views :as views]
            [roots-dictionary.routes :as routes]
            [roots-dictionary.db :as db]))

(defn app
  []
  (views/declension-tag-select)
  #_[views/word-display #:word.declension{:suffix "é"
                                        :root "compr"}])

(defn current-page []
  [views/word-display {:suffix "ತ್ತೇನೆ"
                       :root "ಬದಲಿಸು"}]
  #_[:div
   (if @match
     (let [view (:view (:data @match))]
       [view @match]))])

(defn ^:dev/after-load start []
  (dom/render [current-page]
    (.getElementById js/document "app")))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  #_(rfe/start!
   (rf/router routes {:data {:coercion rcm/coercion}})
   #(reset! match %)
   {:use-fragment true})
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
