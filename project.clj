(defproject cljs-defasync "0.1.0-SNAPSHOT"
  :source-paths ["comp/clojurescript/src/clj"
     						 "comp/clojurescript/src/cljs"]
	:description "FIXME: write description"
	:url "http://example.com/FIXME"
	:license {:name "Eclipse Public License"
			:url "http://www.eclipse.org/legal/epl-v10.html"}
	:plugins [[lein-cljsbuild "0.3.2"]
            [org.bodil/lein-noderepl "0.1.10"]]
	:cljsbuild {
		:builds [{
			:source-paths ["src"]
			:compiler {
				:target :nodejs
				:optimizations :simple
				:pretty-print true}}]}
	:dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.nrepl "0.2.3"]
								 [org.clojure/core.async "0.1.0-SNAPSHOT"]])