<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exam Page</title>
<link rel="stylesheet" href="style.css"> 
</head>
<body>
    <div class="container1">
        <h1>Exam Page</h1>
        <%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect("UserLogin.html");
                return;
            }

            Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
            if (totalQuestions == null) {
                // Initialize totalQuestions if not set
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db", "Hemu", "Hello@World40");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM `112321`");
                    if (rs.next()) {
                        totalQuestions = rs.getInt(1);
                        session.setAttribute("totalQuestions", totalQuestions);
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            List<String> answers = (List<String>) session.getAttribute("answers");
            if (answers == null) {
                answers = new ArrayList<>(Collections.nCopies(totalQuestions, null));
                session.setAttribute("answers", answers);
            }

            Integer questionIndex = (Integer) session.getAttribute("questionIndex");
            if (questionIndex == null) {
                questionIndex = 1;
            }

            String nextQuestionIndex = request.getParameter("nextQuestionIndex");
            String answer = request.getParameter("answer");

            if (answer != null) {
                if (questionIndex <= answers.size()) {
                    answers.set(questionIndex - 1, answer);
                    session.setAttribute("answers", answers);
                }
            }

            if (nextQuestionIndex != null) {
                questionIndex = Integer.parseInt(nextQuestionIndex);
            }

            session.setAttribute("questionIndex", questionIndex);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db", "Hemu", "Hello@World40");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `112321` WHERE SNO = " + questionIndex);

                if (rs.next()) {
                    out.println("<div class='question-box'>");
                    out.println("<p>Question " + rs.getInt("SNO") + ": " + rs.getString("Question") + "</p>");
                    out.println("<form action='ExamPage.jsp' method='post'>");
                    out.println("<input type='radio' name='answer' value='A'> " + rs.getString("OptionA") + "<br>");
                    out.println("<input type='radio' name='answer' value='B'> " + rs.getString("OptionB") + "<br>");
                    out.println("<input type='radio' name='answer' value='C'> " + rs.getString("OptionC") + "<br>");
                    out.println("<input type='radio' name='answer' value='D'> " + rs.getString("OptionD") + "<br>");
                    out.println("<button type='submit' name='nextQuestionIndex' value='" + (questionIndex + 1) + "'>Next</button>");
                    if (questionIndex >= totalQuestions) {
                        out.println("<button type='submit' name='finish' formaction='SubmitExam.jsp'>Finish</button>");
                    }
                    out.println("</form>");
                    out.println("</div>");
                } else {
                    out.println("<p>No more questions available.</p>");
                    out.println("<form action='SubmitExam.jsp' method='post'>");
                    out.println("<button type='submit' name='finish'>Finish</button>");
                    out.println("</form>");
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </div>
</body>
</html>
