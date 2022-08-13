(ns quo2.screens.page-nav
  (:require [reagent.core :as reagent]
            [quo.react-native :as rn]
            [quo.previews.preview :as preview]
            [quo2.foundations.colors :as colors]
            [quo2.components.page-nav :as quo2]))

(def descriptor [{:label "Align middle-component to the left"
                  :key :align-mid
                  :type :boolean}
                 {:label "Page nav color"
                  :key :page-nav-color
                  :type :text}
                 {:label "Mid section main text"
                  :key :mid-section-main-text
                  :type :text}
                 {:label "Mid section type"
                  :key :mid-section-type
                  :type :select
                  :options [{:value "Text only"
                             :key :text-only}
                            {:value "Text with description"
                             :key :text-with-description}
                            {:value "Text with one icon"
                             :key :text-with-one-icon}
                            {:value "Text with two icons"
                             :key :text-with-two-icon}]}
                 {:label "Mid section icon"
                  :key :mid-section-icon
                  :type :select
                  :options [{:value "Placeholder"
                             :key :main-icons/placeholder20}
                            {:value "Wallet"
                             :key :main-icons/wallet}]}
                 {:label "Mid section left icon"
                  :key :mid-section-left-icon
                  :type :select
                  :options [{:value "Placeholder"
                             :key :main-icons/placeholder20}
                            {:value "Wallet"
                             :key :main-icons/wallet}]}
                 {:label "Mid section right icon"
                  :key :mid-section-right-icon
                  :type :select
                  :options [{:value "Placeholder"
                             :key :main-icons/placeholder20}
                            {:value "Wallet"
                             :key :main-icons/wallet}]}
                 {:label "Mid section description"
                  :key :mid-section-description
                  :type :text}
                 {:label "Mid section description icon"
                  :key :mid-section-description-icon
                  :type :select
                  :options [{:value "Placeholder"
                             :key :main-icons/placeholder20}
                            {:value "Wallet"
                             :key :main-icons/wallet}]}
                 {:label "Mid section description user icon"
                  :key :mid-section-description-user-icon
                  :type :text}
                 {:label "Mid section main text icon"
                  :key :mid-section-main-text-icon
                  :type :select
                  :options [{:value "Placeholder"
                             :key :main-icons/placeholder20}
                            {:value "Wallet"
                             :key :main-icons/wallet}]}
                 {:label "Mid section main text icon color"
                  :key :mid-section-main-text-icon-color
                  :type :text}
                 {:label "Mid section user icon URL"
                  :key :mid-section-user-icon-uri
                  :type :text}
                 {:label "left section icon"
                  :key :left-section-icon
                  :type :select
                  :options [{:value "Placeholder"
                             :key :main-icons/placeholder20}
                            {:value "Wallet"
                             :key :main-icons/wallet}]}
                 {:label "Left section icon color"
                  :key :left-section-icon-color
                  :type :text}
                 {:label "Left section icon bg color"
                  :key :left-section-icon-bg-color
                  :type :text}])


(defn cool-preview []
  (let [state (reagent/atom {})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [rn/view {:flex 1}
        [preview/customizer state descriptor]]
       [rn/view {:padding-vertical 60
                 :flex-direction   :row
                 :justify-content  :center}
        [quo2/page-nav @state]]])))

(defn preview-page-nav []
  [rn/view {:background-color (colors/theme-colors colors/white
                                                   colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
