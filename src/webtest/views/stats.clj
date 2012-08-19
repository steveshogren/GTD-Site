(ns webtest.views.stats
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [webtest.models.payments]
        [webtest.views.common]
        [webtest.models.database]
        [clojure.data.json :only (json-str)]
        [webtest.models.loans]
        [clojure.math.numeric-tower]
        [hiccup.core]
        [noir.response :only [json]]
        [net.cgrand.enlive-html]))

(defpartial get-payment-row [payment]
  [:li
   [:h3 (str (:date_paid payment) (:amount payment))]])

(defpartial get-payments-list [payments]
  [:ul (map get-payment-row payments)])

(defpartial stats-layout []
  (let [loans (loan-list)
        payments (payment-list)
        total-max-remaining (totalMaxRemaining loans)
        total-remaining (totalRemaining loans)]
    (fetch-main-template loans payments total-max-remaining total-remaining
                         {:main-content "" :mattr (set-attr :hidden "true")}
                         {:secondary (str "var statData = "
                                          (json-str (summed-payments-by-month))
                                          ";")
                          :sattr (set-attr :class "tables")})))

