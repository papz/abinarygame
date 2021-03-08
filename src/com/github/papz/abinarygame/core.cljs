(ns com.github.papz.abinarygame.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [com.github.papz.abinarygame.glogger :as goog-log]
            [hiccups.runtime :as hiccupsrt]
            [goog.events :as events]
            [goog.events.EventType]
            [goog.dom :as dom]))

(declare level-stepper game-speed)

(def app-el (dom/getElement "app"))

(defn generate-problem
  ""
  ([]
   (let [rand-uuid (str (random-uuid))
         problem-decimal (rand-int 256)
         solution (gen-binary-solution problem-decimal)
         problem-binary (gen-binary-problem 1 (into [] solution))]
     {rand-uuid
      {:problem-decimal problem-decimal
       :problem-binary problem-binary
       :solution solution}}))
  ([x]
   {52
    {:problem-decimal 244
     :problem-binary ["1" "1" "0" "1" " " " " "1" "1"]
     :solution ["1" "1" "0" "1" "1" "1" "1" "1"]}})) ;; this needs to be decimal

;; #Todo: set up problem keys - move to simply state, a single state
(def binary-problems
  (atom {:problems {}}))

(def game-speed 4000)

;; Todo
(defn add-binary-problem [state]
  ""
  [])

(def counter (atom 0))

;; Todo: pass in the problem key
;; fix problem-row wasn't used
(defn solve-problem
  ""
  ([]
   (let [solved-problem (first @binary-problems)]
     (swap! @binary-problems (dissoc solved-problem))))
  ([problem-row]))

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
(defn binary-problem-view
  "The clickable buttons to update the bits"
  [id problem]
  ;; (goog-log/abinarygame (str "bin gen is " (gen-binary-solution x)))
  ;; Todo: destructuring
  ;; todo need an index Wed 03 Mar 2021 13:15:03 - to find the bit index, to switch it
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

;; Todo
(defn gen-binary-solution
  ""
  [x]
  (seq (.padStart (.toString x 2) 8 "0")))

;; later from a list of known IP address, the ordered
;; Todo: rand-nth wasn't producing randomly
(defn gen-binary-problem [n x]
  (if (= n 3)
    x
    (recur (inc n) (assoc x (rand (count x)) ""))))
;; (let [bin-pos [:128 :64 :32 :16 :8 :4 :2 :1]]

(def initial-bin-probs @binary-problems)

(defn step-binary-game
  ""
  [])

(defn cheat
  ""
  []
  (swap! binary-problems update-in [:problems] (fn [h] (dissoc h (last (keys h)))))
  (js/alert "Cmoooon, be better..")
  (render))

;; question not sure if this is right? use a function to set the level-stepper, should have be set!? or an atom
;; it's a global var, perhaps inside the state and then access it atomically? not sure how to update this state, perhaps reset!
(defn begin-game
  ""
  []
  (def level-stepper (js/setInterval
                      (fn [] (do
                               (swap! binary-problems update :problems conj (generate-problem))
                               (render)))
                      game-speed)))

;; Is this the right Event handler to use? do I use closures
(defn pause-game
  ""
  []
  (.clearInterval js/window level-stepper))

(defn user-turn
  ""
  [e]
  ;;  (goog-log/abinarygame (str "I have you now 999" e))
  (let [game-id (.getAttribute e "data-bit-problem")
        bit-idx (.getAttribute e "data-bit-pos")
        bit-val (.getAttribute e "data-bit-val")]


    (js/console.log (str
                     "you changed the idx 777"
                     (get (:problems @binary-problems) bit-idx)
                     "-data-bit-pos" bit-idx))

    (swap! binary-problems update-in [:problems game-id :problem-binary]
           (fn [h]
             (js/console.log (str "wat-" (nth bit-idx 1)))
             ;; There may be problems https://stackoverflow.com/questions/9939760/how-do-i-convert-an-integer-to-binary-in-javascript
             (assoc h (js/parseInt (nth bit-idx 1)) (if (= bit-val "0") "1" "0"))))

    (if (= (get-in @binary-problems [:problems game-id :problem-binary])
           (get-in @binary-problems [:problems game-id :solution]))
      (do
        (js/alert (str "winner winner chicken dinner" game-id))
        (swap! binary-problems update-in [:problems] (fn [h] (dissoc h game-id)))
        (js/console.log (str "the board" @binary-problems))))
    (render)))


;; I could have a hash that matches the data-game-fn to functions
(defn update-game
  ""
  [e]
  ;; (goog-log/abinarygame (str "You clicked" (.getAttribute (aget e "target") "data-game-fn")))
  (let [clicked-button (.getAttribute (aget e "target") "data-game-fn")]
    (case clicked-button
      "cheat-btn" (cheat)
      "begin-btn" (begin-game)
      "pause-btn" (pause-game)
      "bit-btn" (user-turn (aget e "target")))))


;; can we do better than a for
(defn grid-view
  ""
  []
  (html [:div.contianer-fluid [:div.container
                               (for [x (:problems @binary-problems)]
                                 [:div.row.binary-row
                                  (binary-problem-view (first x) (last x)) ;; whyyy first and last? individual HashMap
                                  [:div.col-sm.col-1.decimal-text (:problem-decimal (last x))]])]]))

(defn app-template
  ""
  []
  (html [:div.container [:div.row.justify-content-center
                         [:div.col.md-6 [:h1 "It's The Binary Game 👘"]]
                         [:div.col.md-6 (start-button) (pause-button) (faster-button)]]
         [:quote "Not quite like Jumanji or 🍳 🐇's"]
         (grid-view)]
        (html [:div.container.sticky-bottom [:footer.footer [:div (cheat-button)]]])))

;; use? goog.events.EventType.CLICK
(defonce init-listeners!
  (do
    (events/listen app-el "click" update-game)
    ;; (js/setInterval (fn [] (do (swap! counter inc) (js/console.log "counter" @counter))) 3000)
    (begin-game)
    (js/setTimeout (fn [] (js/console.log "probs " @binary-problems) 4000))))

;; Undecisive stuff, and learnings
(defn be-loud
  ""
  []
  ;; add listeners
  ;; (.addEventListener js/document (.getElementById js/document "add-lesson") show-me-element-fns)
  ;; (.setTimeout js/window (fn [] (js/alert "I did this")) 1000)
  ;;  (goog-log/abinarygame "my own logger 888")

  ;; load css from here?
  (js/console.log "head thing ")
  (js/console.log (first (.getElementsByTagName js/document "head")))
  (js/console.log "her is wat")
  (js/console.log (.getElementById js/document "wat"))
  ;; (js/console.log "what is binary rows" (@binary-problems :bit-rows))
  (js/console.log "what is binary rows" (@binary-problems :problems))
  (js/console.log (.getElementsByTagName js/document "body"))

  ;; app-template))
  (js/console.log (app-template))
  (js/console.log (first (.getElementsByTagName js/document "body")))

  (js/console.log "go on, whack your fruit then!")
  (js/console.log "how much do you know?s")
  ;; (js/console.log (.-innerHTML (.getElementById js/document "result")))

  (js/console.log "eeek"))

;; (.appendChild (.getElementById js/document "app")  (html [:span {:class "foo"} "bar"])))

;; (let [container (js/document.createElement "div")]
;;   (js/document.addElement)))

;; hot-reloading by hooking into the shadow-cljs lifecyle events

;; (defn initialize-listeners!
;;   (events/listen (dom/getElement "app") "click" ;; goog.events.EventType.CLICK
;;                  (fn [e]
;;                    (goog-log/abinarygame (str "You clicked" (.-target e) "-" (.-data e) "-" (.-idx e) "-"))
;;                    (goog-log/abinarygame (str "You clicked" (aget e "target" "id"))))))

;; should pass in state
(defn render
  ""
  []
  ;; 1. mount app on to HTML - could be better way
  (set! (.-innerHTML (.getElementById js/document "app"))
        (app-template)))

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
