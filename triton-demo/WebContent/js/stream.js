var currentWindow = null;
var trendingTopic = new Array();

var Stream = {};

Stream.socket = null;

Stream.connect = (function(host) {
    if ('WebSocket' in window) {
        Stream.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Stream.socket = new MozWebSocket(host);
    } else {
        Console.log('Error: WebSocket is not supported by this browser.');
        return;
    }

    Stream.socket.onopen = function () {
        Console.log('Info: WebSocket connection opened.');
    };

    Stream.socket.onclose = function () {
        Console.log('Info: WebSocket closed.');
    };

    Stream.socket.onmessage = function (message) {
    	message = JSON.parse(message.data);
    	
    	if (message.action === 'info') {
        	Console.log(message.content);    		
    	} else if (message.action === 'result') {
        	// update cell count
        	var res = message.content;
        	var n = res.length;
        	res = res.substring(1, n-1).split(',');
        	console.log(res);
        	//$('#' + res[0])[0].innerHTML = res[1];	
        	
        	// generate trending topic
        	if (currentWindow === null || currentWindow != res[0]) {
        		$('#trending-topic')[0].innerHTML = '<tr><th>Word</th><th>Count</th></tr>'
        		for (var i = 0; i < trendingTopic.length; i++) {
        			var word = trendingTopic[i].word;
        			var count = trendingTopic[i].count;
                	$('#trending-topic').append('<tr><td>' + word + '</td><td>' + count + '</td></tr>');
        		}
        		trendingTopic = new Array();
        		currentWindow = res[0];
        	}
        	
        	trendingTopic.push({word: res[1], count: res[2]});
    	} else if (message.action === 'raw') {
    		console.log("TODOTODOTOD");
    	}
    	
    };
});

Stream.initialize = function() {
    var connectUrl = window.location.host + '/triton-demo/stream';
    if (window.location.protocol == 'http:') {
        Stream.connect('ws://' + connectUrl);
    } else {
        Stream.connect('wss://' + connectUrl);
    }
};
