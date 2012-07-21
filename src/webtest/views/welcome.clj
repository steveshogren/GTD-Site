(ns webtest.views.welcome
  (:require [webtest.views.common :as common]
            [korma.db]
            [korma.core])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [korma.db],
        [korma.core]))

(defdb prod (mysql {:db "loansite"
                    :user "root"
                    :password ""}))

(defentity loan)

(defpage "/welcome" []
  (common/layout
    [:p "Welcome to webtest"]))

#_(defpage "/my-page" []
  (common/site-layout
    [:h1 "welcome to my site!"]
    [:p "hope you like it."]))


(defpage "/todos" {}
  (common/layout (select loan (where {:user_id 17 :savings 0}) (order :interest :DESC) (order :amount :DESC))))

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
