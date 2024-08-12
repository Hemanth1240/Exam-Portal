<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="DBConnection.NewUserDetails" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
</head>
<body>
<%
	//out.print("Hi");
    NewUserDetails nud = new NewUserDetails();
    nud.setUname(request.getParameter("user"));
    nud.setEmailid(request.getParameter("email"));
    nud.setPass1(request.getParameter("pass"));
    nud.setPass2(request.getParameter("pass1"));

    // Call insertUser() once and store the return value
    int insertResult = nud.insertUser();
        if (insertResult == 2){
            %>
            <script type="text/javascript">
                alert("Please enter the same password");
                window.location.href = "register.html";
            </script>
            <%
        } else if(insertResult == 3){
            %>
            <script type="text/javascript">
                alert("Password is not Strong\nPassword should contain minimum 8 characters\nPassword should include one Upper case, one lower case and one special character");
                window.location.href = "register.html";
            </script>
            <%
        } else if(insertResult == 4){
            %>
            <script type="text/javascript">
                alert("Username already exists");
                window.location.href = "register.html";
            </script>
            <%
        } else if(insertResult == 5){
            %>
            <script type="text/javascript">
                alert("Email already exists");
                window.location.href = "register.html";
            </script>
            <%
        } else if(insertResult == 0){
            %>
            <script type="text/javascript">
                alert("Registration Successful\nPlease login");
                window.location.href = "UserLogin.html";
            </script>
            <%
        } else {
            %>
            <script type="text/javascript">
                alert("Unknown error occurred"+insertResult);
                window.location.href = "register.html";
            </script>
            <%
        }
%>
</body>
</html>
