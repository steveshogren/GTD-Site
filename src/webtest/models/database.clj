(ns webtest.models.database
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



