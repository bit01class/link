<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:url var="root" value="/"></c:url>
<html>
<head>
	<meta charset="utf-8"> 
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
	<script type="text/javascript">

	var sock=new SockJS("${root}echo");
	sock.onmessage=function(msg){
		console.log(msg.data);
		$('#target').append(msg.data+"<br/>");
	};
	$(function(){

		$('button').click(function(){
			sock.send($('input').val());
		});
		
	});
	</script>
</head>
<body>
<h1>
	에코 서버!  
</h1>

<input/><button>전송</button>
<div id="target"></div>
</body>
</html>











