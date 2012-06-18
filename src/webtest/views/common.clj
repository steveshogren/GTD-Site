(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial layout [& content]
  (html5
    [:head [:title "webtest"]
     (include-css "/css/reset.css")]
    [:body [:div#wrapper content]]))

(defpartial site-layout [& content]
  (html5
    [:head [:title "my site"]]
    [:body [:div#wrapper content]]))

(defpartial todo-item [{:keys [payment_id amount date_paid user_id soft_delete]}]
  [:li
   [:ul
    [:li "user_id: " user_id]
    [:li "payment_id: " payment_id]
    [:li "softdelete: " soft_delete]
    [:li "amount: " amount]]])

(defpartial todos-list [items]
  [:ul#todoItems ;; set the id attribute
   (map todo-item items)])

