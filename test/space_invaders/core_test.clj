(ns space-invaders.core-test
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [space-invaders.core :as core]
            [midje.sweet :refer :all]))

(def state {:battleship {:x 0 :y 0}})

(facts "about one-move and one-neg-move"
  (fact "one-move is a positive number"
    (> core/one-move 0)
    => true)

  (fact "one-neg-move is a negative number"
    (< core/one-neg-move 0)
    => true)

  (fact "one-neg-move is the negation of one-move"
    (/ core/one-move core/one-neg-move)
    => -1))

(facts "about move-spaceship"
  (fact "about move-spaceship left"
    (:battleship (core/move-spaceship-left state))
    => {:x core/one-neg-move :y 0} )

  (fact "about move-spaceship right"
    (:battleship (core/move-spaceship-right state))
    => {:x core/one-move :y 0} )

  (fact "about move-spaceship up"
   (:battleship (core/move-spaceship-up state))
   => {:x 0 :y core/one-neg-move} )

  (fact "about move-spaceship down"
    (:battleship (core/move-spaceship-down state))
    => {:x 0 :y core/one-move} )

  (fact "about move-spaceship rand"
    (let [rand-x (rand 100)
          rand-y (rand 100)]
      (:battleship (core/move-spaceship rand-x rand-y state))
      => {:x rand-x :y rand-y}))

      (fact "about move-spaceship 1 2"
             (core/move-spaceship 1 2 state)
             => {:battleship {:x 1 :y 2}})

       (fact "about move-spaceship zero"
             (core/move-spaceship 0 0 state)
             => state)

       (fact "about move-spaceship zero"
             (:battleship (core/move-spaceship 0 0 state))
             => {:x 0 :y 0} )

       (fact "about move-spaceship negative"
             (core/move-spaceship -150 -200 state)
             => {:battleship {:x -150 :y -200}} ))

(def left-key-pressed  {:key :left})
(def right-key-pressed {:key :right})
(def other-key-pressed {:key :c})

(facts "about key-pressed"
  (fact "about other key pressed"
    (core/move-after-key-pressed state other-key-pressed)
    => {:battleship {:x 0 :y 0}})

  (fact "about left key pressed"
    (core/move-after-key-pressed state left-key-pressed)
    => {:battleship {:x core/one-neg-move :y 0}})

  (fact "about right key pressed"
    (core/move-after-key-pressed state right-key-pressed)
    => {:battleship {:x core/one-move :y 0}}))
