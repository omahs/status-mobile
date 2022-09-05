(ns status-im.ui.screens.activity-center.views
  (:require [quo.react-native :as rn]
            [quo2.components.activity-logs :as activity-logs]
            [quo2.components.context-tags :as context-tags]
            [quo2.foundations.colors :as colors]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.i18n.i18n :as i18n]
            [status-im.ui.components.react :as react]
            [status-im.ui.components.topbar :as topbar]))

(defn render-notification
  [notification index]
  [rn/view {:flex           1
            :flex-direction :column
            :margin-top     (if (= 0 index) 0 4)}
   [activity-logs/activity-log {:button-1-label "Decline"
                                :button-1-type  :danger
                                :button-2-label "Accept"
                                :button-2-type  :primary
                                :context        [[context-tags/group-avatar-tag "Name" {:color          :purple
                                                                                        :override-theme :dark
                                                                                        :size           :small
                                                                                        :style          {:background-color colors/white-opa-10}
                                                                                        :text-style     {:color colors/white}}]
                                                 [rn/text {:style {:color colors/white}} "did something here."]]
                                :icon           :placeholder
                                :message        {:body (get-in notification [:message :content :text])}
                                :timestamp      "Today 00:00"
                                :title          "Activity Title"
                                :unread?        true}]])

(defn center []
  (reagent/create-class
   {:display-name        "activity-center"
    :component-did-mount #(do (re-frame/dispatch [:activity-center/fetch-notifications-read])
                              (re-frame/dispatch [:activity-center/fetch-notifications-unread]))
    :reagent-render
    (fn []
      (let [notifications-read @(re-frame/subscribe [:activity-center/notifications-read])]
        [react/keyboard-avoiding-view {:style         {:flex 1}
                                       :ignore-offset true}
         [topbar/topbar {:navigation {:on-press #(re-frame/dispatch [:navigate-back])}
                         :title      (i18n/label :t/activity)}]
         [rn/flat-list {:style     {:padding-horizontal 8}
                        :data      notifications-read
                        :key-fn    :id
                        :render-fn render-notification}]]))}))
