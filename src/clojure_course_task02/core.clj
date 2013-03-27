(ns clojure-course-task02.core
  (:gen-class))

(require '[clojure.java.io :as io])

(defn get-file-list [directory]
  (let [full-list (vec (.listFiles directory))
        file-list (filter #(not (.isDirectory %)) full-list)
        dir-list (filter #(.isDirectory %) full-list)]
    (if (empty? dir-list)
      file-list
      (mapcat deref (map #(future (get-file-list %))  dir-list)))))

(defn filter-by-re [re-string array]
  (filter #(->> %  (re-find (re-pattern re-string)) (= nil) not) array))

(defn find-files [file-name-re path]
  (let [file-list (get-file-list (io/file path))]
    (filter-by-re file-name-re (map #(.getName %) file-list))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name-re path]
  (if (or (nil? file-name-re)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name-re " in " path "...")
      (dorun (map println (find-files file-name-re path))))))
