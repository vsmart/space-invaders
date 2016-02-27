(ns space-invaders.core-test
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [space-invaders.core :as core]
            [midje.sweet :refer :all]))
(def state
  {:battleship {:x 0 :y 0}})

(facts "about move-spaceship"
  (fact "about move-spaceship left"
        (:battleship (core/move-spaceship-left state))
        => {:x -1 :y 0} )

  (fact "about move-spaceship right"
        (:battleship (core/move-spaceship-right state))
        => {:x 1 :y 0} )

  (fact "about move-spaceship up"
        (:battleship (core/move-spaceship-up state))
        => {:x 0 :y -1} )

  (fact "about move-spaceship down"
        (:battleship (core/move-spaceship-down state))
        => {:x 0 :y 1} )

  (fact "about move-spaceship rand"
    (let [rand-x (rand 100)
          rand-y (rand 100)]
       (:battleship (core/move-spaceship rand-x rand-y state))
        => {:x rand-x :y rand-y}))

  (fact "about move-spaceship zero"
        (core/move-spaceship 0 0 state)
        => state)

  (fact "about move-spaceship zero"
        (:battleship (core/move-spaceship 0 0 state))
        => {:x 0 :y 0} ))


