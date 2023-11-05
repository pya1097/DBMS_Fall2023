import java.sql.*;

public class WolfParkingSystem {
	static private Connection connection = null;
	static private String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";
	static private String userName = "jkteluku";
    static private String passWord = "200477972";
    
	public static void welcomePrompt() {
		String projectName = "CSC 540 Project Demo: Wolf Parking Management System";
		String line = "=".repeat(120);
		String welcomeMessage = line + "\n\n\t\t\tWelcome to " + projectName + "\n\n" + line;
		System.out.println(welcomeMessage);
	}

	private static void closeConnection() {
		/*
		 * Description: This method helps in closing the connection
		 * with the database.
		 */
		try {
            if(connection != null) {
            	System.out.println("Connection is closed with the Database...");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private static void connectToDatabase() throws SQLException {
		/*
		 * Took the inspiration from HW2 Work
		 * with regard to Connecting to Databases.
		 * 
		 * Description: This method helps in connecting to
		 * the Database System of the Course
		 * 
		 * TODO: Add the drop table operations after the database is connected
		 * so as to fill the data.
		 * 
		 */
		
        System.out.println("URL, Username, Password are configured...");

        try {
            connection = DriverManager.getConnection(url, userName, passWord);
            System.out.println("Connected to the database...");
            // Need to Drop the Tables here.
            	
        } catch (SQLException error) {
        	System.err.println("Failure in Connecting to the Database...");
        	error.printStackTrace();
        } finally {
        	closeConnection();
        }
        
	}
	
	private static void initialize() throws SQLException {
		/*
		 * Description: Initializes the Connection to Database.
		 * 
		 * TODO: Add Sample Data Given by TAs after connectToDatabase();
		 */
		try {
			connectToDatabase();
			// Add the DDL/DML Commands for Tables and Data.
		} catch (SQLException error) {
			error.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
		welcomePrompt();
		initialize();
		closeConnection();
    }
		
	

}
