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

(defn loan-list []
  (select loan
    (where {:user_id 17 :savings 0})
    (order :interest :DESC)
    (order :amount :DESC)))

(defn update-loan
  [loanId loanName loanInterest loanAmount]
  (update loan (set-fields {:description loanName :amount loanAmount :interest loanInterest}) (where {:loan_id loanId})))
