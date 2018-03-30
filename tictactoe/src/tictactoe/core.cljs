(ns tictactoe.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn new-board [n]
(vec (repeat n (vec (repeat n "B")))))

(defonce app-state 
  (atom {:name "TIC TAC TOE GAME"
         :board (new-board 3)}))

(defn computer-move []
(swap! app-state assoc-in [:board 0 0] "C"))

(defn blank [x y]
  [:rect 
    {:width 0.9
    :height 0.9
    :fill (if (zero? (get-in @app-state [:board x y])) "#eee" "#ccc")
    :x (+ 0.05 x)
    :y (+ 0.05 y)
    :on-click
    (fn react[e] 
    (swap! app-state assoc-in [:board x y] "P")
    (computer-move))}
  ]
)

(defn circle [x y]
  [:circle 
  {:r 0.36
  :fill "lightblue"
  :cx (+ 0.5 x)
  :cy (+ 0.5 y)}]
)

(defn cross [x y] 
  [:g {:stroke "pink"
      :stroke-width 0.5
      :stroke-linecap "round"
      :transform
      (str "translate(" (+ 0.5 x) "," (+ 0.5 y) ") "
            "scale(0.35)")}
  [:line {:x1 -0.6 :y1 -0.6 :x2 0.6 :y2 0.6}]
  [:line {:x1 0.6 :y1 -0.6 :x2 -0.6 :y2 0.6}]
  ])

(defn tictactoe []
  [:center 
    [:h1 (:name @app-state)]
    (into
    [:svg
    {:view-box "0 0 3 3"
    :width 500 
    :height 500}]
    (for [x (range (count (:board @app-state)))
          y (range (count (:board @app-state)))]
          (case (get-in @app-state [:board x y])
          "B" [blank x y]
          "P" [circle x y]
          "C" [cross x y])))
    [:pink
      [:button
        {:on-click (
          fn new-game-click [e] 
             (swap! app-state assoc :board (new-board 3)))}
            "New Game"]
    ]
  ]
)

(reagent/render-component [tictactoe]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
(swap! app-state assoc-in [:board 0 0] 2)
)
