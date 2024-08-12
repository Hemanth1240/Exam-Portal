package DBConnection;

import java.sql.*;

public class ResultValidation {
    public int checkAnswer(int totalQuestions, String[] answers, String username) {
        String query = "SELECT Correct_Option FROM `112321`";
        int correctAnswers = 0;
        StringBuilder options = new StringBuilder();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db", "Hemu", "Hello@World40");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            for (int i = 0; i < totalQuestions; i++) {
                if (rs.next()) {
                    String correctOption = rs.getString("Correct_Option");
                    if (answers[i] != null && answers[i].equals(correctOption.toUpperCase())) {
                        correctAnswers++;
                    }

                    if (answers[i] == null || answers[i].equals("Z"))
                        options.append("Z");
                    else
                        options.append(answers[i]);

                    if (i < totalQuestions - 1) {
                        options.append(";"); // Separate answers with a semicolon
                    }
                }
            }

            query = "INSERT INTO results (username, options, totalmarks) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, options.toString());
            pstmt.setInt(3, correctAnswers);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return correctAnswers;
    }
}
