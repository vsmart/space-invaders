(ns space-invaders.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:battleship {:x (/ (q/width) 2) :y (* (q/height) 2/3)}})

(defn draw-state [state]
  (q/background 240)
  (q/fill 60 255 255)
  (q/rect (:x (:battleship state)) (:y (:battleship state)) 20 20)
  (q/fill 0 0 0)
  (q/text (str state) 10 20))

(defn move-spaceship [by-x by-y state]
  (let [new-state (update-in state [:battleship :x] + by-x)
        newer-state (update-in new-state [:battleship :y] + by-y)]
    newer-state))

(defn move-spaceship-left [state]
  (move-spaceship -1 0 state))

(defn move-spaceship-right [state]
  (move-spaceship 1 0 state))

(defn move-spaceship-up [state]
  (move-spaceship 0 -1 state))

(defn move-spaceship-down [state]
  (move-spaceship 0 1 state))

(defn move-after-key-pressed [state event]
  (let [key (:key event)]
    (cond
      (= key :left) (move-spaceship-left state)
      (= key :right) (move-spaceship-right state)
      :else state)))

(q/defsketch space-invaders
  :size [500 500]
  :setup setup
  :draw draw-state
  :key-pressed move-after-key-pressed
  :features [:keep-on-top]
  :middleware [m/fun-mode])
