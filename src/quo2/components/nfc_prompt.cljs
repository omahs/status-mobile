(ns quo2.components.nfc-prompt
  (:require [quo.react-native :as rn]
            [quo.theme :as theme]
            [quo2.foundations.colors :as colors]
            [status-im.ui.components.icons.icons :as icons]))

(def themes
  {:light {:default     {:bg     colors/white
                         :border colors/neutral-20
                         :icon   colors/neutral-50
                         :text   colors/black}}
   :dark  {:default     {:bg     colors/neutral-90
                         :border colors/neutral-70
                         :icon   colors/neutral-40
                         :text   colors/white}}})

(defn get-color [key]
  (get-in themes [(theme/get-theme) key]))

(defn nfc-prompt
  []
  [rn/view {:style {:height "100%"
                    :flex-direction "column-reverse"}}
   [rn/view {:style {:background-color colors/white
                    :width "100%" 
                    :justify-content :center
                    :align-items :center
                    :height 363}}
   [rn/view {:style {:flex-direction :row
                     :justify-content :center 
                     :align-items :center}}
    [rn/text {:style {:id "title" 
                      :font-size 26
                      :font-weight "400"
                      :color "#8F8E94"
                      :line-height 31
                      :text-align :center}}
     "Ready to Scan"]]
   [rn/view {:style {:justify-content :center
                     :align-items :center
                     :margin-top 29}}
    [icons/icon :main-icons/nfc-prompt-light {:width 114
                                              :color "nil"
                                              :height 114}]
     [rn/view {:style {:width 291
                       :height 95
                       :justify-content :center
                       :align-items :center}}
      [rn/text {:style {:font-size 16
                        :font-weight "400"
                        :line-height 20}}
       "Hold your iPhone near a Status Keycard"]]
     [rn/touchable-opacity {:style {:padding-vertical 18
                                    :padding-horizontal 10
                                    :align-items :center
                                    :justify-content :center
                                    :width 291
                                    :border-radius 10
                                    :background-color "#D5D4DB"
                                    :height 52}
                            :on-press (fn [] (prn "hey"))}
      [rn/text "Cancel"]]]]])