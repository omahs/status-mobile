(ns quo2.components.reacts
  (:require [reagent.core :as reagent]
            [quo.core :as quo]
            [status-im.ui.components.icons.icons :as icons]
            [quo.react-native :as rn]
            [quo2.foundations.colors :as colors]
            [quo.design-system.colors :as ds-colors]))

(def reaction-styling
  {:display "flex"
   :flex-direction "row"
   :padding-vertical 3
   :padding-horizontal 8
   :margin-top 25
   :border-radius 10
   :gap 4})

(defn render-react
  "Add your emoji as a param here"
  [{:keys [emoji clicks dark? neutral?]}]
  (let [text-color (if dark? "white" "black")
        clicks-positive? (pos-int? @clicks)]
    [rn/touchable-opacity {:on-press #(swap! clicks inc)
                           :style (merge reaction-styling
                                         (cond-> {:background-color   (if dark?
                                                                        (if neutral? colors/neutral-70 colors/neutral-90)
                                                                        (if neutral? colors/neutral-30 colors/neutral-10))}
                                           (and dark? (not neutral?)) (assoc :border-color
                                                                             colors/neutral-70
                                                                             :border-width 1)
                                           (and (not dark?) (not neutral?)) (assoc :border-color colors/neutral-30
                                                                                   :border-width 1)))}
     [quo/text {:style {:color text-color}}
      (str emoji (if clicks-positive?
                   (str " " @clicks)
                   ""))]]))

(defn open-reactions-menu
  [{:keys [dark?]}]
  [rn/touchable-opacity {:on-press #(swap! dark? not)
                         :style (merge reaction-styling
                                       {:margin-top 25
                                        :border-width 1
                                        :border-color   (if @dark?
                                                          colors/white-opa-5
                                                          colors/neutral-80)})}
   [icons/icon :main-icons/add-reaction-emoji
    {:color (if @dark?
              "white"
              "black")}]])

(defn preview-reacts []
  [rn/view {:background-color (:ui-background @ds-colors/theme)
            :display "flex"
            :flex-direction "column"
            :align-items "center"}
   [render-react {:emoji "😛"
                  :clicks (reagent/atom 5)
                  :dark? false
                  :neutral? false}]
   [render-react {:emoji "😛"
                  :clicks (reagent/atom 100)
                  :dark? true
                  :neutral? false}]
   [render-react {:emoji "😛"
                  :clicks (reagent/atom 0)
                  :dark? false
                  :neutral? true}]
   [render-react {:emoji "😛"
                  :clicks (reagent/atom 9999)
                  :dark? true
                  :neutral? true}]
   [open-reactions-menu {:dark? (reagent/atom false)}]
   [open-reactions-menu {:dark? (reagent/atom true)}]])


