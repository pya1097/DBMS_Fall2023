import java.sql.*;

public class WolfParkingSystem {
	public static void welcomePrompt() {
		String projectName = "CSC 540 Project Demo: Wolf Parking Management System";
		String line = "=".repeat(120);
		String welcomeMessage = line + "\n\n\t\t\tWelcome to " + projectName + "\n\n" + line;
		System.out.println(welcomeMessage);
	}
	
	public static void main(String[] args) throws SQLException {
		welcomePrompt();
		
    }
		
	

}
