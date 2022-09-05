(ns status-im.ui.screens.syncing.views
  (:require [quo.react-native :as rn]
            [status-im.ui.screens.syncing.styles :as styles]
            [status-im.i18n.i18n :as i18n]))
(defn views []
    [rn/view {:style styles/container-main}
     [rn/text {:style styles/synced-devices-text} (i18n/label :t/synced-devices)]
     [rn/view {:style styles/devices-container}]
;        [rn/image ]
    ])
