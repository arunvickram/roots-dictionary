(ns roots-dictionary.db
  (:require [reagent.core :as r]
            [datascript.core :as datascript]
            [re-posh.core :as re-posh]))

(def initial-db
  [{:db/id -1
    :db/ident :app/router}])

(def schema {:db/ident {:db/unique :db.unique/identity}})

(def conn (datascript/create-conn schema))

(re-posh/connect! conn)
