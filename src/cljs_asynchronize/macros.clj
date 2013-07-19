(ns cljs-defasynchronize.macros)

(defn- add-argument-last [form arg]
  `(~@form ~arg))

(defn- callback [c]
  `(fn [err# res#]
    (cljs.core.async.macros/go 
      (~'>! ~c res#))))

(defn- transform [forms c]
  (if (list? forms)
    (if (= (last forms) '...)
      (list 'do 
            (add-argument-last (map #(transform % c) (butlast forms)) (callback c))
            (list '<! c))
      (map #(transform % c) forms))
    forms))

(defmacro asynchronize [& forms]
  (let [c (gensym)]
    `(let [~c (cljs.core.async/chan)]
      (cljs.core.async.macros/go
        ~@(map #(transform % c) forms)))))