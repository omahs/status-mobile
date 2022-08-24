(ns quo2.components.switcher.card
  (:require [quo.react-native :as rn]
            [quo2.components.text :as text]
            [quo2.components.button :as button]
            [quo2.foundations.colors :as colors]
            [quo2.components.icon :as quo2.icons]
            [quo2.components.switcher.styles :as styles]))


;; Supporting Components

(defn last-message-view [content]
  [text/text (styles/last-message-text-props false) content])

;; Home Cards
(defn home-base-card [on-press]
  [rn/touchable-without-feedback {:on-press on-press}
   [rn/view {:style (styles/base-container colors/white-opa-5)}]])

(defn communities-discover [data]
  [:<>])

(defn communities-engage [data]
  [:<>])

(defn messages [data]
  [:<>])

(defn browser-discover [data]
  [:<>])

(defn browser-discover2 [data]
  [:<>])

(defn wallet [data]
  [:<>])


;; Screen Cards

;; TODO - support background banner

(defn screens-base-card [avatar title subtitle background-color on-press on-close last-message]
  [rn/touchable-without-feedback {:on-press on-press}
   [rn/view {:style (styles/base-container background-color)}
    (into
     [rn/view {:style (styles/secondary-container)}
      [text/text (styles/title-props) title]
      [text/text (styles/subtitle-props) subtitle]
      [rn/view {:style (styles/details-container true)}
       [last-message-view last-message]]])
    (when avatar
      [rn/view {:style (styles/avatar-container)}
       [avatar]])
    [button/button (styles/close-button-props on-close) :main-icons2/close]]])

(defn messaging [{:keys [title last-message]}]
  [screens-base-card nil title "Message" "#448EA299" #() #() last-message])

(defn group-messaging [data]
  [:<>])

(defn community-card [data]
  [:<>])

(defn browser-card [data]
  [:<>])

(defn wallet-card [data]
  [:<>])

(defn wallet-collectible [data]
  [:<>])

(defn wallet-graph [data]
  [:<>])
  

(defn card
  "[card type data]
  type
  data
  {}"
  [type data]
  (case type
    :communities-discover #(communities-discover data) ;; Home Card
    :communities-engage   #(communities-engage data)   ;; Home Card
    :messages             #(messages data)             ;; Home Card
    :browser-discover     #(browser-discover data)     ;; Home Card
    :browser-discover2    #(browser-discover2 data)    ;; Home Card
    :wallet               #(wallet data)               ;; Home Card
    :messaging            #(messaging data)            ;; Messaging Card
    :group-messaging      #(group-messaging data)      ;; Group Messaging Card
    :community-card       #(community-card data)       ;; Community Card
    :browser-card         #(browser-card data)         ;; Browser Card
    :wallet-card          #(wallet-card data)          ;; Wallet Card
    :wallet-collectible   #(wallet-collectible data)   ;; Wallet Card
    :wallet-graph         #(wallet-graph data)))       ;; Wallet Card
