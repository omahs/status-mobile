(ns status-im.ui.screens.syncing.styles
  (:require [quo2.foundations.colors :as colors]))

(def synced-devices-text
  {:font-size 13
   :font-weight :500
   :color colors/neutral-40})

(def container-main
  {:margin 16})

(def devices-container
  {:border-color colors/neutral-20
   :border-radius 16
   :border-width 1
   })
