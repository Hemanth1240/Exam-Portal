package DBConnection;

import java.sql.*;

public class PasswordUpdation {
    private String email;
    private String pass1;
    private String pass2;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    private boolean isPasswordStrong() {
        // Regular expression to check password strength
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return pass1.matches(passwordRegex);
    }

    @SuppressWarnings("resource")
	public int passUpdate() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int updateStatus = 0;
        try {
     		Class.forName("com.mysql.cj.jdbc.Driver");
    	}
    	catch(Exception e){
     		e.printStackTrace();
    	}
    	

        try {
        	System.out.println("Getting Connected");    
        	conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db", "Hemu", "Hello@World40");
        	System.out.println("Connected");  
        	stmt = conn.prepareStatement("SELECT * FROM logindetails WHERE emailid=?;");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                updateStatus = 0; // Email not found
            } else {
                if (!pass1.equals(pass2)) {
                    updateStatus = 1; // Passwords do not match
                } else if (!isPasswordStrong()) {
                    updateStatus = 2; // Password is not strong
                } else {
                    // Update password in the database
                    stmt = conn.prepareStatement("UPDATE logindetails SET passkey=? WHERE emailid=?");
                    stmt.setString(1, pass1);
                    stmt.setString(2, email);
                    stmt.executeUpdate();
                    updateStatus = 3; // Password updated successfully
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            // Error occurred
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updateStatus;
    }
}
