(ns status-im.ui.screens.wallet.manage-connections.views
  (:require [quo.core :as quo]
            [quo.design-system.colors :as colors]
            [quo.react-native :as rn]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.constants :as constants]
            [status-im.ui.components.icons.icons :as icons]
            [status-im.ui.components.react :as react]
            [status-im.ui.screens.wallet-connect.session-proposal.views :refer [account-selector]]
            [status-im.ui.screens.wallet.manage-connections.styles :as styles]
            [status-im.utils.utils :as utils]))

(defn account-selector-sheet [{:keys [wc-version] :as session} visible-accounts show-account-selector active-account-atom]
  (let [{:keys [topic]} (when-not (= wc-version constants/wallet-connect-version-1) session)
        update-fn #(re-frame/dispatch (if (= wc-version constants/wallet-connect-version-1)
                                        [:wallet-connect-legacy/change-session-account session @active-account-atom]
                                        [:wallet-connect/change-session-account topic @active-account-atom]))]
    (when @show-account-selector
      [rn/view
       [account-selector visible-accounts active-account-atom update-fn]])))

(defn print-session-info [session visible-accounts show-account-selector]
  (let [peer-meta (get-in session [:params 0 :peerMeta])
        peer-id (get-in session [:params 0 :peerId])
        name (get peer-meta :name)
        url (get peer-meta :url)
        account (get-in session [:params 0 :accounts 0])
        icons (get peer-meta :icons)
        dapp-name (get-in session [:params 0 :peerMeta :name])
        dapp-url (get-in session [:params 0 :peerMeta :url])
        _ (re-frame/dispatch [:wallet-connect-legacy/save-session {:peer-id peer-id
                                                                   :connector (:connector session)
                                                                   :dapp-name dapp-name
                                                                   :dapp-url dapp-url}])
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
                                 :on-press #(swap! show-account-selector not)}
           [rn/text {:style styles/selected-account} (:name selected-account)]])]]]]))

(defn views []
  (let [legacy-sessions @(re-frame/subscribe [:wallet-connect-legacy/sessions])
        visible-accounts @(re-frame/subscribe [:visible-accounts-without-watch-only])]
    [rn/view {:margin-top 10}
     (doall (map-indexed (fn [idx session]
                           (let [account-addr (get-in session [:params 0 :accounts 0])
                                 active-account-atom (reagent/atom @(re-frame/subscribe [:account-by-address account-addr]))
                                 show-account-selector (reagent/atom false)]
                             [rn/view {:key idx}
                              [print-session-info session visible-accounts show-account-selector]
                              [account-selector-sheet session visible-accounts show-account-selector active-account-atom]]))
                         legacy-sessions))]))
