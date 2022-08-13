(ns quo2.components.page-nav
  (:require [clojure.string :as string]
            [quo.react-native :as rn]
            [quo2.foundations.colors :as colors]
            [status-im.ui.components.icons.icons :as icons]
            [status-im.utils.dimensions :as dimensions]))

(def centrify
  {:display :flex
   :justify-content :center
   :align-items :center})

(def align-left
  (merge centrify
         {:align-items :flex-start}))

(defn mid-section
[{:keys [mid-section-type left-align? mid-section-description mid-section-description-color mid-section-description-user-icon mid-section-description-icon mid-section-main-text mid-section-right-icon mid-section-icon mid-section-main-text-icon-color mid-section-left-icon] :or {left-align? false}}]
[rn/view {:id "mid"
          :style (merge
                  (if left-align?
                    align-left
                    centrify)
                  {:flex 1})}
 (case mid-section-type
   :text-only [rn/text mid-section-main-text]
   :text-with-two-icons [rn/view (assoc
                                  centrify
                                  :flex-direction :row)
                         [icons/icon mid-section-left-icon
                          {:width 16
                           :height 16
                           :color mid-section-main-text-icon-color}]
                         [rn/text {:style
                                   {:padding-horizontal 4
                                    :font-size 15
                                    :font-weight "600"}}
                          mid-section-main-text]
                         [rn/view {:style {:font-size 15}}
                          [icons/icon mid-section-right-icon
                           {:width 16
                            :height 16
                            :color mid-section-main-text-icon-color}]]]
   :text-with-one-icon [rn/view (assoc centrify
                                       :flex-direction :row)
                        [rn/text {:style
                                  {:padding-horizontal 4
                                   :font-size 15
                                   :font-weight "600"}}
                         mid-section-main-text]
                        [rn/view {:style {:font-size 15}}
                         [icons/icon mid-section-icon
                          {:width 16
                           :height 16
                           :color mid-section-main-text-icon-color}]]]
   :text-with-description [rn/view (assoc centrify
                                          :flex-direction :row
                                          :margin-horizontal 2)
                           (when mid-section-description-user-icon
                             [rn/image {:source {:uri mid-section-description-user-icon}
                                        :style {:width 32
                                                :height 32
                                                :border-radius 32
                                                :margin-right 4}}]) 
                           [rn/view {:style {:flex-direction :column}}
                            [rn/text {:style
                                      {:font-size 15
                                       :font-weight "600"}}
                             mid-section-main-text]
                            (when mid-section-description
                              [rn/view {:style (assoc centrify :flex-direction :row)}
                               (when mid-section-description-icon
                                 [icons/icon mid-section-description-icon
                                  {:width 16
                                   :height 16
                                   :color mid-section-main-text-icon-color}])
                               [rn/text {:style
                                         {:padding-horizontal 4
                                          :font-size 13
                                          :font-weight "500"
                                          :line-height 18
                                          :color mid-section-description-color}}
                                mid-section-description]])]])])

(def icon-styles (merge
                  centrify
                  {:width 32
                   :height 32
                   :border-radius 10}))

(defn right-section-icon
  [props {:keys [bg icon icon-color]}]
  [rn/view {:style (merge
                    icon-styles
                    {:background-color bg}
                    props)}
   [icons/icon icon {:color icon-color}]])

(defn page-nav
  [{:keys [align-mid page-nav-color page-nav-background-uri mid-section-type mid-section-icon mid-section-main-text mid-section-left-icon
           mid-section-right-icon mid-section-description mid-section-description-color mid-section-description-icon mid-section-description-user-icon
           mid-section-main-text-icon-color left-section-icon left-section-icon-color
           left-section-icon-bg-color right-section-icons] :or {align-mid false
                                                                page-nav-color "red"
                                                                page-nav-background-uri ""
                                                                mid-section-type :text-with-description
                                                                mid-section-icon :wallet
                                                                mid-section-main-text "# general"
                                                                mid-section-left-icon :wallet
                                                                mid-section-right-icon :wallet
                                                                mid-section-description "Description"
                                                                mid-section-description-color "black"
                                                                mid-section-description-icon :placeholder20
                                                                mid-section-description-user-icon "https://i.picsum.photos/id/810/200/300.jpg?hmac=HgwlXd-OaLOAqhGyCiZDUb_75EgUI4u0GtS7nfgxd8s"
                                                                mid-section-main-text-icon-color "nil"
                                                                left-section-icon :peach20
                                                                left-section-icon-color "none"
                                                                left-section-icon-bg-color colors/neutral-30
                                                                right-section-icons [{:bg colors/primary-40
                                                                                      :icon-color "none"
                                                                                      :icon :main-icons/placeholder20}
                                                                                    ;;  {:bg colors/primary-40
                                                                                    ;;   :icon-color "none"
                                                                                    ;;   :icon :main-icons/placeholder20}
                                                                                    ;;  {:bg colors/primary-40
                                                                                    ;;   :icon-color "none"
                                                                                    ;;   :icon :main-icons/placeholder20}
                                                                                     ]}}]
  (let [{:keys [height width]} (dimensions/window) 
        put-middle-section-on-left? (or (true? align-mid)
                                        (> (count right-section-icons) 1))]
    [rn/view {:style
              (cond->
               {:display :flex
                :flex-direction :row
                :width width
                :height (* 0.075 height)
                :align-items :center
                :padding-horizontal 8
                :justify-content :space-between}
                (string/blank? page-nav-background-uri) (assoc :background-color page-nav-color)
                (string/blank? page-nav-color) (assoc :background page-nav-background-uri))}
     [rn/view {:id "leftsection"
               :style {:flex 1
                       :flex-direction :row
                       :align-items :center}}
      [rn/view {:style (merge
                        icon-styles
                        {:background-color left-section-icon-bg-color}
                        (when put-middle-section-on-left? {:margin-right 5}))}
       [icons/icon left-section-icon {:color left-section-icon-color}]]
      (when put-middle-section-on-left?
        [mid-section {:left-align? true
                      :mid-section-type mid-section-type
                      :mid-section-main-text mid-section-main-text
                      :mid-section-right-icon mid-section-right-icon
                      :mid-section-icon mid-section-icon
                      :mid-section-description mid-section-description
                      :mid-section-description-color mid-section-description-color
                      :mid-section-description-icon mid-section-description-icon
                      :mid-section-description-user-icon mid-section-description-user-icon
                      :mid-section-main-text-icon-color mid-section-main-text-icon-color
                      :mid-section-left-icon mid-section-left-icon}])]
     (when-not put-middle-section-on-left?
       [mid-section {:mid-section-type mid-section-type
                     :mid-section-main-text mid-section-main-text
                     :mid-section-right-icon mid-section-right-icon
                     :mid-section-icon mid-section-icon
                     :mid-section-main-text-icon-color mid-section-main-text-icon-color
                     :mid-section-left-icon mid-section-left-icon}])
     [rn/view {:id "rightsection"
               :style (assoc
                       (merge
                        centrify
                        {:flex-direction :row
                         :flex 1})
                       :justify-content :flex-end)}
      (let [last-icon-idx (- (count right-section-icons) 1)] 
        (map-indexed (fn [idx icon]
                       ^{:key idx}
                       [right-section-icon (when-not (= idx last-icon-idx)
                                             {:margin-right 8}) icon])
                     right-section-icons))]]))