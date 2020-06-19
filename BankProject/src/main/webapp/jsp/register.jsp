<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Page</title>
<style>
.banner {
  padding: 10px 0;
  background: #ff6d05;
 // border: 3px solid green;
  text-align: center;
//  font-family: "Trebuchet MS", Helvetica, sans-serif;
  }
  
.register {
  border: 3px solid green;
  text-align: center;
  font-family: "Trebuchet MS", Helvetica, sans-serif;
  width:40%;
  height:55%;
  padding: 15px;
  position: absolute;
  top: 60%;
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

<body>

<header>
<div class="banner">
  <img src="<%=request.getContextPath()%>/jsp/logo.jfif" />
</div>
</header>

  <section class="register">
    <h2>Registration Form.</h2>
    <div class="container">
    <form action="<%=request.getContextPath()%>/register" method="post">
    	<table>
    		<tr style="color: #FF0000;">
  				<td colspan="2" style="text-align:center">${errorMessage}</td>
  			</tr>
    	    <tr>
    			<td style="text-align:right"><label for="first_name"><b>First Name:</b></label></td>
    			<td style="text-align:left"><input type="text" required placeholder="Enter FirstName" name="first_name" size="30" maxlength="50" required></td>
    		</tr>
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Last Name:</b></label></td>
    			<td style="text-align:left"><input type="text" placeholder="Enter LastName" name="last_name" size="30" maxlength="50" required></td>
    		</tr>
    		<tr>
    			<td style="text-align:right"><label for="uname"><b>Username:</b></label></td>
    			<td style="text-align:left"><input type="text" placeholder="Enter Username" name="uname" size="30" maxlength="50" required></td>
    		</tr>	
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Password:</b></label></td>
    			<td style="text-align:left"><input type="password" placeholder="Enter Password" name="psw" required size="30" maxlength="10"></td>
    		</tr>	
    		<tr>
    			<td style="text-align:right"><label for="psw"><b>Email:</b></label></td>
    			<td style="text-align:left"><input type="text" placeholder="Enter Email" name="email" size="30" maxlength="50" required pattern=".*@gmail\.com"></td>
    		</tr>
    		<tr>
    			<td style="text-align:right"><label for=""><b>Account Type:</b></label></td>
    			<td style="text-align:left">
					<input type="radio" id="checking" name="type" value="checking" required>
					<label for="checking">Checking</label>
					<input type="radio" id="savings" name="type" value="savings">
					<label for="saving">Savings</label>
				</td>
    		</tr>
  			<tr>
    			<td style="text-align:right"><label for="bal"><b>Initial Balance:</b></label></td>
    			<td style="text-align:left">$<input type="number" placeholder="Enter Balance" name="bal" size="29" min=100 required step=".01"><span style="font-size:12px">(min $100)</span></td>
    		</tr>
  			<tr>
  				<td>&nbsp;</td>
  				<td style="padding:10px"><button type="submit">Register</button></td>
  			</tr> 
  			<tr>
    			<td style="text-align:right; font-size:12px" colspan="2"><span class="psw"> 
				<a href="<%=request.getContextPath()%>/jsp/login.jsp">back to login</a></span></td>
    		</tr>   
    </table>
    </form>  
  </div>
  </section>
  
  <footer class="footer">
    @Copyright Kuber Bank Inc.
  </footer>
</body>
</html>