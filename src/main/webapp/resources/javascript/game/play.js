var wsUpdateClient;
var restClient;

/**
 *
 * function to be called by web socket client after 'on message' event
 *
 * @param {Object} evt web socket event
 */
var onMessage = function (evt) {
    console.log(evt.data);
    var json = JSON.parse(evt.data);
    if (json !== undefined && json !== null) {
        processJsonData.apply(null, [json]);
    } else {
        console.log("JSON data could not be found.");
    }
};

/**
 *
 * function to be called by web socket client after 'on error' event
 *
 * @param {Object} evt web socket event
 */
var onError = function (evt) {
    console.log('web socket error');
};

/**
 *
 * function to be called by web socket client after 'on close' event
 *
 */
var onClose = function () {
    console.log('connection closed');
};

/**
 *
 * processes json data
 *
 * @param {Object} json json data to be processed
 */
var processJsonData = function (json) {
    console.log(json);
    var type = json.type;
    if (type === undefined || type === null)
        return;
    // update components?
    if (type === 'ComponentUpdate') {
        var components = json.components;
        components.forEach(function (component) {
            // find object
            var fn = window["update_" + component];
            // is object a function?
            if (Object.prototype.toString.call(fn) === '[object Function]') {
                fn.apply(null);
            }
        });
    }
};

// executed before page unload/reload
jQuery(window).bind('beforeunload', function () {
    wsUpdateClient.disconnect();
});

// executed after page load
jQuery(document).ready(function ($) {
    var protocol = jQuery(PrimeFaces.escapeClientId('form5:wsprotocol')).text();
    var host = window.location.host;
    var context_path = jQuery(PrimeFaces.escapeClientId('form5:contextpath')).text();
    var ws_path = jQuery(PrimeFaces.escapeClientId('form5:wspath')).text();
    var cooperatorId = getParameterByName('cooperator-id');
    var url = protocol + '://' + host + context_path + ws_path + '/' + cooperatorId;
    wsUpdateClient = new WebSocketClient(url, null, onError, onMessage, onClose, true);
    wsUpdateClient.connect();
    var gameId = getParameterByName('game-id');
    var url2 = "https://" + host + context_path + "/restful/historical/economic/data/get/all";
    restClient = new RestfulWebServiceClient(url2, "application/json");
    console.log(url2);
    restClient.call([gameId], processRestfulData);
});


var processRestfulData = function (xhr, params, result) {
    console.log(xhr);
    console.log(params);
    console.log(result);
};


/**
 * returns the value of a get param
 * src: http://stackoverflow.com/questions/901115/how-can-i-get-query-string-values-in-javascript/901144#901144
 *
 * @param {String} name of the get param
 * @returns {String} value of the get param
 */
function getParameterByName(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)'),
            results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}