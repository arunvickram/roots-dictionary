(ns tailwind.views
  (:require [tailwind.events :as events]
            [tailwind.db :as db]
            [reagent.core :as r]
            ["react" :as react]
            ["@headlessui/react" :refer (Combobox)]))

(def ComboboxInput (.-Input Combobox))
(def ComboboxOptions (.-Options Combobox))
(def ComboboxOption (.-Option Combobox))

(defn ->tag-option [tag]
  [:> ComboboxOption {:key (:word.declension.tag/name tag)
                      :value (:word.declension.tag/name tag)}
   (:word.declension.tag/text tag)])

(defn ->tag-li [tag]
  [:li {:key (:word.declension.tag/name tag)}
   (:word.declension.tag/text tag)])

(def tags
  [#:word.declension.tag{:name "First person" :text "First person"}
   #:word.declension.tag{:name "Plural" :text "Plural"}])

(prn tags)

(defn declension-tag-select []
  (let [[selected-tags set-selected-tags] (react/useState [])]
    [:> Combobox {:value selected-tags
                  :on-change set-selected-tags
                  :multiple true}
     [:div {:class "relative w-full cursor-default overflow-hidden rounded-lg bg-white text-left shadow-md focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75 focus-visible:ring-offset-2 focus-visible:ring-offset-teal-300 sm:text-sm"}
      (if-not (empty? selected-tags)
        [:ul
         (do (print selected-tags)
          (map ->tag-li selected-tags)   )
         ])
      [:> ComboboxInput {:className "w-full border-none py-2 pl-3 pr-10 text-sm leading-5 text-gray-900 focus:ring-0"}]
      [:> ComboboxOptions
       (map ->tag-option tags)]]]))

(defn word-display [word]
  [:div.flex.flex-1.flex-row.max-w.justify-center.text-5xl {:dir "ltr"}
   [:span
    {:after (or (:word.declension/suffix word) "")
     :before (or (:word.declension/prefix word) "")
     :class "font-bold before:content-[attr(before)] after:content-[attr(after)] before:text-red-500 after:text-blue-500"}
    (:word.declension/root word)]
   [:button {:class "ltr:ml-3 rtl:mr-3"} "+"]])
