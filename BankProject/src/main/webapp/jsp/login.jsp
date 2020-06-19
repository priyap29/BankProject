<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<style>
.banner {
  padding: 10px 0;
  background: #ff6d05;
 // border: 3px solid green;
  text-align: center;
//  font-family: "Trebuchet MS", Helvetica, sans-serif;
  }
  
.login {
  border: 3px solid green;
  text-align: center;
  font-family: "Trebuchet MS", Helvetica, sans-serif;
  width:30%;
  height:40%;
  padding: 15px;
  position: absolute;
  top: 50%;
  left: 50%;
  //-ms-transform: translateX(-50%) translateY(-50%);
  //-webkit-transform: translate(-50%,-50%);
  transform: translate(-50%,-50%);
  }
  
.footer {
  text-align: center;
  font-family: "Trebuchet MS", Helvetica, sans-serif;
  padding: 15px;
  position: absolute;
  top: 95%;
  left: 50%;
//  -ms-transform: translateX(-50%) translateY(-50%);
 // -webkit-transform: translate(-50%,-50%);
  transform: translate(-50%,-50%);
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
 table {
  //padding: 10px 10px;
   margin: 0 auto;
  //border: 1px solid #ccc;
  //cursor: pointer;
  width: 90%;
}
</style>

</head>

<body OnLoad="document.loginForm.uname.focus();">
<header>
<div class="banner">
  <img src="<%=request.getContextPath()%>/jsp/logo.jfif" />
</div>
</header>

  <section class="login">
    <h2>Login Here.</h2>
    <!--  <div class="container">-->
    <form name="loginForm" action="<%=request.getContextPath()%>/userLogin" method="post">
    	<table>
    		<tr style="color: #FF0000;">
  				<td colspan="2">${errorMessage}</td>
  			</tr>
    		<tr>
    			<td style="text-align:right"><label for="uname"><b>Username:</b></label></td>
    			<td><input type="text" placeholder="Enter Username" name="uname" required></td>
    		</tr>	
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Password:</b></label></td>
    			<td><input type="password" placeholder="Enter Password" name="psw" required></td>
    		</tr>
  			<tr>
  				<td colspan="2"><button type="submit">Proceed</button></td>
  			</tr> 
  			<tr>
    			<td style="text-align:left; font-size:12px"> <span class="psw"> <a href="<%=request.getContextPath()%>/jsp/register.jsp">Register</a></span></td>
    			<td style="text-align:right; font-size:12px"><span class="psw"> <a href="<%=request.getContextPath()%>/jsp/forgotPassword.jsp">Forgot password?</a></span></td>
    		</tr>   
    </table>
    </form>  
  <!-- </div> -->
  </section>
  <!-- <div style="color: #FF0000;">${errorMessage}</div>
-->
<footer class="footer">
    @Copyright Kuber Bank Inc.
  </footer>
</body>

</html>