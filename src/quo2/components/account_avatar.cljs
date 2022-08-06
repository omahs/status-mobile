(ns quo2.components.account-avatar
  (:require [quo.react-native :as rn]
            [quo2.foundations.colors :as colors]
            [status-im.ui.components.icons.icons :as icons]))

(def light-mode-color
  {:dark colors/customization-purple-60
   :light colors/customization-purple-50})

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
  {:xxl 36
   :xl 24
   :large 15
   :medium 11
   :small 11})

(defn account-avatar
  [{:keys [size dark? icon]}]
  (let [icon-color (if dark?
                     (:dark light-mode-color)
                     (:light light-mode-color))
        avatar-size (size sizes)
        avatar-border-radius (size border-radii)
        inner-icon-size (size inner-icon-sizes)]
    [rn/view {:style {:width avatar-size
                      :background-color icon-color
                      :height avatar-size
                      :border-radius avatar-border-radius
                      :justify-content :center
                      :align-items :center}}
     [icons/icon (keyword (str "main-icons/" icon))
      {:color "nil"
       :container-style {:width  inner-icon-size
                         :height inner-icon-size}}]]))