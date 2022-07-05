(ns quo.previews.reacts
  (:require [reagent.core :as reagent]
            [quo.core :as quo]
            [quo.animated :as animated]
            [quo.react-native :as rn]
            [quo.design-system.colors :as colors]
            [quo.previews.preview :as preview]))

(def all-props (preview/list-comp [react-count   [1 2 3 4 5]
                                   dark?         [false true false true false]]
                                  {:count react-count
                                   :dark?   dark?}))

(defn render-item [{:keys [count dark?]}]
  (let [text-color (if dark? "white" "black")]
    [rn/view {:style {:display "flex"
                      :flex-direction "row"
                      :padding-horizontal 10
                      :padding-vertical 8 
                      :background-color   (if dark? "black" "white")
                      :width "100%"
                      :margin-top 25
                      :border-radius 8}}
     [quo/text {:style {:color text-color}}
      "😛  "]
     [quo/text {:style {:color text-color}}
      (str count)]]))

(defn preview-text []
  [rn/view {:background-color (:ui-background @colors/theme) 
            :display "flex" 
            :flex-direction "column"
            :align-items "center"}
   [rn/flat-list {:keyboardShouldPersistTaps :always 
                  :data                      all-props
                  :render-fn                 render-item
                  :key-fn                    str}]])


