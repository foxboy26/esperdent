<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="favicon.ico">

    <title>Triton Demo Site</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/style.css" rel="stylesheet">
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Triton</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="index.jsp">Home</a></li>
            <li class="active"><a href="demo.jsp">Demo</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-10">
				<div id="query">
				    <div>
						<div style="float: right">
							<button type="submit" id="run"
								class="btn btn-success footer-item rel-tooltip">
								<i class="icon-cog-alt">Run</i>
							</button>
						</div>
						<div style="float: right">
							<button type="submit" id="stop"
								class="btn btn-success footer-item rel-tooltip">
								<i class="icon-cog-alt">Stop</i>
							</button>
						</div>
					</div>
					<div class="code" id="query-script">select * from a;</div>
				</div>
	            
	            <div id="word-count">sdfsdf</div>
	            
	            <div class="console" id="console" >
	            </div>
            </div>

            <div class"col-md-2" id="input-stream" style="height: 500px">tbd</div>
        </div>
    </div><!-- /.container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    
     <!-- Ace code editor -->
    <script src="js/ace-builds/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
    <script>
        var javaEditor = ace.edit("query-script");
        javaEditor.getSession().setMode("ace/mode/sql");
        javaEditor.getSession().setUseWrapMode(true);
        javaEditor.setReadOnly(true);
        javaEditor.setShowPrintMargin(false);
    </script>    <script src="js/message.js" type="test/javascript" charset="utf-8"></script>
    <script src="js/stream.js" type="text/javascript" charset="utf-8"></script>
    <script>
    
        var Console = {};
        Console.log = (function(message) {
                
            var console = $('#console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = message;
            console.append(p);
            $('#console').scrollTop($('#console')[0].scrollHeight)
        });
        
        Console.clear = (function() {
            var console = $('#console');
            console.html('');
        });
    
    
        $('#run').on('click', function(event) {
        	Console.clear();
            start();
        });
        
        $('#stop').on('click', function(event) {
            stop();
        });
        
        function start() {
        	var message = {
        		"clientId": 'stream',
                "action" : "start",
                "content": "topo"
            };
            Stream.socket.send(JSON.stringify(message));
        }
        
        function stop() {
            var message = {
                    "clientId": 'stream',
                    "action" : "stop",
                    "content": "topo"
                };
        	Stream.socket.send(JSON.stringify(message));
        }
        
        Stream.initialize();
    </script>
  </body>
</html>