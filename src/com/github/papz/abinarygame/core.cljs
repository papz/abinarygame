(ns com.github.papz.abinarygame.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [com.github.papz.abinarygame.glogger :as goog-log]
            [com.github.papz.abinarygame.view :as view]
            [com.github.papz.abinarygame.actions :as actions]
            [com.github.papz.abinarygame.state :as state]
            [goog.events :as events]
            [goog.functions :as gfunctions]
            [hiccups.runtime :as hiccupsrt]
            [goog.events.EventType]
            [goog.dom :as dom]
            [oops.core :refer [ocall oset!]]))

(defn on-refresh
  "this function is called everytime shadow-cljs does a hot module reload"
  []
  (js/console.log "FIXME: make this refresh"))
  ; (do
  ;   (main!)))

(def app-el (dom/getElement "app"))

(defn render!
  "update the innerHTML of the application"
  []
  (let [app-html (html (view/AppTemplate @state/binary-problems))]
    (oset! app-el "innerHTML" app-html)))

(def init-listeners!
  (gfunctions/once
    (fn []
      (events/listen app-el "click" actions/click-app-el)
      (actions/init-game-tick!))))

(def main!
  "The entry point to whack your fruit"
  (gfunctions/once
    (fn []
     (js/console.log "initialize the app!")
     (add-watch state/binary-problems :render (fn [_key _ref old new]
                                                (when (not= old new)
                                                 (render!))))
     (init-listeners!)
     (actions/init-game-tick!)
     (render!))))

(ocall js/window "addEventListener" "load" main!)
