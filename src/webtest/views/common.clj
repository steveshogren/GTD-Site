(ns webtest.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers]
        [hiccup.form-helpers]
        [webtest.models.payments]
        [webtest.models.database]
        [webtest.models.loans]
        [clojure.math.numeric-tower]
        [hiccup.core]
        [net.cgrand.enlive-html]))

(def loan-row-sel [[:form#update (nth-of-type 1)]])

; converts the percentage to match the -120 -> 0 range 
(defn bar-pixel [amount max-amount]
  (if (or (>= 0 amount) (>= 0 max-amount))
    -120
    (round (- (/ (* 120 (* 100 (/ amount max-amount))) 100) 120))))

(defsnippet row-model "html/template2.html" loan-row-sel
  [{:keys [loan_id amount max_amount description interest]}]
  [:input#loanName] (set-attr :value description)
  [:input#loanId] (set-attr :value loan_id)
  [:input#loanInterest] (set-attr :value interest)
  [:.table_max_amount_text] (content (str max_amount))
  [:.percentImage] (set-attr :style (str "background-position: "
                                         (bar-pixel amount max_amount)
                                         "px 0pt;"))
  [:input#loanAmount] (set-attr :value amount))


(defn thermometer-pixel [loans]
  (/ (* 300 (- 100 (loanPayoffPercentage loans))) 100))

(import '(java.text NumberFormat)
        '(java.util Locale))
(defn commify
  ([n] (commify n (Locale/US)))
  ([n locale]
     (.format (NumberFormat/getInstance locale) (bigdec n))))

(deftemplate index2 "html/template2.html"
  [loans payments total-max-remaining total-remaining]
  [:form#update] (content (map row-model loans))
  [:#averagePerWeek] (content (commify (payment-per-week payments)))
  [:#averagePerMonth] (content (commify (payment-per-month payments)))
  [:#payoffDate] (content (payoff-date payments loans))
  [:span#totalMaxAmount] (content (commify total-max-remaining))
  [:span#totalLoanAmount] (content (commify total-remaining))
  [:#cdg_m] (set-attr :style (str "height: " (thermometer-pixel loans) "px;"))
  [:#cdg_p] (set-attr :style (str "margin-bottom: " (thermometer-pixel loans) "px;"))
  [:h2#cdg_h2] (content (str "Percent remaining: " (loanPayoffPercentage loans) "%")))

(defpartial layout []
  (let [loans (loan-list)
        payments (payment-list)
        total-max-remaining (totalMaxRemaining loans)
        total-remaining (totalRemaining loans)]
    (index2 loans payments total-max-remaining total-remaining)))

(defn update-loan-with-response  [{:keys [loanAmount  loanName loanInterest loanId]}]
  #_(println loanId loanName loanInterest loanAmount)
  (update-loan loanId loanName loanInterest loanAmount)
  (let [loans (loan-list)
        payments (payment-list)
        total-max (totalMaxRemaining loans)
        total (totalRemaining loans)]
    {:payoff-date (payoff-date payments loans)
      :total (commify total)
      :percent (loanPayoffPercentage loans)
      :thermometer (thermometer-pixel loans)}))
