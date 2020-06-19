<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Forgot Password</title>

<script type="text/javascript">
	function validatePass(p1, p2) {
		if (p1.value != p2.value || p1.value == '' || p2.value == '') {
			p2.setCustomValidity('Confirm Password doesnt match');
		} else {
			p2.setCustomValidity('');
		}
	}
</script>

<style>
.banner {
	padding: 10px 0;
	background: #ff6d05; //
	border: 3px solid green;
	text-align: center; //
	font-family: "Trebuchet MS", Helvetica, sans-serif;
}

.forgot {
	border: 3px solid green;
	text-align: center;
	font-family: "Trebuchet MS", Helvetica, sans-serif;
	width: 40%;
	height: 40%;
	padding: 15px;
	position: absolute;
	top: 60%;
	left: 50%; //
	-ms-transform: translateX(-50%) translateY(-50%); //
	-webkit-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}

.footer {
	text-align: center;
	font-family: "Trebuchet MS", Helvetica, sans-serif;
	padding: 15px;
	position: absolute;
	top: 95%;
	left: 50%; //
	-ms-transform: translateX(-50%) translateY(-50%); //
	-webkit-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}

button {
	background-color: #4CAF50;
	color: white;
	padding: 5px 5px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 80%;
}

table { //
	padding: 10px 10px;
	margin: 0 auto; //
	border: 1px solid #ccc; //
	cursor: pointer;
	width: 90%;
}
</style>
</head>
<body>

	<header>
		<div class="banner">
			<img src="<%=request.getContextPath()%>/jsp/logo.jfif" />
		</div>
	</header>

	<section class="forgot">
		<h2>Create New Password.</h2>
		<div class="container">
			<form action="<%=request.getContextPath()%>/forgotPassword" method="post">
				<table>
					<tr style="color: #FF0000;">
  						<td colspan="2">${errorMessage}</td>
  					</tr>
					<tr>
						<td style="text-align: right"><label for="uname"><b>Username:</b></label></td>
						<td><input type="text" placeholder="Enter Username" name="uname" required></td>
					</tr>

					<tr>
    					<td style="text-align:right"><label for="psw"><b>Password:</b></label></td>
    					<td><input type="password" placeholder="Enter Password" name="psw" id='p1' required maxlength="10"></td>
    				</tr>
    				<tr>
    					<td style="text-align:right"><label for="cpsw"><b>Confirm Password:</b></label></td>
    					<td><input type="password" placeholder="Confirm Password" name="cpsw" id='p2' required maxlength="10" onkeyup="validatePass(document.getElementById('p1'), document.getElementById('p2'));"></td>
    				</tr>	  	
					<tr>
						<td colspan="2" style="padding: 10px"><button type="submit">Submit</button></td>
					</tr>
					<tr>
						<td style="font-size:12px" colspan="2"><span class="psw">
						<a href="<%=request.getContextPath()%>/jsp/login.jsp">back to login</a></span></td>
					</tr>
				</table>
			</form>
		</div>
	</section>

	<footer class="footer"> @Copyright Kuber Bank Inc. </footer>
</body>
</html>