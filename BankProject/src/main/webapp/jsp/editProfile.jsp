<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Edit Profile</title>
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
	line-height: 30px;
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
	margin: 8px auto;
	border: 3px solid green; 
	cursor: pointer;
	width: 50%;
}

//
.vertical-menu { //
	width: 200px; /* Set a width if you like */
	//
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
.linkAsButton {
  background-color: #4CAF50;
  color: white;
  padding: 5px 5px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 50%;
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
			<c:choose>  
				<c:when test="${customer.roleId == 2}">
					<a href="<%=request.getContextPath()%>/jsp/openAccount.jsp">Open New Account</a> 
					<a href="<%=request.getContextPath()%>/jsp/addJointHolder.jsp">Add Joint Holder</a> 
				</c:when>  
			    <c:otherwise>  
					<a href="<%=request.getContextPath()%>/jsp/upgradePremium.jsp">Upgrade to Premium</a>  
				</c:otherwise>  
			</c:choose>	 
			<a href="<%=request.getContextPath()%>/jsp/profile.jsp" class="active">Profile</a>
		</div>
	</nav>

	<section>
    <div>
    <form action="<%=request.getContextPath()%>/editProfile" method="post">
    	<table>
    		<tr>
  				<td colspan="2" style="text-align:center"><h1><b>Edit Profile</b></h1></td>
  			</tr>
  			<tr>
    			<td style="text-align:right"><label for="uname"><b>Username:</b></label></td>
    			<td style="text-align:left"><input type="text" required value="${customer.username}" name="uname" size="30" maxlength="50" readonly></td>
    		</tr>
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Password:</b></label></td>
    			<td style="text-align:left"><input type="password" placeholder="New Password" name="psw" required size="30" maxlength="10"></td>
    		</tr>
    	    <tr>
    			<td style="text-align:right"><label for="first_name"><b>First Name:</b></label></td>
    			<td style="text-align:left"><input type="text" required value="${customer.firstName}" name="first_name" size="30" maxlength="50" required></td>
    		</tr>
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Last Name:</b></label></td>
    			<td style="text-align:left"><input type="text" value="${customer.lastName}" name="last_name" size="30" maxlength="50" required></td>
    		</tr>		
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Email:</b></label></td>
    			<td style="text-align:left"><input type="text" value="${customer.email}" name="email" size="30" maxlength="50" required pattern=".*@gmail\.com"></td>
    		</tr>
  			<tr>
  				<td><input type="hidden" id="roleId" name="roleId" value="${customer.roleId}"></td>
  				<td><input type="hidden" id="userId" name="userId" value="${customer.userId}"></td>
  				<td style="padding:10px"><button type="submit">Submit</button></td>
  			</tr>   
    </table>
    </form>  
  </div>
  </section>

	<footer class="footer"> @Copyright Kuber Bank Inc. </footer>
</body>
</html>