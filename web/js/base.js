$(document).ready(()=>{
    /** A Button for testing **/
    $(document).on('click', '#sss', (e)=> {
        ajaxSparql(__QUERIES__.q1);
    });

    // $(document).on('click', '#q_2', (e)=> { ajaxSparql(__QUERIES__.q2); });
});

function clickItem(obj, q){
    $('#txt').html(__P_NAME__[q]);
    ajaxSparql(__QUERIES__.q1);
}

/**
 * Get the data by ajax with this function.
 * @param link
 * @param query
 * @param msg
 */
function ajaxSparql(query, link=__LINK__, msg=__MSG__){
    $.ajax({
        url: link,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        dataType: "json",
        // crossDomain: true,
        type: 'GET',
        data: query
    }).done((o)=>{
        alert(msg.done);
    }).fail((o) => {
        // alert(msg.fail);
    }).always((o)=>{
        showInTable(dataSparql);
    });
}

/**
 * This function just for put the data which got from GraphDB into the table that id is 'id'.
 * @param data The data got from GraphDB.
 * @param id The id which you want put the data in.
 */
function showInTable(data, id="table") {
    let thead = "";
    let tbody = "";

    data.head.vars.forEach((element, index, array)=>{
        thead += "<th>" + element + "</th>"
    });

    data.results.bindings.forEach((elementBody, indexBody, arrayBody)=>{
        tbody += "<tr>";
        data.head.vars.forEach((element, index, array)=>{
           tbody += "<td>" + elementBody[element].value + "</td>";
        });
        tbody +=  "</tr>";
    });

    let table = "<thead class=\"thead-dark\">" + thead + "</thead>" + "<tbody style=\"background-color: #eeeee4;\">"+ tbody +"</tbody>";


    $("#"+id).html(table);
}

/**
 * All config data below here.
 */

var __LINK__ = 'http://localhost:7200/rest/locations';

var __QUERIES__ = {
    q1: {query: 'prefix rr: <http://www.w3.org/ns/r2rml#> prefix geo: <http://www.opengis.net/ont/geosparql#> prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#> prefix xsd: <http://www.w3.org/2001/XMLSchema#> prefix oxly: <http://www.example.org/ont/groupK#>   SELECT ?countryName  (AVG(?corr) AS ?corr ) (AVG(?gdp) AS ?gdp) (AVG(?happiness_score) AS ?happiness_score) WHERE {           ?country oxly:countryName ?countryName ;     		 oxly:hasSustainability ?sus ;              oxly:hasEconomy ?eco .          ?sus oxly:countryWBI ?well .     ?well oxly:corruptionScore ?corr ;           oxly:happinessScore ?happiness_score.          ?eco oxly:countryGDP ?gdp_entity .     ?gdp_entity oxly:gdpValue ?gdp .    	                   } GROUP BY ?countryName limit 10'},
    q2: {query: ''},
    q3: {query: ''},
    q4: {query: ''},
    q5: {query: ''},
    q6: {query: ''},
    q7: {query: ''},
    q8: {query: ''},
    q9: {query: ''},
    q10: {query: ''}
}

var __P_NAME__ = {
    q1: "Country Name",
    q2: "",
    q3: "",
    q4: "",
    q5: "",
    q6: "",
    q7: "",
    q8: "",
    q9: "",
    q10:""
};

/**
 * This is the alert message about What will happen when the ajax is done, fail and always.
 * @type {{always: string, fail: string, done: string}}
 * @private
 */
var __MSG__ = {
    done: 'Successful',
    fail: 'do fail',
    always: 'do always'
}

/**
 * This just a testing data, but the structure is same as I get it from the API of GraphDB.
 * @type {{head: {vars: string[]}, results: {bindings: [{corr: {datatype: string, type: string, value: string}, gdp: {datatype: string, type: string, value: string}, countryName: {type: string, value: string}, happiness_score: {datatype: string, type: string, value: string}}, {corr: {datatype: string, type: string, value: string}, gdp: {datatype: string, type: string, value: string}, countryName: {type: string, value: string}, happiness_score: {datatype: string, type: string, value: string}}, {corr: {datatype: string, type: string, value: string}, gdp: {datatype: string, type: string, value: string}, countryName: {type: string, value: string}, happiness_score: {datatype: string, type: string, value: string}}, {corr: {datatype: string, type: string, value: string}, gdp: {datatype: string, type: string, value: string}, countryName: {type: string, value: string}, happiness_score: {datatype: string, type: string, value: string}}, {corr: {datatype: string, type: string, value: string}, gdp: {datatype: string, type: string, value: string}, countryName: {type: string, value: string}, happiness_score: {datatype: string, type: string, value: string}}, null, null, null, null, null]}}}
 */
var dataSparql = {
    "head": {
        "vars": [
            "countryName",
            "corr",
            "gdp",
            "happiness_score"
        ]
    },
    "results": {
        "bindings": [
            {
                "countryName": {
                    "type": "literal",
                    "value": "Netherlands"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.3000239416666667"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "8.19E11"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "7.3646666183333345"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Norway"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.34625127833333336"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "4.1275E11"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "7.519000059666667"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Guinea"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.11717587266666663"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "9.12332497675E9"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "3.589999989666666"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Botswana"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.11033773299999995"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "1.59308293075E10"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "4.024000010666668"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Nicaragua"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.16225932733333334"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "1.292728355125E10"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "5.963666699666668"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Slovenia"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.039782993"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "4.66145046755E10"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "5.791333299000001"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Bhutan"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.16314346333333332"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "2.13000652075E9"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "5.153333385333332"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Morocco"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.08575439433333332"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "1.06E11"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "5.133000044666665"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Montenegro"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.11944806466666669"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "4.47060052275E9"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "5.196666662999999"
                }
            },
            {
                "countryName": {
                    "type": "literal",
                    "value": "Cyprus"
                },
                "corr": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "0.05165932533333334"
                },
                "gdp": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "2.167047030675E10"
                },
                "happiness_score": {
                    "datatype": "http://www.w3.org/2001/XMLSchema#double",
                    "type": "literal",
                    "value": "5.618666604333332"
                }
            }
        ]
    }
}
