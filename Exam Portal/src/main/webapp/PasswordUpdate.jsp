<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,DBConnection.PasswordUpdation" %>
<%@ include file="UserLogin.html" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Password</title>
</head>
<body>
<%
    String email = request.getParameter("email");
    String pass1 = request.getParameter("pass");
    String pass2 = request.getParameter("pass1");
    PasswordUpdation pu = new PasswordUpdation();
    pu.setEmail(email);
    pu.setPass1(pass1);
    pu.setPass2(pass2);
    int updateStatus = pu.passUpdate();
    switch (updateStatus) {
        case 0: // Email not found
%>
        <script type="text/javascript">
            alert("Invalid EmailID");
            window.location.href = "ForgotPassword.html";
        </script>
<%
            break;
        case 1: // Passwords do not match
%>
            <script type="text/javascript">
                alert("Please enter the same password");
                window.location.href = "ForgotPassword.html";
            </script>
<%
            break;
        case 2: // Password is not strong
%>
            <script type="text/javascript">
                alert("Password is not Strong\nPassword should contain minimum 8 characters\nPassword should include one Upper case, one lower case and one special character");
                window.location.href = "ForgotPassword.html";
            </script>
<%
            break;
        case 3: // Password updated successfully
%>
             <script type="text/javascript">
                 alert("Password Updation Successful\nPlease login");
                 window.location.href = "UserLogin.html";
             </script>
<%
            break;
        default:
             out.println("Error occures");
             break;
    }
%>
</body>
</html>
