(ns quo2.screens.dropdown
  (:require [quo.components.text :as text]
            [quo.previews.preview :as preview]
            [quo.react-native :as rn]
            [quo2.components.dropdown :as quo2]
            [quo2.foundations.colors :as colors]
            [reagent.core :as reagent]))

(def descriptor [{:label   "Icon"
                  :key     :icon
                  :type    :select
                  :options [{:key   :main-icons/placeholder
                             :value "Placeholder"}
                            {:key   :main-icons/wallet
                             :value "Wallet"}]}
                 {:label "Text"
                  :key   :text
                  :type  :text}
                 {:label "Disabled"
                  :key   :disabled?
                  :type  :boolean}
                 {:label "Default item"
                  :key   :default-item
                  :type  :text}
                 {:label "Border color"
                  :key   :border-color
                  :type  :text}
                 {:label "DD color"
                  :key   :dd-color
                  :type  :text}
                 {:label   "Size"
                  :key     :size
                  :type    :select
                  :options [{:key   :big
                             :value "big"}
                            {:key   :medium
                             :value "medium"}
                            {:key   :small
                             :value "small"}]}])

(defn cool-preview []
  (let [items ["Banana"
               "Apple"
               "COVID +18"
               "Orange"
               "Kryptonite"
               "BMW"
               "Meh"]
        state    (reagent/atom {:icon :main-icons/placeholder
                                :text "Dropdown"
                                :default-item "item1"
                                :border-color colors/neutral-60
                                :dd-color colors/purple-50
                                :size :small})
        selected-item  (reagent/cursor state [:default-item])
        on-select #(reset! selected-item %)]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer state descriptor]
       [rn/view {:padding-vertical 60
                 :align-items      :center}
        [text/text {:color :main} (str "Selected item: " @selected-item)]
        [quo2/dropdown (merge @state {:on-select on-select
                                      :items items})]]])))

(defn preview-dropdown []
  [rn/view {:background-color (colors/theme-colors colors/white colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :flex-grow                 1
                  :nestedScrollEnabled       true
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
