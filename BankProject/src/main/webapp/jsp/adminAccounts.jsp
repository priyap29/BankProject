<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Main Page</title>
<style>
.banner {
	padding: 10px 0;
	background: #ff6d05; //
	border: 3px solid green;
	text-align: center; //
	font-family: "Trebuchet MS", Helvetica, sans-serif;
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

body {
	font-family: "Trebuchet MS", Helvetica, sans-serif;
}

nav {
	line-height: 30px; //
	background-color: #eeeeee;
	height: 300px;
	width: 200px;
	float: left;
	padding: 5px;
}

section {
	width: 70%;
	float: left;
	padding: 10px;
}

table { 
	padding: 10px 10px;
	margin: 0 auto;
	border: 3px solid green;
	border-collapse: collapse;
	width: 80%;
}

.innerTable { 
	padding: 10px 10px;
	margin: 0 auto;
	border: 1px solid black;
	border-collapse: collapse;
	width: 80%;
}

td { 
	border: 1px solid black;
}
.vertical-menu { 
	width: 200px; /* Set a width if you like */
}

.vertical-menu a {
	background-color: #ddd; /* Grey background color */
	color: black; /* Black text color */
	display: block; /* Make the links appear below each other */
	padding: 12px; /* Add some padding */
	text-decoration: none; /* Remove underline from links */
}

.vertical-menu a:hover {
	background-color: #ccc; /* Dark grey background on mouse-over */
}

.vertical-menu a.active {
	background-color: #4CAF50;
	/* Add a green color to the "active/current" link */
	color: white;
}
</style>
</head>


<body>

	<header>
		<div class="banner">
			<img src="<%=request.getContextPath()%>/jsp/logo.jfif" />
		</div>
	</header>
	<p>Hello ${admin.firstName}!</p>
	<p style="font-size: 14px">
		<a href="<%=request.getContextPath()%>/jsp/login.jsp">logout</a>
	</p>
	<br>


	<nav>
		<div class="vertical-menu">
			<a href="<%=request.getContextPath()%>/jsp/adminMain.jsp">Users</a> 
			<a href="#" class="active">Accounts</a>
			<a href="<%=request.getContextPath()%>/jsp/transferFunds.jsp">Roles</a>
			<a href="<%=request.getContextPath()%>/jsp/depositFunds.jsp">Account Types</a> 
			<a href="<%=request.getContextPath()%>/jsp/profile.jsp">Account Status</a>
		</div>
	</nav>

	<section>
		<h1>User Accounts</h1>
		<div>
			<table>
				<tr>
					<td><b>User ID</b></td>
					<td><b>Username</b></td>
					<td><b>Accounts</b></td>
				</tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.userId}</td>
						<td>${user.firstName}&nbsp;&nbsp;${user.lastName}</td>
						<td>
							<table class="innerTable">
								<c:forEach items="${user.accountList}" var="account">
								<tr>
									<td>${account.accountId}</td>
									<td>${account.strType}</td>
									<td>${account.strStatus}</td>
									<td>${account.balance}</td>
									<td><a href="#">Edit</a></td>
									<td><a href="#">Delete</a></td>
								</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</section>


</body>
</html>