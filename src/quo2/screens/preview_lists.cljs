(ns quo2.screens.preview-lists
  (:require [quo.react-native :as rn]
            [reagent.core :as reagent]
            [quo.previews.preview :as preview]
            [status-im.react-native.resources :as resources]
            [quo2.foundations.colors :as colors]
            [status-im.ui.components.react :as react]
            [quo2.components.info-message :as quo2]))

(def descriptor [{:label   "Type:"
                  :key     :type
                  :type    :select
                  :options [{:key   :default
                             :value "Default"}
                            {:key   :success
                             :value "Success"}
                            {:key   :error
                             :value "Error"}]}
                 {:label   "Size:"
                  :key     :size
                  :type    :select
                  :options [{:key   :default
                             :value "Default"}
                            {:key   :tiny
                             :value "Tiny"}]}
                 {:label "Message"
                  :key   :message
                  :type  :text}])

(defn cool-preview []
  (let [state (reagent/atom {:type    :default
                             :size    :default
                             :icon    :main-icons2/placeholder
                             :message "This is a message"})]
    (fn []
      [rn/view {:margin-bottom 250
                :background-color :blue}
       [preview/customizer state descriptor]

       [rn/image {:width 400
                  :height 400
                  :position :absolute
                  :left 0
                  :resize-mode :cover
                  :top 0
                  :source  (resources/get-image :naruto)}]


       [rn/view {:style {:width 100
                         :height 100
                         :border-radius 50
                         :position :absolute
                         :top 180
                         :left 150
                         :background-color :red}}]
       [rn/view {:style {:width 120
                         :height 120
                         :position :absolute
                         :top      170
                         :left     200
                         :border-radius 60
                         :overflow :hidden}}
           [rn/image {:width 400
                      :height 400
                      :position :absolute
                      :left -200
                      :resize-mode :cover
                      :top -170
                      :source  (resources/get-image :naruto)}]]
       [rn/view {:style {:width 100
                         :height 100
                         :border-radius 50
                         :position :absolute
                         :top 180
                         :left 210
                         :background-color :yellow}}]])))

(defn preview-info-message []
  [rn/view {:background-color (colors/theme-colors colors/white colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
