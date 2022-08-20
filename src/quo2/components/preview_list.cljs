(ns quo2.components.preview-list
  (:require [quo.theme :as theme]
            [quo.react-native :as rn]
            [quo2.components.text :as text]
            [quo2.foundations.colors :as colors]
            [quo2.components.icon :as quo2.icons]))

(def themes
  {:light {:default      colors/neutral-40
           :success      colors/success-50
           :error        colors/danger-50}
   :dark  {:default      colors/neutral-60
           :success      colors/success-60
           :error        colors/danger-60}})

(defn get-color [key]
  (get-in themes [(theme/get-theme) key]))

(defn list-item [type item])

(defn preview-list
  "[preview-list type size data]
  opts
  {:type       :users/:communities/:accounts/:tokens/:collectibles/:dapps
   :size       32/24/16
   :data       list-data"
  ([type size data]
   (preview-list type size data nil))
  ([type size data style]
   (let [list-size (count data)] 
     [rn/view {:style (merge {:flex-direction :row
                              :flex           1} style)}
      (for [item (if (> list-size 4) (take 3 data) data)]
        ^{:key item}
        [list-item type item])
      (when (> list-item 4)
        [list-item :overflow-item {:label (- list-size 3)}])])))
