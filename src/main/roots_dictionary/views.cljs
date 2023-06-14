(ns roots-dictionary.views
  (:require [roots-dictionary.events :as events]
            [roots-dictionary.db :as db]
            [reagent.core :as r]
            ["react" :as react]
            ["@headlessui/react" :refer (Combobox)]))

(def ComboboxInput (.-Input Combobox))
(def ComboboxOptions (.-Options Combobox))
(def ComboboxOption (.-Option Combobox))

(defn ->tag-option [tag]
  [:> ComboboxOption {:key (:name tag)
                      :value tag}
   [:div.p-5
    (:text tag)]
   ])

(defn ->tag-li [tag]
  [:li {:key (:name tag)}
   (:text tag)])

(def tags
  [{:name "First person" :text "First person"}
   {:name "Plural" :text "Plural"}])

(defn declension-tag-badge [tag]
  [:span {:key (:name tag)
          :class "inline-flex items-center gap-x-0.5 rounded-md bg-purple-50 px-2 py-1 text-xs font-medium text-purple-700 ring-1 ring-inset ring-purple-700/10"}
   (:text tag)
   [:button {:type "button"
             :class "group relative -mr-1 h-3.5 w-3.5 rounded-sm hover:bg-purple-600/20"}
    [:span.sr-only "Remove"]
    [:svg {:viewBox "0 0 14 14"
           :class "h-3.5 w-3.5 stroke-indigo-600/50 group-hover:stroke-indigo-600/75"}
     [:path {:d "M4 4l6 6m0-6l-6 6"}]]
    [:span.absolute.-inset-1]]])

(defn js-by [key]
  (fn [a b] (= (key (js->clj a :keywordize-keys true)) (key (js->clj b :keywordize-keys true)))))

(def selected-tags (r/atom []))

(defn declension-tag-select []
  (print "Current state: " @selected-tags)
  [:> Combobox {:value @selected-tags
                :on-change #(reset! selected-tags (js->clj % :keywordize-keys true))
                :multiple true}
   [:div {:class "relative w-full cursor-default overflow-hidden rounded-lg bg-white text-left shadow-md focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 focus-visible:ring-offset-2 focus-visible:ring-offset-teal-300 sm:text-sm"}
    (if-not (empty? @selected-tags)
      [:ul
       (do (print @selected-tags)
           (map declension-tag-badge @selected-tags))])
    [:> ComboboxInput
     {:className "w-full border-none py-2 pl-3 pr-10 text-sm leading-5 text-gray-900 focus:ring-0"}]
    [:> ComboboxOptions
     (map ->tag-option tags)]]])

(defn word-display [word]
  [:div.flex.flex-1.flex-row.max-w.justify-center.text-5xl {:dir "ltr"}
   [:span
    {:after (or (:suffix word) "")
     :before (or (:prefix word) "")
     :class "font-bold before:content-[attr(before)] after:content-[attr(after)] before:text-red-500 after:text-blue-500"}
    (:word.declension/root word)]
   [:button {:class "ltr:ml-3 rtl:mr-3"} "+"]])
