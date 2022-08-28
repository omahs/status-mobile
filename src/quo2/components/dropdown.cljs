(ns quo2.components.dropdown
  (:require [quo.react-native :as rn]
            [quo2.components.icon :as icons]
            [quo2.components.text :as text]
            [quo2.foundations.colors :as colors]
            [quo2.reanimated :as reanimated]
            [reagent.core :as reagent]))

(defn apply-anim [dd-height val]
  (reanimated/animate-shared-value-with-delay dd-height
                                              val
                                              300
                                              :easing1
                                              0))

(def sizes {:big {:icon-size 20
                  :font {:font-size :paragraph-1}
                  :height 40
                  :padding {:padding-with-icon {:padding-top 9
                                                :padding-bottom 9
                                                :padding-left 12
                                                :padding-right 12}
                            :padding-with-no-icon {:padding-top 9
                                                   :padding-bottom 9
                                                   :padding-left 16
                                                   :padding-right 12}}}
            :medium {:icon-size 20
                     :font {:font-size :paragraph-1}
                     :height 32
                     :padding {:padding-with-icon {:padding-top 5
                                                   :padding-bottom 5
                                                   :padding-horizontal 8}
                               :padding-with-no-icon {:padding-top 5
                                                      :padding-bottom 5
                                                      :padding-left 12
                                                      :padding-right 8}}}
            :small {:icon-size 12
                    :font {:font-size :label}
                    :height 24
                    :padding {:padding-with-icon {:padding-vertical 3
                                                  :padding-left 6
                                                  :padding-right 6}
                              :padding-with-no-icon {:padding-vertical 3
                                                     :padding-horizontal 8}}}})
(defn color-by-10
  [color]
  (colors/alpha color 0.6))

(defn dropdown-comp [{:keys [icon open? text dd-height size disabled? dd-color border-color]}]
  (let [dark? (colors/dark?)
        {:keys [width height width-with-icon padding font icon-size]} (get-in sizes [size])
        {:keys [padding-with-icon padding-with-no-icon]} padding
        font-size (:font-size font)
        spacing 4]
    [rn/touchable-opacity (cond-> {:on-press (fn []
                                       (if (swap! open? not)
                                         (apply-anim dd-height 120)
                                         (apply-anim dd-height 0)))
                           :style (cond-> (merge
                                   (if icon
                                     padding-with-icon
                                     padding-with-no-icon)
                                   {:width (if icon
                                             width-with-icon
                                             width)
                                    :height height
                                    :border-radius (if (= :small size)
                                                     8
                                                     10)
                                    :flex-direction :row
                                    :align-items :center
                                    :border-width 1
                                    :border-color (if @open?
                                                    border-color
                                                    (color-by-10 border-color))
                                    :background-color (if @open?
                                                        dd-color
                                                        (color-by-10 dd-color))})
                                    disabled? (assoc :opacity 0.3))}
                            disabled? (assoc :disabled true))
     (when icon
       [icons/icon icon {:no-color true
                         :size 20
                         :container-style {:margin-right spacing
                                           :margin-top 1
                                           :width icon-size
                                           :height icon-size}}])
     [text/text {:size font-size
                 :weight :medium
                 :font :font-medium
                 :color :main
                 :style {:margin-right spacing}} text]
     [icons/icon (if @open?
                   (if dark?
                     :main-icons/pullup
                     :main-icons/pullup-black)
                   (if dark?
                     :main-icons/dropdown-white
                     :main-icons/dropdown-black))
      {:no-color true
       :size 20
       :container-style {:width icon-size
                         :margin-top 1
                         :height icon-size}}]]))

(defn items-comp
  [{:keys [items on-select]}]
  (let [items-count (count items)]
    [:f>
     (fn []
       [rn/scroll-view {:style {:height "100%"}
                        :horizontal false
                        :nestedScrollEnabled true}
        (doall
         (map-indexed (fn [idx item]
                        [rn/touchable-opacity
                         {:key (str item idx)
                          :style {:padding 4
                                  :border-bottom-width (if (= idx (- items-count 1))
                                                         0
                                                         1)
                                  :border-color (colors/theme-colors
                                                 colors/black
                                                 colors/white)
                                  :text-align :center}
                          :on-press #(on-select item)}
                         [text/text {:style {:text-align :center}} item]]) items))])]))

(defn dropdown [{:keys [items icon text default-item on-select size disabled? border-color dd-color]}]
  [:f>
   (fn []
     (let [open? (reagent/atom false)
           dd-height (reanimated/use-shared-value 0)]
       [rn/view {:style {:flex-grow 1}}
        [dropdown-comp {:items items
                        :icon icon
                        :disabled? disabled?
                        :size size
                        :dd-color dd-color
                        :text text
                        :border-color border-color
                        :default-item default-item
                        :open? open?
                        :dd-height dd-height}]
        [reanimated/view {:style
                          (reanimated/apply-animations-to-style
                           {:height dd-height}
                           {:height dd-height})}
         [items-comp {:items items
                      :on-select on-select}]]]))])