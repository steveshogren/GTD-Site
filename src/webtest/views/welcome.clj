(ns webtest.views.welcome
  (:require [webtest.views.common :as common]
            [korma.db]
            [korma.core])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [korma.db],
        [korma.core]))

(defdb prod (mysql {:db "gtd"
                    :user "root"
                    :password ""}))

(defentity tasks)

(defpage "/welcome" []
  (insert tasks
    (values {:task "first task"}))
  (common/layout
    [:p "Welcome to webtest"]))

(defpage "/my-page" []
  (common/site-layout
    [:h1 "welcome to my site!"]
    [:p "hope you like it."]))


(defpage "/todos" {}
  (common/layout
    [:h1 "Tasks"]
    [:p (common/todos-list (select tasks))]))

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
