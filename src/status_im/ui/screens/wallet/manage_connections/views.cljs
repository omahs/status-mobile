(ns status-im.ui.screens.wallet.manage-connections.views
  (:require [quo.core :as quo]
            [quo.design-system.colors :as colors]
            [quo.react-native :as rn]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.ui.components.bottom-panel.views :as bottom-panel]
            [status-im.ui.components.icons.icons :as icons]
            [status-im.ui.components.react :as react]
            [status-im.ui.screens.wallet-connect.session-proposal.views :refer [app-management-sheet-view]]
            [status-im.ui.screens.wallet.manage-connections.styles :as styles]
            [status-im.utils.handlers :refer [<sub]]
            [status-im.utils.utils :as utils]))

(defn get-currently-selected-accounts
  [selected-tab]
  (-> selected-tab
      second
      :session
      :params
      first
      :accounts))

(defn account-selector-bottom-sheet [{:keys [session show-account-selector? dapp-name dapp-url peer-id idx]}]
  (reagent/create-class
   {:reagent-render
    (fn [{:keys [session show-account-selector? dapp-name dapp-url peer-id]}]
      (when @show-account-selector?
        [rn/view {:style (cond-> {:height 50}
                           (= idx 0) (assoc :margin-top 50))}
         [bottom-panel/animated-bottom-panel
          session
          app-management-sheet-view
          true]]))
    :component-did-update (fn [this old-argv]
                            (let [account-tab-changed? (not= (get-currently-selected-accounts old-argv)
                                                             (get-currently-selected-accounts (reagent/argv this)))]
                              (when account-tab-changed?
                                (re-frame/dispatch [:wallet-connect-legacy/save-session
                                                    {:peer-id peer-id
                                                     :connector (:connector session)
                                                     :dapp-name dapp-name
                                                     :dapp-url dapp-url}]))))}))

(defn print-session-info [{:keys [session visible-accounts show-account-selector?]}]
  (let [peer-meta (get-in session [:params 0 :peerMeta])
        peer-id (get-in session [:params 0 :peerId])
        name (get peer-meta :name)
        url (get peer-meta :url)
        account (get-in session [:params 0 :accounts 0])
        icons (get peer-meta :icons)
        icon-uri (first (status-im.utils.utils/exclude-svg-resources icons))
        selected-account (first (filter
                                 #(= account
                                     (:address %))
                                 visible-accounts))]
    ^{:key peer-id}
    [rn/view
     [:<>
      [rn/view {:style styles/app-row}
       [react/image {:style styles/dapp-icon :source {:uri icon-uri}}]
       [rn/view {:style styles/app-column}
        [quo/text {:style styles/dapp-name} name]
        [quo/text {:style styles/dapp-url} url]]

       [rn/view {:flex-direction :row
                 :position :absolute
                 :right 10
                 :align-items :center}
        [rn/touchable-opacity {:style styles/delete-icon-container
                               :on-press #(re-frame/dispatch [:wallet-connect-legacy/disconnect session])}

         [icons/icon :icons/delete {:width 20
                                    :height 20
                                    :container-style {:elevation 3}
                                    :color colors/red}]]
        (when selected-account ;; The account might not be available in theory, if deleted
          [rn/touchable-opacity {:style (styles/selected-account-container (:color selected-account))
                                 :on-press #(swap! show-account-selector? not)}
           [rn/text {:style styles/selected-account} (:name selected-account)]])]]]]))

(defn list-item [{:keys [session visible-accounts show-account-selector? dapp-name dapp-url peer-id]} idx]
  [rn/view
   [print-session-info {:session session
                        :visible-accounts visible-accounts
                        :show-account-selector? show-account-selector?}]
   [account-selector-bottom-sheet {:session session
                                   :show-account-selector? show-account-selector?
                                   :dapp-name dapp-name
                                   :dapp-url dapp-url
                                   :peer-id peer-id
                                   :idx idx}]])

(defn views []
  (let [legacy-sessions (<sub [:wallet-connect-legacy/sessions])
        visible-accounts (<sub [:visible-accounts-without-watch-only])
        items (doall (map (fn [session]
                     (let [show-account-selector? (reagent/atom false)
                           dapp-name (get-in session [:params 0 :peerMeta :name])
                           dapp-url (get-in session [:params 0 :peerMeta :url])
                           peer-id (get-in session [:params 0 :peerId])]
                       {:peer-id peer-id
                        :dapp-url dapp-url
                        :dapp-name dapp-name
                        :visible-accounts visible-accounts
                        :show-account-selector? show-account-selector?
                        :session session}))
                   legacy-sessions))]
     [rn/flat-list {:flex                      1
                    :keyboardShouldPersistTaps :always
                    :data                      items
                    :render-fn                 list-item
                    :key-fn                    str}]))
