(ns webtest.views.welcome
  (:require [webtest.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to webtest"]))

(defpage "/my-page" []
  (common/site-layout
    [:h1 "welcome to my site!"]
    [:p "hope you like it."]))

(defpage "/todos" {}
  (let [items (all-todos)]
    (layout
      [:h1 "Todo list!"]
      (todos-list items))))
