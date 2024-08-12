<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="DBConnection.ValidUser" %>
<%@ include file="UserLogin.html" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
		out.println("Hello World");
		ValidUser vu=new ValidUser();
    	vu.setUname(request.getParameter("user"));
    	vu.setPass(request.getParameter("pass"));
    	int checkStatus=vu.checkUser();
    	if (checkStatus==0) {
%>
        <script type="text/javascript">
            alert("Invalid username");
            window.location.href = "UserLogin.html";
        </script>
<%
    } else if (checkStatus==1) {
%>
            <script type="text/javascript">
                alert("Incorrect Password");
                window.location.href = "UserLogin.html";
            </script>
<%
        } else {
            session.setAttribute("username", vu.getUname());
            %>
                    <script type="text/javascript">
                        window.location.href = "welcome.jsp";
                    </script>
            <%
        }
%>
</body>
</html>
