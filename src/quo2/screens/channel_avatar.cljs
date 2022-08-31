(ns quo2.screens.channel-avatar
  (:require [reagent.core :as reagent]
            [quo.react-native :as rn]
            [quo.previews.preview :as preview]
            [quo2.foundations.colors :as colors]
            [quo2.components.channel-avatar :as quo2]))

(def descriptor [{:label "Big?"
                  :key :big?
                  :type :boolean}
                 {:label "Dark"
                  :key :dark?
                  :type :boolean}
                 {:label "Icon color"
                  :key :icon-color
                  :type :text}
                 {:label "Lock status"
                  :key :lock-status
                  :type :select
                  :options [{:key   :unlocked
                             :value "Unlocked"}
                            {:key   :locked
                             :value "Locked"}]}
                 {:label "Icon"
                  :key :icon
                  :type :select
                  :options [{:key   :main-icons/peach
                             :value "Peach"}
                            {:key   :main-icons/wallet
                             :value "Wallet"}
                            {:key   :main-icons/play
                             :value "Play"}]}])

(defn cool-preview []
  (let [state (reagent/atom {:big? true
                             :dark? true
                             :lock-status :none
                             :icon :main-icons/peach20})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [rn/view {:flex 1}
        [preview/customizer state descriptor]]
       [rn/view {:padding-vertical 60
                 :flex-direction   :row
                 :justify-content  :center}
        [quo2/channel-avatar @state]]])))

(defn preview-channel-avatar []
  [rn/view {:background-color (colors/theme-colors colors/white
                                                   colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
