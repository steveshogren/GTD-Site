(ns webtest.views.welcome
  (:require [webtest.views.common :as common])
  (:use [noir.core :only [defpage]]
        [noir.response :as response]
        [hiccup.core :only [html]]))

(defpage "/" {}
  (common/layout))

(defpage [:post "/update"] {:keys [loanId loanName loanInterest loanAmount]}
  (let [response (common/update-loan-with-response loanId loanName loanInterest loanAmount)]
    (response/json {:payoffDate (get :payoff-date response)
                    :totalLoanAmount (get :total response)
                    :percentPaid (get :percent response)
                    :marginLevel (get :thermometer response)})))

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
