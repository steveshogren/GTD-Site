(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [hiccup.core]))

(defpartial layout [& content]
  (html5
    [:head [:title "webtest"]
     (include-css "/css/stylesheet.css")]
    [:body [:div#wrapper content]]))

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
    [:td.tabletext (input "Update")]
    [:td.tabletext (input "Delete")]])


(defpartial todos-list [items]
  [:table#table
   [:tr
    [:th "Loan Name"]
    [:th "Interest"]
    [:th "Amount"]
    [:th "Max"]]
   (map todo-item items)])

