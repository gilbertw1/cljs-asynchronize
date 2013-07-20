# cljs-asynchronize

Asynchronize is a clojurescript macro that allows you to wrap arbitary blocks of code to execute that will treat
functions requiring callbacks as first class functions. This allows clojure code to be written in a serial fashion
without having to nest callback functions.

## Installation

In order to use Asynchronize you must import both core.async, the go macro, and the asynchronize macro itself.

```clojure
(:require [cljs.core.async])
(:require-macros [cljs-asynchronize.macros :as dm :refer [asynchronize]]
                 [cljs.core.async.macros :as am :refer [go]]))
```

## Usage

Use the 'asynchronize' macro to wrap blocks of code which contain callback based functions, replacing the callback parameter
with an elipsis '...':

```clojure
(asynchronize
  (def res (.get http "http://www.google.com" ...))
  (console/log res))
```

More examples can be found in [examples](https://github.com/gilbertw1/cljs-asynchronize/tree/master/examples)