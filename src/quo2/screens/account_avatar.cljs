(ns quo2.screens.account-avatar
  (:require [reagent.core :as reagent]
            [quo.react-native :as rn]
            [quo.previews.preview :as preview]
            [quo.design-system.colors :as colors]
            [quo2.components.account-avatar :as quo2]))

(def descriptor [{:label   "Dark"
                  :key     :dark?
                  :type    :boolean}
                 {:label   "Size"
                  :key     :size
                  :type    :select
                  :options [{:key   :small
                             :value "Small"}
                            {:key   :medium
                             :value "Medium"}
                            {:key   :large
                             :value "Big"}
                            {:key   :xl
                             :value "Very big"}
                            {:key   :xxl
                             :value "Seriously Big!"}]}])

(defn cool-preview []
  (let [state (reagent/atom {:size :small})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer state descriptor]
       [rn/view {:padding-vertical 60
                 :align-items      :center}
        [quo2/account-avatar @state]]])))

(defn preview-account-avatar []
  [rn/view {:background-color (:ui-background @colors/theme)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])