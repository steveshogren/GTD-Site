(ns webtest.views.welcome
  (:require [webtest.views.common :as common]
            [korma.db]
            [korma.core])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [korma.db],
        [korma.core]))

(defdb prod (mysql {:db "loansite"
                    :user ""
                    :password ""}))

(defentity payment)

(defpage "/welcome" []
  (common/layout
    [:p "Welcome to webtest"]))

(defpage "/my-page" []
  (common/site-layout
    [:h1 "welcome to my site!"]
    [:p "hope you like it."]))


(defpage "/todos" {}
  (common/layout
    [:h1 "Tasks"]
    [:p (common/todos-list (select payment (where {:user_id 17 :soft_delete 0})))]))

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
