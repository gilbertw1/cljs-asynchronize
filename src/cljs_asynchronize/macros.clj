(ns cljs-asynchronize.macros)

(defn- add-argument-last [form arg]
  `(~@form ~arg))

(defn- callback [sc fc]
  `(fn [err# res#]
    (cljs.core.async.macros/go
      (if err#
        (~'>! ~fc err#)
        (~'>! ~sc res#)))))

(defn- success-value-or-throw [sc fc]
  `(let [[v# c#] (~'alts! [~sc ~fc])]
      (try
        (if (= c# ~sc)
          v#
          (throw (js/Error. v#)))
        (finally
          (cljs.core.async/close! ~sc)
          (cljs.core.async/close! ~fc)))))

(defn- transform [forms]
  (if (list? forms)
    (if (= (last forms) '...)
      (let [sc (gensym) fc (gensym)]
        `(let [~sc (cljs.core.async/chan) ~fc (cljs.core.async/chan)]
           (do 
             ~(add-argument-last (map transform (butlast forms)) (callback sc fc))
             ~(success-value-or-throw sc fc))))
      (map transform forms))
    forms))

(defmacro asynchronize [& forms]
  (let [c (gensym)]
    `(cljs.core.async.macros/go
      ~@(map transform forms))))