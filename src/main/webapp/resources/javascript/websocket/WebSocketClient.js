/**
 *
 * @param {string} url url to be connected to
 * @callback onOpen callback function to be called on open event
 * @callback onError callback function to be called on error event
 * @callback onMessage callback function to be called on message event
 * @callback onClose callback function to be called on close event
 * @oaram {boolean} autoReconnect indicates whether client shall automatically reconnect after 'on close'
 * @returns {WebSocketClient} web socket client
 */
var WebSocketClient = function (url, onOpen, onError, onMessage, onClose, autoReconnect) {
    //
    if (url === undefined || url === null || Object.prototype.toString.call(url) !== '[object String]') {
        throw "Parameter #1 must be a String.";
    }
    if (onOpen !== null && Object.prototype.toString.call(onOpen) !== '[object Function]') {
        throw "Parameter #2 is not a function.";
    }
    if (onError !== null && Object.prototype.toString.call(onError) !== '[object Function]') {
        throw "Parameter #3 is not a function.";
    }
    if (onMessage !== null && Object.prototype.toString.call(onMessage) !== '[object Function]') {
        throw "Parameter #4 is not a function.";
    }
    if (onClose !== null && Object.prototype.toString.call(onClose) !== '[object Function]') {
        throw "Parameter #5 is not a function.";
    }
    //
    this.url = url;
    this.onOpen = onOpen;
    this.onError = onError;
    this.onMessage = onMessage;
    this.onClose = onClose;
    this.autoReconnect = ((autoReconnect !== undefined && autoReconnect !== null && autoReconnect === false) ? false : true);
    //
    var websocket;
    var attempts = 1;
    //
    this.connect = function reconnect() {
        console.log("url: " + this.url);
        websocket = new WebSocket(this.url);
        websocket.onerror = function (evt) {
            if (onError !== null) {
                onError.apply(null, [evt]);
            }
        };
        websocket.onopen = function (evt) {
            attempts = 1;
            if (onOpen !== null) {
                onOpen.apply(null, [evt]);
            }
        };
        websocket.onmessage = function (evt) {
            if (onMessage !== null) {
                onMessage.apply(null, [evt]);
            }
        };
        websocket.onclose = function () {
            if (onClose !== null) {
                onClose.apply(null);
                if (autoReconnect) {
                    var time = function () {
                        var maxInterval = (Math.pow(2, attempts) - 1) * 1000;
                        if (maxInterval > 30 * 1000) {
                            maxInterval = 30 * 1000; // If the generated interval is more than 30 seconds, truncate it down to 30 seconds.
                        }
                        return maxInterval;
                    };
                    setTimeout(function () {
                        this.url = url;
                        this.onOpen = onOpen;
                        this.onError = onError;
                        this.onClose = onClose;
                        this.autoReconnect = autoReconnect;
                        // We've tried to reconnect so increment the attempts by 1
                        attempts++;
                        // Connection has closed so try to reconnect every 10 seconds.
                        reconnect();
                    }, time);
                }
            }
        };
    };
    this.send = function (data) {
        websocket.send(data);
    };
    this.disconnect = function () {
        websocket.close();
    };
};
