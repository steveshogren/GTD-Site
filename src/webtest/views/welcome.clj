(ns webtest.views.welcome
  (:require [webtest.views.common :as common])
  (:use [noir.core :only [defpage]]
        [noir.response :as response]
        [hiccup.core :only [html]]))

(defpage "/" {}
  (common/layout))

(defpage [:post "/update"] valus
  (let [resp (common/update-loan-with-response valus)
        replys {:payoffdate (str (get resp :payoff-date))
                    :totalloanamount (str (get resp :total))
                    :percentpaid (str (get resp :percent))
                    :marginlevel (str (get resp :thermometer))}]
    (response/json replys)))

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
