<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upgrade Premium</title>
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
	width: 50%;
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
	<p>Hello ${customer.firstName}!</p>
	<p style="font-size: 14px">
		<a href="<%=request.getContextPath()%>/jsp/login.jsp">logout</a>
	</p>
	<br>


	<nav>
		<div class="vertical-menu">
			<a href="<%=request.getContextPath()%>/jsp/customerMain.jsp">Accounts</a>
			<a href="<%=request.getContextPath()%>/jsp/transferFunds.jsp">Transfer Funds</a>
			<a href="<%=request.getContextPath()%>/jsp/depositFunds.jsp">Withdraw / Deposit	Funds</a> 
			<a href="#" class="active">Upgrade to Premium</a> 
			<a href="<%=request.getContextPath()%>/jsp/profile.jsp">Profile</a>
		</div>
	</nav>

	<section>
		<div>
		<form action="<%=request.getContextPath()%>/upgradePremium" method="post">
			<table>
				<tr>
  					<td colspan="2" style="text-align:center"><h1><b>Upgrade to Premium</b></h1></td>
  				</tr>
				<tr style="color: #FF0000;">
  					<td colspan="2" style="text-align:center">${errorMessage}</td>
  				</tr>
				<tr> 
					<td colspan="2" style="padding:10px">
					By upgrading to premium account you will be able to:
  							<ul>
  								<li>
  									Open any number of accounts with Kuber Bank.
  								</li>
  								<li>
  									Open joint accounts.
  								</li>
  								<li>
  									Add users to existing accounts.
  								</li>
  							</ul>
  						Cost to upgrade is $100 which will be deducted directly from your existing account.	For more 
  						information please call our customer care at 1-800-000-000 
					</td>
				</tr>
    			<tr>
  					<td style="padding:10px" colspan="2"><button type="submit">Upgrade</button></td>
  				</tr>
			</table>
			</form>
		</div>
	</section>






	<footer class="footer"> @Copyright Kuber Bank Inc. </footer>
</body>
</html>