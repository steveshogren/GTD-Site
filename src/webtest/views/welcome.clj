(ns webtest.views.welcome
  (:require [webtest.views.common :as common]
            [noir.response :as resps])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" {}
  (common/layout))

(defpage [:post "/update"] valus
  (let [resp (common/update-loan-with-response valus)]
    (resps/json resp)))

