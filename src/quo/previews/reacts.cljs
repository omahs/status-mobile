(ns quo.previews.reacts
  (:require [reagent.core :as reagent]
            [quo.core :as quo]
            [status-im.ui.components.icons.icons :as icons]
            [quo.react-native :as rn]
            [quo.design-system.colors :as colors]))

(def reaction-styling
  {:display "flex"
   :flex-direction "row"
   :padding-vertical 8
   :padding-horizontal 8
   :margin-top 25
   :border-radius 10

   :border-width 1})

(defn render-react
  "Add your emoji as a param here"
  [{:keys [emoji clicks dark? neutral?]}]
  (let [text-color (if dark? "white" "black")
        clicks-positive? (pos-int? @clicks)]
    [rn/touchable-opacity {:on-press #(swap! clicks inc)
                           :style (merge reaction-styling
                                         {:border-color (if dark? "white" "black")
                                          :background-color   (if dark?
                                                                (if neutral? "#192438" "black")
                                                                (if neutral? "#F0F2F5" "white"))})}
     [quo/text {:style {:color text-color}}
      (str emoji (if clicks-positive?
                   (str " " @clicks)
                   ""))]]))

(defn open-reactions-menu
  [{:keys [is-open? bg-open bg-closed]}]
  [rn/touchable-opacity {:on-press #(swap! is-open? not)
                         :style (merge reaction-styling
                                       {:margin-top 25
                                        :background-color (if @is-open?
                                                            bg-open
                                                            bg-closed)})}
   [icons/icon :main-icons/add-reaction-emoji
    {:style {:color "black"
             :opacity (if @is-open? 0.5 1)}}]])

(defn preview-reacts []
  [rn/view {:background-color (:ui-background @colors/theme)
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
   [open-reactions-menu {:is-open? (reagent/atom true)
                         :bg-open "red"
                         :bg-closed "white"}]])


