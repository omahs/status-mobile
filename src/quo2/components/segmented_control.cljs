(ns quo2.components.segmented-control
  (:require [reagent.core :as reagent]
            [quo.react-native :as rn]
            [quo2.components.tab :as tab]
            [quo2.foundations.colors :as colors]
            [quo.theme :as theme]))

(def themes {:light {:background-color colors/neutral-20}
             :dark  {:background-color colors/neutral-80}})

(defn segmented-control [{:keys [default-active on-change]}]
  (let [active-tab-id (reagent/atom default-active)]
    (fn [{:keys [data size]}]
      (let [active-id @active-tab-id]
        [rn/view {:flex-direction   :row
                  :background-color (get-in themes [(theme/get-theme) :background-color])
                  :border-radius    (case size
                                      32 10
                                      28 8
                                      24 8
                                      20 6)
                  :padding          2}
         (for [[indx {:keys [label id]}] (map-indexed vector data)]
           ^{:key id}
           [rn/view {:margin-left (if (= 0 indx) 0 2)
                     :flex 1}
            [tab/tab
             {:id        id
              :segmented true
              :size      size
              :active    (= id active-id)
              :on-press  #(do (reset! active-tab-id %)
                              (when on-change
                                (on-change %)))}
             label]])]))))
