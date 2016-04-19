(ns advent-of-code.core
  (:gen-class))

(require '[advent-of-code.day1 :as day1])
(require '[advent-of-code.day2 :as day2])
(require '[advent-of-code.day3 :as day3])
(require '[advent-of-code.day4 :as day4])
(require '[advent-of-code.day5 :as day5])

;;;;;

(defn -main
  [& args]
  (let [exercise (first args)]
    (case exercise
      "1" (println (day1/day-1-1) (day1/day-1-2))
      "2" (println (day2/day-2-1) (day2/day-2-2))
      "3" (println (day3/day-3-1) (day3/day-3-2))
      "4" (println (day4/day-4-1) (day4/day-4-2))
      "5" (println (day5/day-5-1) (day5/day-5-2))
      (println "lein run [1..5]"))))
