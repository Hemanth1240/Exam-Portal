<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="DBConnection.ResultValidation" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Submit Exam</title>
<link rel="stylesheet" href="styles.css"> 
<style>
    body {
        margin: 0;
        padding: 0;
        background-color: rgb(230, 236, 240);
        font-family: sans-serif;
    }
    header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 20px;
        background-color: #f1f1f1;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }
    header .title a {
        text-decoration: none;
        font-size: 20px;
        color: rgb(249, 6, 31);
    }
    header .username {
        font-size: 18px;
        font-weight: bold;
        color: rgb(0, 0, 0);
    }
    .content {
        width: 600px;
        background: whitesmoke;
        color: rgb(0, 0, 0);
        margin: 50px auto;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    .content h1 {
        text-align: center;
        font-size: 24px;
        color: rgb(249, 6, 31);
        margin-bottom: 20px;
    }
    .content h3 {
        font-size: 20px;
        margin-bottom: 15px;
    }
</style>
</head>
<body>
    <header>
        <div class="title">
        	
            <a href="welcome.jsp">Home</a>
        </div>
        <div class="username">
            <% 
                String username = (String) session.getAttribute("username");
                if (username != null) {
                    out.print(username);
                }
            %>
        </div>  
    </header>
    
    <div class="content">
        <h1>Exam Results</h1>
        <%
            Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
            if (totalQuestions == null || username == null) {
                out.println("<h3>Error: Required session attributes are missing.</h3>");
            } else {
                List<String> answers = (List<String>) session.getAttribute("answers");
                if (answers != null) {
                    ResultValidation rv = new ResultValidation();
                    int correctAnswers = rv.checkAnswer(totalQuestions, answers.toArray(new String[0]), username);

                    out.println("<h3>Total Questions: " + totalQuestions + "</h3>");
                    out.println("<h3>Correct Answers: " + correctAnswers + "</h3>");
                } else {
                    out.println("<h3>No answers received.</h3>");
                }
            }
            session.setAttribute("username", username);
        %>
    </div>
</body>
</html>
