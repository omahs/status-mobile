(ns quo2.screens.preview-lists
  (:require [quo.react-native :as rn]
            [reagent.core :as reagent]
            [quo.previews.preview :as preview]
            [status-im.react-native.resources :as resources]
            [quo2.foundations.colors :as colors]
            [status-im.ui.components.react :as react]
            [quo2.components.preview-list :as quo2]))

(def descriptor [{:label   "Type:"
                  :key     :type
                  :type    :select
                  :options [{:key   :users
                             :value "Users"}
                            {:key   :communities
                             :value "Communities"}
                            {:key   :accounts
                             :value "Accounts"}
                            {:key   :tokens
                             :value "Tokens"}
                            {:key   :collectibles
                             :value "Collectibles"}
                            {:key   :dapps
                             :value "dApps"}]}
                 {:label   "Size:"
                  :key     :size
                  :type    :select
                  :options [{:key   32
                             :value "32"}
                            {:key   24
                             :value "24"}
                            {:key   16
                             :value "16"}]}
                 {:label "Previews"
                  :key   :previews
                  :type  :select
                  :options [{:key   1
                             :value 1}
                            {:key   2
                             :value 2}
                            {:key   3
                             :value 3}
                            {:key   4
                             :value 4}
                            {:key   5
                             :value "More than 4"}]}
                 {:label "Count"
                  :key   :count
                  :type  :text}])

(defn cool-preview []
  (let [state (reagent/atom {:type     :users
                             :size     32
                             :previews 1
                             :count    1})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer state descriptor]
       [rn/view {:padding-vertical 60
                 :align-items      :center}
        [quo2/preview-list (:type @state) (:size @state) nil]]])))

(defn preview-preview-lists []
  [rn/view {:background-color (colors/theme-colors colors/white colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
