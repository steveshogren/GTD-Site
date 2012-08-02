(ns webtest.views.welcome
  (:require [webtest.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
  (common/layout
    [:p "Welcome to webtest"]))

(defpage "/todos" {}
  (common/layout))

;(defpage [:post "/todos"] {:keys [title due]}
;  (if-let [todo-id (add-todo title due)]
;    (response/json {:id todo-id
;                    :title title
;                    :due-date due})
;    (response/empty)))

;(defpage "/todo/remove/:id" {todo-id :id}
;  (if (remove-todo todo-id)
;    (response/json {:id todo-id})
;    (response/empty)))
