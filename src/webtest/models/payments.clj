(ns webtest.models.payments
  (use [webtest.models.dates]
       [webtest.models.database]
       [webtest.models.loans]
       [korma.db]
       [korma.core]
       [clojure.math.numeric-tower]))

(defn payment-list []
  (select payment
    (where {:user_id 17 :soft_delete 0})
    (order :date_paid :DESC)))

(defn oldest-payment-date [payments]
 (.getTime (get (last payments) :date_paid)))

(defn newest-payment-date [payments]
 (.getTime (get (first payments) :date_paid)))

(defn days-of-payment [payments]
  (round (/ (/ (/ (/ (- (newest-payment-date payments) (oldest-payment-date payments)) 1000) 60) 60) 24)))

(defn sum-payments [payments]
  (sumKey payments :amount))

(defn payment-per-day [payments]
 (/ (- (sum-payments payments) (get (last payments) :amount)) (days-of-payment payments)))

(defn payment-per-week [payments]
 (round (* (payment-per-day payments) 7)))

(defn payment-per-month [payments]
 (round (* (payment-per-day payments) 30)))

(defn payoff-date [payments loans]
  (today-plus (round (/ (totalRemaining loans) (payment-per-day payments)))))

