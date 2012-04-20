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

(defpartial todo-item [{:keys [id task due]}]
  [:li [:h3 task]])

(defpartial todos-list [items]
  [:ul#todoItems ;; set the id attribute
   (map todo-item items)])

