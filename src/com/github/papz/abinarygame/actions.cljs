(ns com.github.papz.abinarygame.actions
  (:require [com.github.papz.abinarygame.state :as state]))

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
     :solution ["1" "1" "0" "1" "1" "1" "1" "1"]}}))

(defn cheat
  ""
  []
  (swap! state/binary-problems update-in [:problems] (fn [h] (dissoc h (last (keys h)))))
  (js/alert "Cmoooon, be better.."))
  

;; question not sure if this is right? use a function to set the level-stepper, should have be set!? or an atom
;; it's a global var, perhaps inside the state and then access it atomically? not sure how to update this state, perhaps reset!
(defn begin-game
  ""
  []
  (def level-stepper (js/setInterval
                      (fn [] (do
                               (swap! state/binary-problems update :problems conj (generate-problem))))                            
                      state/game-speed)))

;; Is this the right Event handler to use? do I use closures
(defn pause-game
  ""
  []
  (.clearInterval js/window level-stepper))

;; There may be problems https://stackoverflow.com/questions/9939760/how-do-i-convert-an-integer-to-binary-in-javascript
(defn user-turn
  ""
  [e]
  (let [game-id (.getAttribute e "data-bit-problem")
        bit-idx (.getAttribute e "data-bit-pos")
        bit-val (.getAttribute e "data-bit-val")]
    (js/console.log (str
                     "you changed the idx 777"
                     (get (:problems @state/binary-problems) bit-idx)
                     "-data-bit-pos" bit-idx))

    (swap! state/binary-problems update-in [:problems game-id :problem-binary]
           (fn [h]
             (js/console.log (str "wat-" (nth bit-idx 1)))
             (assoc h (js/parseInt (nth bit-idx 1)) (if (= bit-val "0") "1" "0"))))

    (if (= (get-in @state/binary-problems [:problems game-id :problem-binary])
           (get-in @state/binary-problems [:problems game-id :solution]))
      (do
        (js/alert (str "winner winner chicken dinner" game-id))
        (swap! state/binary-problems update-in [:problems] (fn [h] (dissoc h game-id)))))))
        ;; (js/console.log (str "the board" @state/binary-problems))))))
    ;; (render)))

(defn update-game
  ""
  [e]
  (let [clicked-button (.getAttribute (aget e "target") "data-game-fn")]
    (case clicked-button
      "cheat-btn" (cheat)
      "begin-btn" (begin-game)
      "pause-btn" (pause-game)
      "bit-btn" (user-turn (aget e "target")))))
    ;; (render)))
