(ns quo2.screens.switcher-cards
  (:require [quo.react-native :as rn]
            [reagent.core :as reagent]
            [quo.previews.preview :as preview]
            [quo2.foundations.colors :as colors]
            [quo2.components.switcher.card :as switcher-card]))

(def descriptor [{:label   "Type:"
                  :key     :type
                  :type    :select
                  :options [{:key   :communities-discover
                             :value "Communities Discover"}
                            {:key   :communities-engage
                             :value "Communities Engage"}
                            {:key   :messages
                             :value "Messages"}
                            {:key   :browser-discover
                             :value "Browser Discover"}
                            {:key   :browser-discover2
                             :value "Browser Discover2"} ;; TODO - Use different name (design)
                            {:key   :wallet
                             :value "Wallet"}
                            {:key   :messaging
                             :value "Messaging"}
                            {:key   :group-messaging
                             :value "Group Messaging"}
                            {:key   :community-card
                             :value "Community Card"}
                            {:key   :browser-card
                             :value "Browser Card"}
                            {:key   :wallet-card
                             :value "Wallet Card"}
                            {:key   :wallet-collectible
                             :value "Wallet Collectible"}
                            {:key   :wallet-graph
                             :value "Wallet Graph"}]}
                 {:label "Title"
                  :key   :title
                  :type  :text}
                 {:label "Last Message"
                  :key   :last-message
                  :type  :text}])

(defn cool-preview []
  (let [state (reagent/atom {:type         :messaging
                             :title        "Alisher Yakupov"
                             :last-message "This is fantastic! Ethereum"})]
    (fn []
      [rn/view {:margin-bottom 50
                :padding       16}
       [preview/customizer state descriptor]
       [rn/view {:padding-vertical 60
                 :align-items      :center}
        [switcher-card/card (:type @state) @state]]])))

(defn preview-switcher-cards []
  [rn/view {:background-color (colors/theme-colors colors/white colors/neutral-90)
            :flex             1}
   [rn/flat-list {:flex                      1
                  :keyboardShouldPersistTaps :always
                  :header                    [cool-preview]
                  :key-fn                    str}]])
