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

(def loan-row-sel [:.loanRow])

; converts the percentage to match the -120 -> 0 range 
(defn bar-pixel
  ([{:keys [amount max_amount]}] (bar-pixel amount max_amount)) 
  ([amount max-amount] (if (or (>= 0 amount) (>= 0 max-amount))
                         -120
                         (round (- (/ (* 120 (* 100 (/ amount max-amount))) 100) 120))))) 

(defsnippet row-model "html/template2.html" loan-row-sel
  [{:keys [loan_id amount max_amount description interest]}]
  [:input#loanName] (set-attr :value description)
  [:input#loanId] (set-attr :value loan_id)
  [:td.deleteButton] (set-attr :id loan_id)
  [:input#loanInterest] (set-attr :value interest)
  [:.table_max_amount_text] (content (str max_amount))
  [:.table_max_amount_text] (set-attr :id (str "max_amount_" loan_id))
  [:.percentImage] (set-attr :style (str "background-position: "
                                         (bar-pixel amount max_amount)
                                         "px 0pt;")
                             :id (str "barpixel" loan_id))
  [:input#loanAmount] (set-attr :value amount))

(defn thermometer-pixel [loans]
  (/ (* 300 (- 100 (loanPayoffPercentage loans))) 100))

(import '(java.text NumberFormat) '(java.util Locale))
(defn commify
  ([n] (commify n (Locale/US)))
  ([n locale] (.format (NumberFormat/getInstance locale) (bigdec n))))

(deftemplate fetch-main-template "html/template2.html"
  [loans payments total-max-remaining total-remaining {:keys [main-content mattr]} {:keys [secondary sattr]}]
  #_(println (content main-content) )
  [:#updateBody] (content main-content)
  [:.tables] mattr 
  [:#secondary] (content secondary)
  [:#secondary] sattr 
  [:#secondarychart] sattr 
  [:#averagePerWeek] (content (commify (payment-per-week payments)))
  [:#averagePerMonth] (content (commify (payment-per-month payments)))
  [:#payoffDate] (content (payoff-date payments loans))
  [:span#totalMaxAmount] (content (commify total-max-remaining))
  [:span#totalLoanAmount] (content (commify total-remaining))
  [:#cdg_m] (set-attr :style (str "height: " (thermometer-pixel loans) "px;"))
  [:#cdg_p] (set-attr :style (str "margin-bottom: " (thermometer-pixel loans) "px;"))
  [:h2#cdg_h2] (content (str "Percent remaining: " (loanPayoffPercentage loans) "%")))

(defpartial main-layout []
  (let [loans (loan-list)
        payments (payment-list)
        total-max-remaining (totalMaxRemaining loans)
        total-remaining (totalRemaining loans)]
    (fetch-main-template loans payments total-max-remaining total-remaining
                         {:main-content (map row-model loans) :mattr (set-attr :test "test")}
                         {:secondary "" :sattr (set-attr :hidden "true")})))

(defn loan-ajax-response []
  (let [loans (loan-list)
        payments (payment-list)
        total-max (totalMaxRemaining loans)
        total (totalRemaining loans)]
    {:payoffdate (str (payoff-date payments loans))
     :total (str (commify total))
     :totalmax (str (commify total-max))
     :percent (str (loanPayoffPercentage loans))
     :paymentweek (str (payment-per-week payments))
     :paymentmonth (str (payment-per-month payments))
     :thermometer (str (thermometer-pixel loans))}))
 
(defn update-loan-with-response
  [loan-to-update]
  (let [changed-loan (update-loan loan-to-update)
        resp (loan-ajax-response)]
    (assoc resp
      :loanid (str (:loan_id changed-loan))
      :barpixel (str (bar-pixel changed-loan))
      :loanmax (str (:max_amount changed-loan)))))

(defn delete-loan-with-response
  [{:keys [loanId]}]
  (let [deleted-loan (delete-loan loanId)
        resp (loan-ajax-response)]
    (assoc resp
      :loanid (str loanId))))


