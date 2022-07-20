(ns quo2.components.account-avatar
  (:require [quo.react-native :as rn]
            [quo2.foundations.colors :as colors]
            [status-im.ui.components.icons.icons :as icons]))

(def light-mode-color
  {:dark colors/purple-opa-60
   :light colors/purple-opa-20})

(def sizes
  {:xxl 80
   :xl 48
   :large 32
   :medium 24
   :small 20})

(def border-radii
  {:xxl 16
   :xl 12
   :large 10
   :medium 8
   :small 6})

(def inner-icon-sizes
  {:xxl 48
   :xl 24
   :large 20
   :medium 12
   :small 12})

(defn account-avatar
  [{:keys [size dark?]}]
  (let [icon-color (if dark?
                     (:dark light-mode-color)
                     (:light light-mode-color))
        avatar-size (size sizes)
        avatar-border-radius (size border-radii)
        inner-icon-size (size inner-icon-sizes)]
    [rn/view {:background-color icon-color
              :style {:width avatar-size
                      :height avatar-size
                      :border-radius avatar-border-radius
                      :justify-content :center
                      :align-items :center}}
     [icons/icon :main-icons/peach16
      {:color "nil"
       :container-style {:width  inner-icon-size
                         :height inner-icon-size}}]]))