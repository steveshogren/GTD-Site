(ns webtest.views.welcome
  (:require [webtest.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [korma.db]))

(defdb prod (mysql {:db "gtd"
                    :user "root"}))

(defentity tasks)

;(insert users
;  (values [{:task "get this working"}]))

(defpage "/welcome" []
  (common/layout
    [:p "Welcome to webtest"]
    [:p (insert tasks
      (values (:task "first task")))]))

(defpage "/my-page" []
  (common/site-layout
    [:h1 "welcome to my site!"]
    [:p "hope you like it."]))

(set all-todos [{:id "todo1"
                 :title "get milk"
                 :due "today"}])

(defpage "/todos" {}
  (let [items (all-todos)]
    (layout
      [:h1 "Todo list!"]
      (todos-list items))))

(defpage [:post "/todos"] {:keys [title due]}
  (if-let [todo-id (add-todo title due)]
    (response/json {:id todo-id
                    :title title
                    :due-date due})
    (response/empty)))

(defpage "/todo/remove/:id" {todo-id :id}
  (if (remove-todo todo-id)
    (response/json {:id todo-id})
    (response/empty)))
