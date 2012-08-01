(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [clojure.math.numeric-tower]
        [hiccup.core]
        [net.cgrand.enlive-html]))

(def loan-row-sel [[:form#update (nth-of-type 1)]])

(defsnippet row-model "html/template2.html" loan-row-sel
  [{:keys [loan_id amount max_amount description interest]}]
  [:input#loanName] (set-attr :value description)
  [:input#loanInterest] (set-attr :value interest)
  [:.table_max_amount_text] (content (str max_amount))
  [:input#loanAmount] (set-attr :value amount))

(defn totalRemaining [loans]
  (round (reduce + (map #(get % :amount) loans))))

(defn totalMaxRemaining [loans]
  (round (reduce + (map #(get % :max_amount) loans))))

(defn loanPayoffPercentage [loans]
  (round (* 100 (/ (totalRemaining loans) (totalMaxRemaining loans)))))

(defn thermometer-pixel [loans]
  (/ (* 300 (- 100 (loanPayoffPercentage loans))) 100))

(import '(java.text NumberFormat)
        '(java.util Locale))
(defn commify
  ([n] (commify n (Locale/US)))
  ([n locale]
     (.format (NumberFormat/getInstance locale) (bigdec n))))

(deftemplate index2 "html/template2.html"
  [loans]
  [:form#update] (content (map row-model loans))
  [:span#totalMaxAmount] (content (commify (totalMaxRemaining loans)))
  [:span#totalLoanAmount] (content (commify (totalRemaining loans)))
  [:#cdg_m] (set-attr :style (str "height: " (thermometer-pixel loans) "px;"))
  [:#cdg_p] (set-attr :style (str "margin-bottom: " (thermometer-pixel loans) "px;"))
  [:h2#cdg_h2] (content (str "Percent remaining: " (loanPayoffPercentage loans) "%")))


(defpartial layout [loans]
  (index2 loans))


