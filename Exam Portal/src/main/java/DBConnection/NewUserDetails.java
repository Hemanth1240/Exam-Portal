package DBConnection;
import java.sql.*;
public class NewUserDetails {
	String uname, emailid, pass1, pass2;

	public String getPass2() {
		return pass2;
	}

	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPass1() {
		return pass1;
	}

	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}
	public boolean isPasswordStrong() {
		return (this.pass1).matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
	}
	public int insertUser() {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
     		Class.forName("com.mysql.cj.jdbc.Driver");
    	}
    	catch(Exception e){
     		e.printStackTrace();
    	}
	    try {
	    	conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_db","Hemu","Hello@World40");
	        stmt = conn.prepareStatement("SELECT * FROM logindetails");
	        rs = stmt.executeQuery();

	        if (!rs.next()) {
	            if (!(this.pass1).equals(this.pass2))
	                return 2; // Passwords don't match
	            if (!isPasswordStrong())
	                return 3; // Weak password

	            // Closing previous statement before reusing it
	            stmt.close();
	            stmt = conn.prepareStatement("INSERT INTO logindetails (username, emailid, passkey) VALUES (?, ?, ?)");
	            stmt.setString(1, this.uname);
	            stmt.setString(2, this.emailid);
	            stmt.setString(3, this.pass1);
	            stmt.executeUpdate();
	            return 0; // Success
	        } else {
	            do {
	                if ((this.uname).equals(rs.getString(1)))
	                    return 4; // Username already exists
	                if ((this.emailid).equals(rs.getString(2)))
	                    return 5; // Email already exists
	            } while (rs.next());

	            if (!(this.pass1).equals(this.pass2))
	                return 2; // Passwords don't match
	            if (!isPasswordStrong())
	                return 3; // Weak password

	            // Closing previous statement before reusing it
	            stmt.close();
	            stmt = conn.prepareStatement("INSERT INTO logindetails (username, emailid, passkey) VALUES (?, ?, ?)");
	            stmt.setString(1, this.uname);
	            stmt.setString(2, this.emailid);
	            stmt.setString(3, this.pass1);
	            stmt.executeUpdate();
	            return 0; // Success
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; // Error
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}



}
