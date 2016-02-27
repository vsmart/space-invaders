(ns space-invaders.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:battleship
    {:x (/ (q/width) 2)
     :y (* (q/height) 2/3)}})

(defn draw-state [state]
 ; (q/background 240)
  (draw-battleship state)
  (print-current-state state))

(defn draw-battleship [state]
  (q/fill 120 255 255)
  (q/ellipse (:x (:battleship state)) (:y (:battleship state)) 35 15))

(defn print-current-state [state]
  (q/fill 0 0 0)
  (q/text (str state) 10 20))

(defn move-spaceship [by-x by-y state]
  (let [new-state (update-in state [:battleship :x] + by-x)
        newer-state (update-in new-state [:battleship :y] + by-y)]
    newer-state))

(defn move-spaceship-left [state]
  (move-spaceship one-neg-move 0 state))

(defn move-spaceship-right [state]
  (move-spaceship one-move 0 state))

(defn move-spaceship-up [state]
  (move-spaceship 0 one-neg-move state))

(defn move-spaceship-down [state]
  (move-spaceship 0 one-move state))

(defn fire [state]
  (loop [x (:x (:battleship state))
         y (:y (:battleship state))]
    (if (> 0 y)
      state
      (do
        (q/fill 200 255 255)
        (q/ellipse x y (/ y 10) (/ y 10))
        (recur x (- y 5))))))

(defn move-after-key-pressed [state event]
  (let [key (:key event)]
    (cond
      (= key :left) (move-spaceship-left state)
      (= key :right) (move-spaceship-right state)
      (= key :up) (fire state)
      :else state)))

(def one-move 20)
(def one-neg-move (* one-move -1))

(q/defsketch space-invaders
  :size [500 500]
  :setup setup
  :draw draw-state
  :key-pressed move-after-key-pressed
  :features [:keep-on-top]
  :middleware [m/fun-mode])
