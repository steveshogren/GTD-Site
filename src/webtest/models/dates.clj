(ns webtest.models.dates
  (:import java.util.Calendar)
  (:import java.text.SimpleDateFormat)
  (:import java.util.Date))

(defn today-plus [days] (let [c (Calendar/getInstance)
                              sdf (new SimpleDateFormat "MM-dd-yyyy")]  
                    (.setTime c (new Date))
                    (.add c Calendar/DATE days)
                    (.format sdf (.getTime c))))



