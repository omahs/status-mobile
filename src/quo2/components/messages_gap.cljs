(ns quo2.components.messages-gap
  (:require
   [oops.core :refer [oget]]
   [quo.react-native :as rn]
   [quo.theme :as theme]
   [quo2.components.icon :as icon]
   [quo2.components.text :as text]
   [quo2.foundations.colors :as colors]
   [reagent.core :as reagent]
   [status-im.i18n.i18n :as i18n]
   [status-im.ui.components.react :refer [pressable-class]]
   [status-im.utils.handlers :refer [>evt]]))

;;; helpers
(def themes
  {:light {:icon       colors/neutral-40
           :time       colors/neutral-50
           :background colors/neutral-5}
   :dark  {:icon       colors/neutral-60
           :time       colors/neutral-40
           :background colors/neutral-95}})

(defn get-color [key]
  (get-in themes [(theme/get-theme) key]))

(def ui-images
  {:light {:horizontal (js/require "../resources/images/ui/message-gap-hborder-light.png")
           :vertical   (js/require "../resources/images/ui/message-gap-vborder-light.png")
           :circles    (js/require "../resources/images/ui/message-gap-circle-bg-light.png")}
   :dark  {:horizontal (js/require "../resources/images/ui/message-gap-hborder-dark.png")
           :circles    (js/require "../resources/images/ui/message-gap-circle-bg-dark.png")}})

(defn get-image [key]
  (get-in ui-images [(theme/get-theme) key]))

;;; components
;;;; borders
(defn hborder [{:keys [type style]}]
  [rn/image {:source      (get-image :horizontal)
             :resize-mode :repeat
             :style       (merge {:position           :absolute
                                  :left               0
                                  :padding-horizontal 4
                                  :overflow           :hidden
                                  :width              "110%"
                                  :height             8
                                  :margin-left        -4}
                                 (if (= type :top)
                                   {:top 0}
                                   {:transform [{:rotateZ "180deg"}]
                                    :bottom    0})
                                 style)}])

(defn vborder [type body-height]
  (let [height @body-height
        img    (get-image :vertical)]
    (when (and img height)
      [rn/image {:source      img
                 :resize-mode :repeat
                 :style       (merge
                               {:position :absolute
                                :top      4
                                :height   (- height 8)
                                :width    4}
                               (if (= type :left)
                                 {:left 0}
                                 {:transform [{:rotate "180deg"}]
                                  :right     0}))}])))

;;;; others
(defn circle []
  [rn/view
   {:width         8
    :height        8
    :border-width  1
    :margin        4
    :flex          0
    :border-color  (get-color :icon)
    :border-radius 50}])

(defn timestamp [str]
  [text/text {:size  :label
              :style {:text-transform :none
                      :color          (get-color :time)}} str])

(defn info-button [on-press]
  [pressable-class
   {:on-press on-press}
   [icon/icon "message-gap-info" {:size 12 :no-color true :container-style {:padding 4}}]])

;;;; left/right
(defn left []
  [rn/view {:flex            0
            :padding-left    11.5
            :margin-right    20.5
            :align-items     :center
            :justify-content :space-between}
   [circle]
   [rn/image {:style {:flex 1} :source (get-image :circles) :resize-mode :repeat}]
   [circle]])

(defn right [timestamp-far timestamp-near chat-id gap-ids on-info-button-pressed]
  [rn/view {:flex 1}
   [rn/view
    {:flex-direction  :row
     :align-items     :center
     :justify-content :space-between
     :margin-right    2}
    [timestamp timestamp-far]
    (when on-info-button-pressed [info-button on-info-button-pressed])]

   [pressable-class
    {:style    {:flex 1 :margin-top 16 :margin-bottom 20}
     :on-press #(when (and chat-id gap-ids)
                  (>evt [:chat.ui/fill-gaps chat-id gap-ids]))}
    [text/text
     (i18n/label :messages-gap-warning)]]

   [timestamp timestamp-near]])

;;; main
(defn messages-gap
  "if `gap-ids` and `chat-id` are provided, press the main text area to fetch messages
  if `on-info-button-pressed` fn is provided, the info button will show up and is pressable"
  [{:keys [timestamp-far
           timestamp-near
           gap-ids
           chat-id
           on-info-button-pressed
           style]}]
  (let [body-height (reagent/atom nil)]
    (fn []
      [rn/view
       {:on-layout #(reset! body-height (oget % "nativeEvent.layout.height"))
        :overflow  :hidden}
       [hborder {:type :top}]
       [hborder {:type :bottom}]
       [rn/view (merge {:width            "100%"
                        :background-color (get-color :background)
                        :flex-direction   :row
                        :padding          20
                        :margin-vertical  4}
                       style)

        [left]
        [right timestamp-far timestamp-near chat-id gap-ids on-info-button-pressed]]
       [vborder :left body-height]
       [vborder :right body-height]])))
