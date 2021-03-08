(ns com.github.papz.abinarygame.core
  (:require [com.github.papz.abinarygame.glogger :as goog-log]
            [com.github.papz.abinarygame.view :as view]
            [com.github.papz.abinarygame.actions :as actions]
            [com.github.papz.abinarygame.state :as state]
            [goog.events :as events]
            [goog.events.EventType]
            [goog.dom :as dom]))

(declare level-stepper game-speed)

(def app-el (dom/getElement "app"))

 ;; this needs to be decimal


;; Todo State game updates
(defn add-binary-problem [state]
  ""
  [])

(def counter (atom 0))

;; (defn solve-problem
;;   ""
;;   ([]
;;    (let [solved-problem (first @binary-problems)]
;;      (swap! @binary-problems (dissoc solved-problem))))
;;   ([problem-row]))

;; (let [bin-pos [:128 :64 :32 :16 :8 :4 :2 :1]]

(defn step-binary-game
  ""
  [])

;; use? goog.events.EventType.CLICK
(defonce init-listeners!
  (do
    (events/listen app-el "click" actions/update-game)
    (actions/begin-game)))

;; should pass in state
(defn render
  ""
  []
  ;; 1. mount app on to HTML - could be better way
  (set! (.-innerHTML (.getElementById js/document "app"))
        (view/app-template state/binary-problems)))

(defn main!
  "The entry point to whack your fruit"
  []
  (render))

;; add Listeners - defined once

;; then add a watch

(defn ^:dev/after-load start
  ""
  []
  (do
    (main!)))
