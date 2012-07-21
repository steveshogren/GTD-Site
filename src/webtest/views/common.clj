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
  [:input#loanAmount] (set-attr :value amount))

(deftemplate index2 "html/template2.html"
  [loans]
  [:form#update] (content (map row-model loans)))

(defpartial layout [loans]
  (index2 loans))

#_(defpartial site-layout [& content]
  (html5
    [:head [:title "my site"]]
    [:body [:div#wrapper content]]))

(defpartial todo-item
    [{:keys [loan_id amount max_amount description interest]}]
  [:tr
    [:td.tabletext (text-field "input_name" description)]
    [:td.table_interest (text-field "input_interest" interest)]
    [:td.tabletext (text-field "input_amount" amount)]
    [:td.table_max_amount_text max_amount]
     ])


#_(defpartial todos-list [items]
  [:table#table
   [:tr
    [:th "Loan Name"]
    [:th "Interest"]
    [:th "Amount"]
    [:th "Max"]]
   (map todo-item items)])

