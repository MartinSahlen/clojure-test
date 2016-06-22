(ns martin.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]
            [schema.core :as s]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(defapi app
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Simple"
                    :description "Compojure Api example"}
             :tags [{:name "api", :description "some apis"}]}}}

    (context "/api" []
             :tags ["api"]

             (GET "/plus" []
                  :return {:result Long}
                  :query-params [x :- Long, y :- Long]
                  :summary "adds two numbers together"
                  (log/info "got request2")

                  (ok {:result (+ x y)}))

             (POST "/echo" []
                   :return Pizza
                   :body [pizza Pizza]
                   :summary "echoes a Pizza"
                   (ok pizza))

             (ANY "/*" {headers :headers}
                  (log/info headers)
                  (not-found  headers))))