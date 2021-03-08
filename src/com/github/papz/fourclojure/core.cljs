(ns com.github.papz.fourclojure.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccups]))


(defn init! []
  (let [args (js->clj js/process.argv)
        user-choice (nth args 2)]
    (println (pr-str "and we're alive and kicking" user-choice))))

(= 15 (reduce + [1 2 3 4]))

(js/console.log (html [:span {:class "foo"} "barrying"]))
(println (html [:span {:class "foo"} "barrying"]))
;; (= 2 (map))



(= 0 (reduce + []))
(=  6 (reduce * 1 [2 3]))

                                        ; #35
(= 7 (let [x 5] (+ 2 x)))
(= 7 (let [x 3, y 10] (- y x)))
(= 7 (let [x 21] (let [y 3] (/ x y))))
                                        ; #13
(= [20 30 40] (rest [10 20 30 40]))
                                        ; #17
(= [6 7 8] (map #(+ % 5) '(1 2 3)))
                                        ; 14
(= 8 ((fn add-five [x] (+ x 5)) 3))
(= 8 ((fn [x] (+ x 5)) 3))
(= 8 (#(+ % 5) 3))
(= 8 ((partial + 5) 3))

(zipmap [:a :b :c :e ] [1 2 3 4])


;; switch to repl in Emacs C-c M-z
(= [2 4] (let [[a b c d e] [0 1 2 3 4]] [c e]))

(def my-line [[3 5] [10 11]])

;; my own destructuring
(let [[p1 p2] my-line
      [x1 y1] p1]
  (println "I've got p1 here" x1 y1))
