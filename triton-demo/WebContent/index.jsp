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
      <div class="container">
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

    <div class="container">
<!--             <div class="starter-template">
        <h1>Bootstrap starter template</h1>
        <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>
      </div> -->
		<div class="row">
			<div class="col-md-8">
			    
                <div id="editor">SELECT * FROM movie WHERE title = "inception";</div>
			</div>
			<div class="col-md-4"></div>
		</div>
		<div class="row" style="padding:0">
			<div class="col-md-6"></div>
			<div class="col-md-2">
				<button type="submit" id="submit" class="btn btn-success footer-item rel-tooltip">
				<i class="icon-cog-alt"></i>
				Run</button>
			</div>
			<div class="col-md-4"></div>
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
        var editor = ace.edit("editor");
        editor.setTheme("ace/theme/tm");
        editor.getSession().setMode("ace/mode/sql");
    </script>
    <script>
        $('#submit').on('click', function(event) {
            compile();
        });
        
        function compile() {
        	var query = {
        		'query' : editor.getValue()
        	};
        	
        	$.post( "compile.jsp", query, function(data) {
        		$('#console').html(data);
        	});
        }
    </script>
  </body>
</html>