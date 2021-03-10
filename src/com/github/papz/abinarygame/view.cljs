(ns com.github.papz.abinarygame.view
  "this ns generates pure data that will be converted into HTML"
  (:require [clojure.string :as str]))

;; Having required google closure events we can handle events using closure
(defn CheatButton
  ""
  []
  [:button#cheat-btn.btn.btn-primary {:data-game-fn "cheat-btn"} "A Cheat Button 🚸"])

(defn StartButton
  ""
  []
  [:button#begin-btn.btn.btn-primary {:data-game-fn "begin-btn"} "Start"])

(defn PauseButton
  ""
  []
  [:button#pause-btn.btn.btn-primary {:data-game-fn "pause-btn"} "Stooop it"])

(defn FasterButton
  ""
  []
  [:button#faster-btn.btn.btn-primary "Go Faster!"])

(defn BinaryButton
  [problem-id pos-idx value]
  [:div.col-sm.col-1
   [:button.btn.btn-outline-light.btn-lg.bit-btn
    {:data-game-fn "bit-btn"
     :data-bit-val value
     :data-bit-problem-id problem-id
     :data-bit-pos-idx pos-idx}
    (if-not (str/blank? value)
      value
      "🐇")
    [:div.col-sm.col-1]]])

;; I think this starting to get a bit wrong - need better ways to handle events based on data-attrs..
;; the data structure needs to have the key of the element
  ;; Todo: destructuring
  ;; todo need an index Wed 03 Mar 2021 13:15:03 - to find the bit index, to switch it
(defn ProblemRow
  "The clickable button row to update the bits"
  [[problem-id {:keys [problem-binary problem-decimal solution]}]]
  [:div.row.binary-row
   (map-indexed #(BinaryButton problem-id %1 %2) problem-binary)
   [:div.col-sm.col-1.decimal-text problem-decimal]])

(defn ProblemRows
  ""
  [problems]
  [:div.contianer-fluid
   [:div.container
    (map ProblemRow problems)]])

(defn AppTemplate
  ""
  [app-state]
  [:div.container [:div.row.justify-content-center
                   [:div.col.md-6 [:h1 "It's The Binary Game 👘"]]
                   [:div.col.md-6 (StartButton) (PauseButton) (FasterButton)]]
   [:quote "Not quite like Jumanji or 🍳 🐇's"]
   (ProblemRows (:problems app-state))
   [:div.container.sticky-bottom [:footer.footer [:div (CheatButton)]]]])
