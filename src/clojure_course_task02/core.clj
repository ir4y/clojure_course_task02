(ns clojure-course-task02.core
  (:gen-class))

(require '[clojure.java.io :as io])

(defn to-path [path file]
  (apply str path "/" file))

(defn get-file-list [path]
  (let [full-list (-> (io/file path) .list vec)
        file-list (filter #(-> (io/file %) .isDirectory not) full-list)
        dir-list (filter #(-> (io/file %) .isDirectory) full-list)]
    (if (empty? dir-list)
      file-list
      (mapcat #(get-file-list (to-path path %)) dir-list))))

(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  nil)

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
