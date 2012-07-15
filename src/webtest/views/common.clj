(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [hiccup.core]
        [net.cgrand.enlive-html]))

(deftemplate index2 "html/template2.html"
  [ctxt]
  [:table#loanName] (content (:loanName ctxt)))

(defpartial layout [& content]
  (index2 {:loanName "testname!"}))

(defpartial site-layout [& content]
  (html5
    [:head [:title "my site"]]
    [:body [:div#wrapper content]]))

(defpartial todo-item [{:keys [loan_id amount max_amount description interest]}]
   [:tr
    [:td.tabletext (text-field "input_name" description)]
    [:td.table_interest (text-field "input_interest" interest)]
    [:td.tabletext (text-field "input_amount" amount)]
    [:td.table_max_amount_text max_amount]
     ])


(defpartial todos-list [items]
  [:table#table
   [:tr
    [:th "Loan Name"]
    [:th "Interest"]
    [:th "Amount"]
    [:th "Max"]]
   (map todo-item items)])

