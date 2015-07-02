function test_websocket() {
    var data = {};
    var sender = jQuery(PrimeFaces.escapeClientId('logout:remote_user_name')).text();
    data.type = 'ChatMessage';
    data.message = 'How are you?';
    data.sender = sender;
    var json = JSON.stringify(data);
    wsUpdateClient.send(json);
}