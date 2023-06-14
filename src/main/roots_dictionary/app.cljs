(ns roots-dictionary.app
  (:require [reagent.dom :as dom]
            [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [roots-dictionary.views :as views]
            [roots-dictionary.db :as db]
            ))

(defn home-page [& args]
  [:div
   [:h2 "Hello"]])

(defn app
  []
  (views/declension-tag-select)
  #_[views/word-display #:word.declension{:suffix "é"
                                        :root "compr"}])

;; start is called by init and after code reloading finishes
(defonce match (r/atom nil))

(defn current-page []
  [:div
   (if @match
     (let [view (:view (:data @match))]
       [view (:parameters @match)]))])

(def routes
  [["/"
    {:name ::home-page
     :view home-page}]
   ["/word/:text"
    {:name ::word-entry-display
     :view views/word-display
     :parameters {:path {:text string?}}}]])

(defn ^:dev/after-load start []
  (dom/render [current-page]
    (.getElementById js/document "app")))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (rfe/start!
   (rf/router routes {:data {:coercion rss/coercion}})
   #(reset! match %)
   {:use-fragment false})
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
