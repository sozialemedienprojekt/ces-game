/**
 * @constructor
 * @param {string} url URL to be connected to
 * @param {string} content_type content type of response
 * @param {(string|undefined)} username username (optional, necessary for authentication)
 * @param {(string|undefined)} password password (optional, necessary for authentication)
 */
var RestfulWebServiceClient = function (url, content_type, username, password) {
    this.url = url;
    this.content_type = content_type;
    this.username = username;
    this.password = password;
    /**
     *
     * @param {Array} params parameters to be appended to URL
     * @param {Function} callback function to be called after asynchronous request
     */
    this.call = function (params, callback) {
        // params = array?
        if (params !== null && Object.prototype.toString.call(params) !== '[object Array]') {
            throw "Parameter #1 is not an array.";
        }
        // callback = function?
        if (callback !== null && Object.prototype.toString.call(callback) !== '[object Function]') {
            throw "Parameter #2 is not a function.";
        }
        var xhr = new XMLHttpRequest();
        xhr.ontimeout = function () {
            throw "The request for " + this.url + " timed out.";
        };
        xhr.onload = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    var result = null;
                    var response = xhr.responseText;
                    if (response !== undefined && response !== null) {
                        result = JSON.parse(response);
                    }
                    if (callback !== null) {
                        callback.apply(null, [xhr, params, result]);
                    }
                } else {
                    console.error(xhr);
                }
            } else {
                console.error(xhr);
            }
        };
        var param_str = ((params !== null) ? "/" + params.join("/") : "");
        xhr.open("GET", this.url + param_str, true, this.username, this.password);
        xhr.setRequestHeader("Content-Type", this.content_type);
        //xhr.responseType = "text";
        xhr.send(null);
    };
};