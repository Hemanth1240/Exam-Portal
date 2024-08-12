<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header class="PageHeader py-3">
        <div class="container">
            <div class="row align-items-center">
                <div class="col text-center">
                    <span class="brand-name">Wipro <sub>Exam Practice</sub></span>
                </div>
                <div class="col-auto ms-auto">
                    <%
                        String username = (String) session.getAttribute("username");
                        if (username != null) {
                    %>
                        <span class="username"><%= username %></span>
                    <%
                        } else {
                    %>
                        <a href="UserLogin.html" id="b1" class="btn btn-light me-2">Login</a>
                        <a href="register.html" id="b1" class="btn btn-light">Sign Up</a>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </header>

    <section class="content exam-container">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>S.No</th>
                    <th>Name of Exam</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td><a href="ExamPage.jsp">Interfaces</a></td>
                </tr>
            </tbody>
        </table>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
