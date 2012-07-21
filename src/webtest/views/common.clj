(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [hiccup.core]
        [net.cgrand.enlive-html]))

(def loan-row-sel [[:form#update (nth-of-type 1)]])

(defsnippet row-model "html/template2.html" loan-row-sel
  [{:keys [loan_id amount max_amount description interest]}]
  [:input#loanName] (set-attr :value description)
  [:input#loanInterest] (set-attr :value interest)
  [:.table_max_amount_text] (content (str max_amount))
  [:input#loanAmount] (set-attr :value amount))

(deftemplate index2 "html/template2.html"
  [loans]
  [:form#update] (content (map row-model loans)))

(defpartial layout [loans]
  (index2 loans))


