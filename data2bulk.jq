#!jq -rfc

def ouiNon2Bool:
    . == "OUI"
;

def map_doc:
    .fields
    | {
        ebike,
        capacity,
        name,
        nom_arrondissement_communes,
        numbikesavailable,
        mechanical,
        stationcode,
        coordonnees_geo,
        numdocksavailable,
        duedate,

        is_installed: ( .is_installed | ouiNon2Bool ),
        is_renting: ( .is_renting | ouiNon2Bool ),
        is_returning: ( .is_returning | ouiNon2Bool )
      }
;

.[]
| . as $doc
| { index : { _index: "velib", _type: "_doc", _id: .recordid } }
| ., ( $doc | map_doc )