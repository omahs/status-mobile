(ns quo2.components.switcher.styles
  (:require [quo.theme :as theme]
            [quo2.foundations.colors :as colors]))

(def colors-map
  {:secondary-container-bg-color   colors/white-opa-5
   :title-color                    colors/white
   :subtitle-color                 colors/white-opa-60
   :last-message-unread-text-color colors/white
   :last-message-read-text-color   colors/white-opa-40
   :close-button-bg-color          colors/neutral-80-opa-40
   :close-button-icon-color        colors/white})

(defn base-container [background-color]
  {:width            160
   :height           160
   :border-radius    16
   :background-color background-color})

(defn secondary-container []
  {:width            160
   :height           120
   :border-radius    16
   :bottom           0
   :position         :absolute
   :background-color (:secondary-container-bg-color colors-map)})

(defn title []
  {:position          :absolute
   :top               28
   :margin-horizontal 12
   :color             (:title-color colors-map)})

(defn title-props []
  {:size            :paragraph-1
   :weight          :semi-bold
   :number-of-lines 1
   :ellipsize-mode  :tail
   :style           (title)})

(defn subtitle []
  {:position          :absolute
   :top               50
   :margin-horizontal 12
   :color             (:subtitle-color colors-map)})

(defn subtitle-props []
  {:size            :paragraph-2
   :weight          :medium
   :style           (subtitle)})

(defn details-container [text?]
  {:position          :absolute
   :bottom            (if text? 10 12)
   :margin-horizontal 12})

(defn last-message-text [unread?]
  {:color (if unread?
            (:last-message-unread-text-color colors-map)
            (:last-message-read-text-color colors-map))})

(defn last-message-text-props [unread?]
  {:size            :paragraph-2
   :weight          :regular
   :number-of-lines 2
   :ellipsize-mode  :tail
   :style           (last-message-text unread?)})

(defn close-button []
  {:position         :absolute
   :right            8
   :top              8
   :background-color (:close-button-bg-color colors-map)
   :icon-color       (:close-button-icon-color colors-map)})

(defn close-button-props [on-press]
  {:size     24
   :type     :grey
   :icon     true
   :on-press on-press
   :style    (close-button)})

(defn avatar-container []
  {:width    48
   :height   48
   :left     12
   :top      12
   :position :absolute})
