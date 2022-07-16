(ns quo2.screens.reacts
  (:require [quo.react-native :as rn]
            [quo.previews.preview :as preview]
            [reagent.core :as reagent]
            [quo2.components.reacts :as quo2]
            [quo.design-system.colors :as colors]))

(def descriptor [{:label "Count"
                  :key   :clicks
                  :type  :text}
                 {:label "Emoji"
                  :key   :emoji
                  :type  :text}
                 {:label "Dark"
                  :key   :dark?
                  :type  :boolean}
                  {:label "Neutral"
                   :key   :neutral?
                   :type  :boolean}])

(defn cool-preview []
  (let [state (reagent/atom {})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer state descriptor]
       [rn/view {:padding-vertical 60
                 :align-items      :center}
        [quo2/render-react @state]]])))

(defn preview-reacts []
  [rn/view {:background-color (:ui-background @colors/theme)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
