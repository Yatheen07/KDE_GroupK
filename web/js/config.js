/**
 * All config data below here.
 */

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

/**
 * @type {{Row0: {military: string, countryName: string, happiness_score: string}, Row1: {military: string, countryName: string, happiness_score: string}, Columns: string[]}}
 */
var dataSparql = {
    "Row0": {
        "military": "13.32567233",
        "countryName": "Saudi Arabia",
        "happiness_score": "6.411"
    },
    "Row1": {
        "military": "2342342342342",
        "countryName": "aAAAAAA",
        "happiness_score": "77777777777777777777777777777.411"
    },
    "Columns":["military", "countryName", "happiness_score"]
};
