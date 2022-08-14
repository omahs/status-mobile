(ns quo2.components.network-breakdown
  (:require [status-im.ui.components.icons.icons :as icons]
            [quo.react-native :as rn]
            [quo2.foundations.colors :as colors]))

(defn network-breakdown
  [{:keys [eth-value network-conversions]}]
  [rn/view {:style {:background-color colors/neutral-95
                    :padding 6}}
   [rn/view {:style {:border-bottom-width 1
                     :border-bottom-color colors/neutral-70
                     :padding-vertical 10}}
    [rn/text {:style {:font-size 18
                      :font-weight "600"
                      :color colors/white}}
     (str eth-value " ETH")]]
   [rn/scroll-view {:horizontal true
                    :style {:padding-vertical 18
                            :text-align :center}}
    (let [last-item-idx (-> network-conversions
                            count
                            dec)]
      (map-indexed
       (fn [idx {:keys [conversion network icon]}]
         [rn/view {:style {:flex-direction :row}
                   :key idx}
          [rn/view
           [rn/view {:style {:flex-direction :row
                             :align-items :center
                             :justify-content :space-between}}
            [rn/view {:flex-direction :row
                      :align-items :center}
             [rn/text {:style {:color colors/white}}
              [icons/icon icon
               {:width 12
                :color "nil"
                :height 12}]
              (str " " conversion)]]
            (when-not (= last-item-idx
                         idx)
              [rn/view {:style {:border-right-width 1
                                :margin-horizontal 8
                                :border-right-color colors/neutral-50
                                :height "50%"}}])]
           [rn/text {:style {:margin-left 12
                             :color colors/white}} " on " network]]])
       network-conversions))]])