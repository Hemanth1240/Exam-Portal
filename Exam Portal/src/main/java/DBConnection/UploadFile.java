package DBConnection;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
@MultipartConfig
public class UploadFile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String path;

    public UploadFile() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    private String sanitizeFileName(String fileName) {
        fileName = fileName.replace(".txt", "").replace(" ", "_").replace(",", "").replace("-", "_");
        return fileName;
    }

    private void saveContentsToDB(HttpServletRequest request, HttpServletResponse response, String fileName) throws ServletException, IOException {
        File file = new File(this.path);
        if (file.exists()) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db", "Hemu", "Hello@World40");
                 PreparedStatement createStmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `" + sanitizeFileName(fileName) + "` (SNO int PRIMARY KEY, Question varchar(1000) NOT NULL, OptionA varchar(100) NOT NULL, OptionB varchar(100) NOT NULL, OptionC varchar(100) NOT NULL, OptionD varchar(100) NOT NULL, Correct_Option varchar(2) NOT NULL)");
                 PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO `" + sanitizeFileName(fileName) + "` (SNO, Question, OptionA, OptionB, OptionC, OptionD, Correct_Option) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                createStmt.executeUpdate();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                    String line;
                    StringBuilder question = new StringBuilder("<pre>");
                    String optionA = "", optionB = "", optionC = "", optionD = "", correctOption = "";
                    int count = 1;

                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("Error") || line.startsWith("java") || line.startsWith("Copy") || line.startsWith("Multiple-choice") || line.contains("Options")) {
                            continue;
                        } else if (line.startsWith("a)")) {
                            optionA = line.substring(3);
                        } else if (line.startsWith("b)")) {
                            optionB = line.substring(3);
                        } else if (line.startsWith("c)")) {
                            optionC = line.substring(3);
                        } else if (line.startsWith("d)")) {
                            optionD = line.substring(3);
                        } else if (line.startsWith("Correct option:")) {
                            correctOption = line.substring(16, 17);
                            question.append("</pre>");
                            insertStmt.setInt(1, count);
                            insertStmt.setString(2, question.toString());
                            insertStmt.setString(3, optionA);
                            insertStmt.setString(4, optionB);
                            insertStmt.setString(5, optionC);
                            insertStmt.setString(6, optionD);
                            insertStmt.setString(7, correctOption);
                            insertStmt.executeUpdate();
                            question.setLength(0);
                            question.append("<pre>");
                            count++;
                        } else {
                            question.append(line).append("<br>");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().println("No such file!");
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Part part = request.getPart("file");
            String fileName = part.getSubmittedFileName();
            this.path = getServletContext().getRealPath("/") + "files" + File.separator + fileName;

            if (uploadFile(part.getInputStream(), this.path)) {
                out.println("File uploaded: " + this.path);
                saveContentsToDB(request, response, fileName);
            } else {
                out.println("Error in file upload.");
            }
        }
    }

    private boolean uploadFile(InputStream is, String path) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
