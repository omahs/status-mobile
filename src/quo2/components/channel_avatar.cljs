(ns quo2.components.channel-avatar
  (:require [quo2.foundations.colors :as colors]
            [quo.react-native :as rn]
            [clojure.string :as clojure-string]
            [status-im.ui.components.icons.icons :as icons]))

(defn channel-avatar [{:keys [big? dark? lock-status icon-color icon]}]
  (let [locked? (= :locked lock-status)
        lock-exists? (not= :none lock-status)]
    [rn/view {:style {:width (if big? 32 24)
                      :height (if big? 32 24)
                      :border-radius (if big? 32 24)
                      :justify-content :center
                      :align-items :center
                      :background-color (if dark?
                                          colors/neutral-70
                                          colors/neutral-30)}}
     [rn/view {:style {:width (if big? 20 12)
                       :height (if big? 20 12)
                       :display :flex
                       :justify-content :center
                       :align-items :center}}
      [icons/icon
       icon
       {:color (if (clojure-string/blank? icon-color)
                 "nil"
                 icon-color)
        :width (if big? 20 12)
        :height (if big? 20 12)}]
      (when lock-exists?
        [rn/view {:style {:position :absolute
                          :left (if big?
                                  12
                                  5)
                          :top (if big?
                                 12
                                 5)
                          :background-color (if dark?
                                              colors/neutral-90
                                              colors/white)
                          :border-radius 15
                          :padding 2}}
         [icons/icon (if locked?
                       :main-icons/locked12
                       :main-icons/unlocked12)
          {:color (if dark?
                    colors/neutral-40
                    colors/neutral-50)
           :width 16
           :height 16}]])]]))
