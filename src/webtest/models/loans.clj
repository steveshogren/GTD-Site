(ns webtest.models.loans
  (use [webtest.models.database]
       [korma.db]
       [korma.core]
       [clojure.math.numeric-tower]))

(defn totalRemaining [loans]
  (sumKey loans :amount))

(defn totalMaxRemaining [loans]
  (sumKey loans :max_amount))

(defn loanPayoffPercentage [loans]
  (round (* 100 (/ (totalRemaining loans) (totalMaxRemaining loans)))))

(defn create-loan
  [{:keys [name interest amount max_amount]}]
  #_(println (str "name: " name " inter: " interest " amount: " amount " max: " max_amount))
  (insert loan
    (values {:user_id 17 :description name :amount amount :interest interest :max_amount max_amount})))

(defn loan-list []
  (select loan
    (where {:user_id 17 :savings 0})
    (order :interest :DESC)
    (order :amount :DESC)))

(defn loan-by-id [id]
  (select loan
    (where {:loan_id id})))

(defn intn [val]
  (java.lang.Float/parseFloat val))

(defn update-loan
  [{:keys [loanAmount  loanName loanInterest loanId]}]
  (let [old-max (get (first (loan-by-id loanId)) :max_amount)
        new-max (if (> (intn loanAmount) old-max) loanAmount old-max)]
    (update loan
      (set-fields {:description loanName :amount loanAmount :interest loanInterest :max_amount new-max})
      (where {:loan_id loanId}))
    (first (loan-by-id loanId))))

(defn delete-loan [loanId]
  "Not deleting payments, because those are needed for averages"
  (delete loan
    (where {:loan_id loanId})))


