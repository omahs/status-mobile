(ns status-im.activity-center.core
  (:require [re-frame.core :as re-frame]
            [status-im.data-store.activities :as data-store.activities]
            [status-im.ethereum.json-rpc :as json-rpc]
            [status-im.utils.fx :as fx]
            [taoensso.timbre :as log]))

(def notifications-per-page
  5)

(fx/defn fetch-notifications-read-success
  {:events [:activity-center/fetch-notifications-read-success]}
  [{:keys [db]} {:keys [cursor notifications]}]
  {:db (-> db
           (update-in [:activity-center :read] dissoc :loading?)
           (assoc-in [:activity-center :read :cursor] cursor)
           ;; (update-in [:activity-center :read :notifications]
           ;;            concat
           ;;            (map data-store.activities/<-rpc notifications))
           (assoc-in [:activity-center :read :notifications]
                     (map data-store.activities/<-rpc notifications)))})

(fx/defn fetch-notifications-read-error
  {:events [:activity-center/fetch-notifications-read-error]}
  [{:keys [db]} error]
  (log/warn "Failed to load Activity Center read notifications" error)
  {:db (update-in db [:activity-center :read] dissoc :loading?)})

(fx/defn fetch-notifications-read
  {:events [:activity-center/fetch-notifications-read]}
  [cofx]
  (let [cursor ""]
    {::json-rpc/call [{:method     "wakuext_readActivityCenterNotifications"
                       :params     [cursor notifications-per-page]
                       :on-success #(re-frame/dispatch [:activity-center/fetch-notifications-read-success %])
                       :on-error   #(re-frame/dispatch [:activity-center/fetch-notifications-read-error %])}]}))

(fx/defn fetch-notifications-unread-success
  {:events [:activity-center/fetch-notifications-unread-success]}
  [{:keys [db]} {:keys [cursor notifications]}]
  {:db (-> db
           (update-in [:activity-center :unread] dissoc :loading?)
           (assoc-in [:activity-center :unread :cursor] cursor)
           (update-in [:activity-center :unread :notifications]
                      concat
                      (map data-store.activities/<-rpc notifications)))})

(fx/defn fetch-notifications-unread-error
  {:events [:activity-center/fetch-notifications-unread-error]}
  [{:keys [db]} error]
  (log/warn "Failed to load Activity Center unread notifications" error)
  {:db (update-in db [:activity-center :unread] dissoc :loading?)})

(fx/defn fetch-notifications-unread
  {:events [:activity-center/fetch-notifications-unread]}
  [cofx]
  (let [cursor ""]
    {::json-rpc/call [{:method     "wakuext_unreadActivityCenterNotifications"
                       :params     [cursor notifications-per-page]
                       :on-success #(re-frame/dispatch [:activity-center/fetch-notifications-unread-success %])
                       :on-error   #(re-frame/dispatch [:activity-center/fetch-notifications-unread-error %])}]}))
