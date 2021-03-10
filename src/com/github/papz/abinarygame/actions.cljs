(ns com.github.papz.abinarygame.actions
  (:require [com.github.papz.abinarygame.state :as state]
            [oops.core :refer [ocall oget]]
            [goog.functions :as gfunctions]))

(declare level-stepper game-speed)

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

(defn generate-problem
  "creates a binary problem row"
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
     :solution ["1" "1" "0" "1" "1" "1" "1" "1"]}}))

(defn cheat
  ""
  []
  (swap! state/binary-problems update-in [:problems] (fn [h] (dissoc h (last (keys h)))))
  (js/alert "Cmoooon, be better.."))

(def game-running? (atom true))

(defn- on-tick
  "this function runs continuously"
  []
  (when @game-running?
    (swap! state/binary-problems update :problems conj (generate-problem))
    (js/console.log (str "update - " @state/binary-problems))))

(def init-game-tick!
  (gfunctions/once
    (fn []
      (js/setInterval on-tick state/game-speed))))

(defn pause-game
  "pause the game loop"
  []
  (reset! game-running? false))

(defn restart-game!
  "re-start the game loop"
  []
  (reset! game-running? true))

;; There may be problems https://stackoverflow.com/questions/9939760/how-do-i-convert-an-integer-to-binary-in-javascript
(defn user-turn
  ""
  [e]
  (let [problem-id (.getAttribute e "data-bit-problem-id")
        pos-idx (int (.getAttribute e "data-bit-pos-idx"))
        bit-val (.getAttribute e "data-bit-val")]
    ; (js/console.log (str
    ;                  "you changed the idx 777"
    ;                  (get (:problems @state/binary-problems) bit-idx)
    ;                  "-data-bit-pos" bit-idx))
    (swap! state/binary-problems update-in [:problems problem-id :problem-binary pos-idx]
           (fn [n]
             (case n
               "1" "0"
               "0" "1"
               "0"))) ;; default to 0 if the user clicks on the bunny

             ; (js/console.log (str "wat-" (nth bit-idx 1)))
             ; (assoc h (js/parseInt (nth bit-idx 1)) (if (= bit-val "0") "1" "0"))))

    (let [app-state @state/binary-problems
          problem (get-in app-state [:problems problem-id])
          their-solution (:problem-binary problem)
          our-solution (:solution problem)]
      (when (= their-solution our-solution)
        (js/alert (str "winner winner chicken dinner" problem-id))
        (swap! state/binary-problems update-in [:problems] (fn [h] (dissoc h problem-id)))
        (js/console.log (str "the board" @state/binary-problems))))))

(defn click-app-el
  "this function fires when anything inside the app container is clicked"
  [js-evt]
  (let [target-el (oget js-evt "target")
        game-event (ocall target-el "getAttribute" "data-game-fn")]
    (case game-event
      "cheat-btn" (cheat)
      "begin-btn" (init-game-tick!)
      "pause-btn" (pause-game)
      "bit-btn" (user-turn target-el)
      nil)))
