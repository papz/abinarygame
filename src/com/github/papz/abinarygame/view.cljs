(ns com.github.papz.abinarygame.view
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccupsrt]))

;; Having required google closure events we can handle events using closure
(defn cheat-button
  ""
  []
  [:button#cheat-btn.btn.btn-primary {:data-game-fn "cheat-btn"} "A Cheat Button 🚸"])

(defn start-button
  ""
  []
  [:button#begin-btn.btn.btn-primary {:data-game-fn "begin-btn"} "Start"])

(defn pause-button
  ""
  []
  [:button#pause-btn.btn.btn-primary {:data-game-fn "pause-btn"} "Stooop it"])

(defn faster-button
  ""
  []
  [:button#faster-btn.btn.btn-primary "Go Faster!"])

;; I think this starting to get a bit wrong - need better ways to handle events based on data-attrs..
;; the data structure needs to have the key of the element
  ;; Todo: destructuring
  ;; todo need an index Wed 03 Mar 2021 13:15:03 - to find the bit index, to switch it
(defn binary-problem-view
  "The clickable button row to update the bits"
  [id problem]
  (let [id id
        problem-decimal (:problem-decimal problem)
        problem-binary (:problem-binary problem)
        solution (:solution problem)]
    (for [x (map-indexed vector problem-binary)]
      [:div.col-sm.col-1
       [:button.btn.btn-outline-light.btn-lg.bit-btn
        {:data-game-fn "bit-btn"
         :data-bit-val (last x)
         :data-bit-problem id
         :data-bit-pos x}
        (if (empty? (last x))
          "🐇"
          (last x))
        [:div.col-sm.col-1]]])))

(defn grid-view
  ""
  [problems row-view]
  (html [:div.contianer-fluid [:div.container
                               (for [x (:problems problems)]
                                 [:div.row.binary-row
                                  (row-view (first x) (last x)) ;; whyyy first and last? individual HashMap
                                  [:div.col-sm.col-1.decimal-text (:problem-decimal (last x))]])]]))

(defn app-template
  ""
  [binary-problems]
  (html [:div.container [:div.row.justify-content-center
                         [:div.col.md-6 [:h1 "It's The Binary Game 👘"]]
                         [:div.col.md-6 (start-button) (pause-button) (faster-button)]]
         [:quote "Not quite like Jumanji or 🍳 🐇's"]
         (grid-view binary-problems binary-problem-view)]
        (html [:div.container.sticky-bottom [:footer.footer [:div (cheat-button)]]])))

