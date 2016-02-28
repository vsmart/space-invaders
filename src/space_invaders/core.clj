(ns space-invaders.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 10)
  (q/color-mode :hsb)
  {:battleship
    {:x (/ (q/width) 2)
     :y (* (q/height) 4/5)}
   :fire-count 100
   :aliens
     [{:x 30  :y 140}
      {:x 60 :y 140}
      {:x 90 :y 140}]})

(defn draw-state [state]
  (q/background 0)
  (draw-battleship state)
  (draw-aliens state)
  (print-current-state state))

(defn draw-battleship [state]
  (q/fill 120 255 255)
  (q/ellipse (:x (:battleship state)) (:y (:battleship state)) 55 25))

(defn draw-aliens [state]
  (doseq [alien (:aliens state)] (draw-alien alien)))

(defn draw-alien [alien]
  (q/fill 40 255 255)
  (q/rect (:x alien) (:y alien) 20 20))

(defn print-current-state [state]
  (q/fill 250)
  (q/text (str state) 10 20))

(defn update-state [state]
  (move-aliens state))

(defn move-aliens [state]
  (if (> (:x (first (:aliens state))) (q/width))
   (move-aliens-to-left-border state)
   (move-aliens-one-step state)))

(defn move-aliens-to-left-border [state]
  (assoc state :aliens (map
    (fn [alien] (assoc-in alien [:x] - (q/width)))
    (:aliens state))))

(defn move-aliens-one-step [state]
  (assoc state :aliens (map
    (fn [alien] (update-in alien [:x] + 10))
    (:aliens state))))

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
         y (- (:y (:battleship state)) 50)]
    (if (> 20 y)
      (update-in state [:fire-count] dec)
      (do
        (q/fill 200 255 255)
        (q/ellipse (+ x (rand 10)) (+ y (rand 20)) (/ y 10) (/ y 10))
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
  :update update-state
  :key-pressed move-after-key-pressed
  :features [:keep-on-top]
  :middleware [m/fun-mode])
