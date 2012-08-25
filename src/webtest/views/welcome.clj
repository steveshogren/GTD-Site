(ns webtest.views.welcome
  (:require [webtest.views.common :as common]
            [webtest.views.stats :as stats]
            [noir.response :as resps])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" {}
  (common/main-layout))

(defpage "/stats" {}
  (stats/stats-layout))

(defpage [:post "/update"] valus
  (let [resp (common/update-loan-with-response valus)]
    (resps/json resp)))

(defpage [:post "/delete"] valus
  (let [resp (common/delete-loan-with-response valus)]
    (resps/json resp)))

