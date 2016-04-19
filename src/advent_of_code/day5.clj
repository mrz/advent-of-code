(ns advent-of-code.day5
  (:require [clojure.java.io :as io]))

;; --- Day 5: Doesn't He Have Intern-Elves For This? ---

;; Santa needs help figuring out which strings in his text file are naughty or
;; nice.

;; A nice string is one with all of the following properties:

;;     It contains at least three vowels (aeiou only), like aei, xazegov, or
;;     aeiouaeiouaeiou. It contains at least one letter that appears twice in a
;;     row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd). It does not
;;     contain the strings ab, cd, pq, or xy, even if they are part of one of
;;     the other requirements.

;; For example:

;;     ugknbfddgicrmopn is nice because it has at least three vowels
;;     (u...i...o...), a double letter (...dd...), and none of the disallowed
;;     substrings. aaa is nice because it has at least three vowels and a double
;;     letter, even though the letters used by different rules overlap.
;;     jchzalrnumimnmhp is naughty because it has no double letter.
;;     haegwjzuvuyypxyu is naughty because it contains the string xy.
;;     dvszwmarrgswjxmb is naughty because it contains only one vowel.

;; How many strings are nice?

;; --- Part Two ---

;; Realizing the error of his ways, Santa has switched to a better model of
;; determining whether a string is naughty or nice. None of the old rules apply,
;; as they are all clearly ridiculous.

;; Now, a nice string is one with all of the following properties:

;;     It contains a pair of any two letters that appears at least twice in the
;;     string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not
;;     like aaa (aa, but it overlaps).
;;     It contains at least one letter which repeats with exactly one letter
;;     between them, like xyx, abcdefeghi (efe), or even aaa.

;; For example:

;;     qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj)
;;     and a letter that repeats with exactly one letter between them (zxz).
;;     xxyxx is nice because it has a pair that appears twice and a letter that
;;     repeats with one between, even though the letters used by each rule
;;     overlap. uurcxstgmygtbstg is naughty because it has a pair (tg) but no
;;     repeat with a single letter between them. ieodomkazucvgmuy is naughty
;;     because it has a repeating letter with one between (odo), but no pair
;;     that appears twice.

;; How many strings are nice under these new rules?

(def vowels #{\a
              \e
              \i
              \o
              \u})

(def forbidden #{"ab"
                 "cd"
                 "pq"
                 "xy"})

(defn- is-vowel [letter]
  (if (vowels letter)
    1
    0))

(defn contains-at-least-3-vowels [input]
  (>= (reduce + (map is-vowel input)) 3))

(defn contains-at-least-1-contiguous-letter [input]
  (some true? (map = input (rest input))))

(defn does-not-contain-forbidden-sequences [input]
  (not (some forbidden (map str input (rest input)))))

(defn nice? [input]
  (every? true? ((juxt contains-at-least-3-vowels
                       contains-at-least-1-contiguous-letter
                       does-not-contain-forbidden-sequences) input)))

(defn pair-count [p s]
  (cond
    (empty? s) 0
    (.startsWith s p) (inc (pair-count p (.substring s 2)))
    :else (pair-count p (.substring s 1))))

(defn has-repeating-pairs [input]
  (let [pairs (set (map str input (rest input)))]
    (not (empty? (filter #(>= (pair-count % input) 2) pairs)))))

(defn contains-at-least-1-interspersed-letter [input]
  (some true? (map = input (drop 2 input))))

(defn nicer? [input]
  (every? true? ((juxt has-repeating-pairs
                       contains-at-least-1-interspersed-letter) input)))

(defn day-5-1 []
  (with-open [input (io/reader (io/resource "day-5"))]
    (count (filter nice? (line-seq input)))))

(defn day-5-2 []
  (with-open [input (io/reader (io/resource "day-5"))]
    (count (filter nicer? (line-seq input)))))
