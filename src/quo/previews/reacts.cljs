(ns quo.previews.reacts
  (:require [reagent.core :as reagent]
            [quo.core :as quo]
            [status-im.ui.components.icons.icons :as icons] 
            [quo.react-native :as rn]
            [quo.design-system.colors :as colors]
            [quo.previews.preview :as preview]))

(defn render-item
  "Add your emoji as a param here"
  [{:keys [emoji clicks dark? neutral?]}] 
  (let [text-color (if dark? "white" "black")
        clicks-positive? (pos-int? @clicks)]
    [rn/touchable-opacity {:on-press #(swap! clicks inc)
                           :style {:display "flex"
                                   :flex-direction "row"
                                   :padding-vertical 8
                                   :padding-horizontal 8
                                   :background-color   (if dark?
                                                         (if neutral? "#192438" "black")
                                                         (if neutral? "#F0F2F5" "white"))
                                   :margin-top 25
                                   :border-radius 10
                                   :border-color (if dark? "white" "black")
                                   :border-width 1}}
     [quo/text {:style {:color text-color}}
      (str emoji (if clicks-positive?
                   (str " " @clicks)
                   ""))]]))

(defn preview-reacts []
  [rn/view {:background-color (:ui-background @colors/theme) 
            :display "flex" 
            :flex-direction "column"
            :align-items "center"}
   [render-item {:emoji "😛"
                 :clicks (reagent/atom 5)
                 :dark? false
                 :neutral? false}]
   [render-item {:emoji "😛"
                 :clicks (reagent/atom 100)
                 :dark? true
                 :neutral? false}]
   [render-item {:emoji "😛"
                 :clicks (reagent/atom 0)
                 :dark? false
                 :neutral? true}]
   [render-item {:emoji "😛"
                 :clicks (reagent/atom 9999)
                 :dark? true
                 :neutral? true}]])


