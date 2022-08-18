(ns quo2.screens.account-selector
  (:require [quo.react-native :as rn]
            [quo.previews.preview :as preview]
            [reagent.core :as reagent]
            [quo2.foundations.colors :as colors]
            [quo2.components.account-selector :as quo2]))

(def descriptor  [{:label "Show Label?:"
                   :key   :show-label?
                   :type  :boolean}
                  {:label "Transparent Background?:"
                   :key   :transparent?
                   :type  :boolean}
                  {:label "Account Text"
                   :key   :account-text
                   :type  :text}
                  {:label "Label Text"
                   :key   :label-text
                   :type  :text}])

(defn cool-preview []
  (let [state    (reagent/atom {:show-label?    true
                                :transparent?   false
                                :style          {:width :100%}
                                :account-text   "My Savings"
                                :label-text     "Label"})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer state descriptor]
       [rn/view {:padding-vertical 60
                 :align-items      :center}
        [quo2/account-selector @state]]])))

(defn preview-this []
  [rn/view {:background-color (colors/theme-colors colors/white colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
