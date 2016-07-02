(ns martin.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]
            [schema.core :as s]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.swagger.json-schema :as rjs]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})


(s/defschema Required
  (rjs/field
    {(s/optional-key :name) s/Str
     (s/optional-key :title) s/Str
     :address (rjs/field
                {:street (rjs/field s/Str {:description "description here"  :readOnly true})}
                {:description "Streename"
                 :example "Ankkalinna 1"})}
    {:minProperties 1
     :description "I'm required"
     :example {:name "Iines"
               :title "Ankka"}}))

(s/defschema ErrorMessage
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})


(s/defschema Result
  {:result (rjs/field s/Int {:description "description here"  :readOnly true})})

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
                  :return Result
                  :query-params [x :- Long, y :- Long]
                  :summary "adds two numbers together"
                  :responses {403 {:schema Required}
                              404 {:schema ErrorMessage}}
                  (log/info "got request2")

                  (ok {:result (+ x y)}))

             (POST "/echo" []
                   :return Pizza
                   :body [pizza Pizza]
                   :summary "echoes a Pizza"
                   (ok pizza))

             (ANY "/*" {headers :headers}
               (if (= (get headers "authorization") "ApiToken uc-api-ac2feec4-574f-40c2-bffc-9fb5847e6181")
                 (log/info (.concat "successfully authorized with " (get headers "authorization")))
                 (log/info "false"))
               (not-found  headers))))

(def handler
  (wrap-cors app :access-control-allow-origin [#".*"]
             :access-control-allow-methods [:get :put :post :delete]))