package DBConnection;

import java.sql.*;

public class ValidUser {
    String uname, pass;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int checkUser() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Use the correct driver class name
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db?serverTimezone=UTC", "Hemu", "Hello@World40");
            stmt = conn.prepareStatement("SELECT * FROM logindetails WHERE username=?");
            stmt.setString(1, this.uname);
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) 
                return 0;
            else {
                rs.next();
                if (!(this.pass).equals(rs.getString("passkey"))) 
                    return 1;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 2;
    }
}
