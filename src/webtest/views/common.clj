(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [clojure.math.numeric-tower]
        [hiccup.core]
        [net.cgrand.enlive-html]))

#_([clojure.Contrib.math :only [floor]])
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

(deftemplate index2 "html/template2.html"
  [loans]
  [:form#update] (content (map row-model loans))
  [:span#totalLoanAmount] (content (str (totalRemaining loans)))
  [:span#totalLoanAmount2] (content (str (totalRemaining loans)))
  [:span#percentageLeft1] (content (str (loanPayoffPercentage loans)))
  [:h2#cdg_h2] (content (str (loanPayoffPercentage loans))))


(defpartial layout [loans]
  (index2 loans))


