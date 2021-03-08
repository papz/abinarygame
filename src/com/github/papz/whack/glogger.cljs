(ns com.github.papz.whack.glogger
  (:require
   [goog.log :as goog-log]
   [goog.debug.Console :as Console])
  (:import [goog.debug Console FancyWindow DivConsole]))


;; https://google.github.io/closure-library/api/goog.log.html
;; https://lambdaisland.com/blog/2019-06-10-goog-log
(.setCapturing (Console.) true)

(defn wack-logger
  ""
  ([]
   (goog-log/warning (goog-log/getLogger "com.github.papz.whack.glogger") "No info, ta!"))
  ([x]
   (goog-log/warning (goog-log/getLogger "com.github.papz.whack.glogger") x)))

;; other help https://github.com/binaryage/dirac/blob/6cf5cfaf28e335c3b7dc6379315f9af786a9d0fa/src/shared/dirac/shared/console.cljs
