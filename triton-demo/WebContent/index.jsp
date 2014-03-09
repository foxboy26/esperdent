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
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">Demo</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

    <div class="container-fluid">
		<div class="row">
			<div class="col-md-6">
                <div id="tql-editor" class="editor">SELECT * FROM movie WHERE title = "inception";</div>
			</div>
			<div class="col-md-6">
			    <div id="java-editor" class="editor">class Test {
    public static void main(String[] args){
        System.out.println("Hello World!");
    }
}</div>
			</div>
		</div>
		<div class="row" style="padding:0">
			<div class="col-md-5"></div>
			<div class="col-md-1">
				<button type="submit" id="submit" class="btn btn-success footer-item rel-tooltip">
				<i class="icon-cog-alt"></i>
				Run</button>
			</div>
			<div class="col-md-6"></div>
		</div>
        <div class="row">
        <p class="lead">Console</p>
            <div id="console" >
            </div>
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
        var editor = ace.edit("tql-editor");
        //editor.setTheme("ace/theme/github");
        editor.getSession().setMode("ace/mode/sql");
        editor.getSession().setUseWrapMode(true);
        editor.setShowPrintMargin(false);
        
        var javaEditor = ace.edit("java-editor");
        //javaEditor.setTheme("ace/theme/github");
        javaEditor.getSession().setMode("ace/mode/java");
        javaEditor.getSession().setUseWrapMode(true);
        javaEditor.setReadOnly(true);
        javaEditor.setShowPrintMargin(false);
    </script>
    <script src="js/demo.js" type="text/javascript" charset="utf-8"></script>
    <script>
    
	    var Console = {};
	    Console.log = (function(message) {
	            
	        var console = $('#console');
	        var p = document.createElement('p');
	        p.style.wordWrap = 'break-word';
	        p.innerHTML = message;
	        console.append(p);
	    });
	    
	    Console.clear = (function() {
	    	var console = $('#console');
	    	console.html('');
	    });
    
    
        $('#submit').on('click', function(event) {
            compile();
        });
        
        function compile() {
        	var query = {
        		'query' : editor.getValue()
        	};
        	Console.clear();
        	//Compiler.socket.send(JSON.stringify(query));
            Compiler.socket.send(editor.getValue());
        }
        
        Compiler.initialize();
    </script>
  </body>
</html>