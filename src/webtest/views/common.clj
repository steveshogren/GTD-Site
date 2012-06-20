(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [hiccup.core]))

(defpartial layout [& content]
  (html5
    [:head [:title "webtest"]
     (include-css "/css/reset.css")]
    [:body [:div#wrapper content]]))

(defpartial site-layout [& content]
  (html5
    [:head [:title "my site"]]
    [:body [:div#wrapper content]]))

(defpartial todo-item [{:keys [loan_id amount max_amount description interest]}]
   [:tr
    [:td (text-field "description" description)]
    [:td (text-field "interest" interest)]
    [:td (text-field "amount" amount)]
    [:td max_amount]])


(defpartial todos-list [items]
  [:table#table
   [:tr
    [:th "Loan Name"]
    [:th "Interest"]
    [:th "Amount"]
    [:th "Max"]]
   (map todo-item items)])

