(ns quo2.components.channel-avatar
  (:require [quo2.foundations.colors :as colors]
            [quo.react-native :as rn]
            [clojure.string :as clojure-string]
            [quo2.components.icon :as icons]))

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
       (cond->
        {:container-style {:width (if big? 20 12)
                           :height (if big? 20 12)}
         :size 20}
         (not (clojure-string/blank? icon-color)) (assoc :color icon-color)
         (clojure-string/blank? icon-color) (assoc :no-color true))]
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
                       :main-icons/locked
                       :main-icons/unlocked)
          {:color (if dark?
                    colors/neutral-40
                    colors/neutral-50)
           :container-style {:width 16
                             :height 16}
           :size 12
           :height 16}]])]]))
