// loadJS('/js/config.js');

var __LINK__ = 'http://localhost:8000/executeQuery';

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

function  subSort(obj){
    submitItem($(obj).parent().prev().attr('id'), 'sort', $(obj).parent().prev().children('select').val())
}

function  subContinent(obj){
    submitItem($(obj).parent().prev().attr('id'), 'continent', $(obj).parent().prev().children('select').val())
}

function submitItem(q, key, val){
    let resJson ={queryID:q};
    resJson[key] = val;
    ajaxSparql(resJson);
}

/**
 * Get the data by ajax with this function.
 * @param link
 * @param query
 * @param msg
 */
function ajaxSparql(query, link=__LINK__, msg=__MSG__){
    var settings = {
        'cache': false,
        "async": true,
        "crossDomain": true,
        "url": link ,
        "method": "GET",
        "data": query,
        "headers": {
            "accept": "application/json",
            "Access-Control-Allow-Origin": "x-requested-with"
        }
    }

    $.ajax(settings).done(function (response) {
        console.log(response);
        showInTable_new(response);
    });
    /*$.ajax({
        url: link, 
        crossDomain: true,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Headers":"origin, content-type, accept, authorization",
            "Access-Control-Allow-Credentials":true,
            "Access-Control-Allow-Methods":"GET",
            "Access-Control-Max-Age": "1209600"
            },
        dataType: "json",
        // crossDomain: true,
        type: 'GET',
        data: query
    }).done((o)=>{
        console.log(__MSG__.done);
    }).fail((o) => {
        console.log(__MSG__.fail);
    }).always((o)=>{
        console.log(__MSG__.always);
        showInTable_new(o);
    });*/
}

/**
 * This function just for put the data which got from GraphDB into the table that id is 'id'.
 * @param data The data got from GraphDB.
 * @param id The id which you want put the data in.
 */
function showInTable_new(data, id="table") {
    console.log(typeof data);
    console.log(data);
    data = JSON.parse(data);
/*
    console.log(typeof data);
    console.log(data);*/

    let thead = "<th>" + data["Columns"][0] + "</th>" + "<th>" + data["Columns"][1] + "</th>" + "<th>" + data["Columns"][2] + "</th>";
    let tbody = "";

    for(let k in data)
        if(!k.indexOf("Row")>0)
            tbody += "<tr>" + "<td>" + data[k][data.Columns[0]] + "</td>" + "<td>" + data[k][data.Columns[1]] + "</td>" + "<td>" + data[k][data.Columns[2]] + "</td>" + "</tr>";

    let table = "<thead class=\"thead-dark\">" + thead + "</thead>" + "<tbody style=\"background-color: #eeeee4;\">"+ tbody +"</tbody>";

    $("#"+id).html(table);
}

function loadJS(src, o=false) {
    let script = document.createElement('script');
    let head = document.getElementsByTagName('head')[0];

    script.type = 'text/javascript';
    script.src = src;

    if (script.addEventListener) {
        script.addEventListener('load', function () {
            if (o)
                o();
            else
                console.log('Load Successfully: ' + src);
        }, false);
    }

    head.appendChild(script);
}
