var Compiler = {};

Compiler.socket = null;

Compiler.connect = (function(host) {
    if ('WebSocket' in window) {
        Compiler.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Compiler.socket = new MozWebSocket(host);
    } else {
        Console.log('Error: WebSocket is not supported by this browser.');
        return;
    }

    Compiler.socket.onopen = function () {
        Console.log('Info: WebSocket connection opened.');
    };

    Compiler.socket.onclose = function () {
        Console.log('Info: WebSocket closed.');
    };

    Compiler.socket.onmessage = function (message) {
    	message = JSON.parse(message.data);
    	if (message.action === 'result') {
            var javaEditor = ace.edit("java-editor");
            //javaEditor.setTheme("ace/theme/github");
            javaEditor.getSession().setValue(message.content);
    	} else {
            Console.log(message.content);
    	}
    };
});

Compiler.initialize = function() {
    var connectUrl = window.location.host + '/triton-demo/compiler';
    if (window.location.protocol == 'http:') {
        Compiler.connect('ws://' + connectUrl);
    } else {
        Compiler.connect('wss://' + connectUrl);
    }
};
