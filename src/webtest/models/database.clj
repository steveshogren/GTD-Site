(ns webtest.models.database
  (:require [korma.db]
            [korma.core])
  (:use [korma.db]
        [clojure.math.numeric-tower]
        [korma.core]))

(defdb prod (mysql {:db "loansite"
                    :user "root"
                    :password ""}))
(defentity loan)
(defentity payment)

(defn sumKey [hash key]
  (round (reduce + (map #(get % key) hash))))

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

