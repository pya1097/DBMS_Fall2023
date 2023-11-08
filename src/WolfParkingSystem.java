import java.sql.*;
import java.util.Scanner;
import java.util.*;
import MenuOfOperations;


public class WolfParkingSystem {
	static private Connection connection = null;
	static private Statement stmt = null;
	static private String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/jkteluku";
	static private String userName = "jkteluku";
    static private String passWord = "200477972";
    
	public static void welcomePrompt() {
		String projectName = "CSC 540 Project Demo: Wolf Parking Management System";
		String line = "=".repeat(120);
		String welcomeMessage = line + "\n\n\t\t\tWelcome to " + projectName + "\n\n" + line;
		System.out.println(welcomeMessage);
	}

	private static void close() {
		/*
		 * Description: This method helps in closing the connection
		 * with the database.
		 */
		if(connection != null){
			try {
				System.out.println("Connection is closed with the Database...");
                connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(stmt != null){
			try {
				System.out.println("Connection is closed with the Database...");
                stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
            stmt = connection.createStatement();
            System.out.println("Connected to the database...");
            // Need to Drop the Tables here.
            	
        } catch (SQLException error) {
        	System.err.println("Failure in Connecting to the Database...");
        	error.printStackTrace();
        } finally {
        	close();
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
			initDBTables();
		} catch (SQLException error) {
			error.printStackTrace();
		}
		
	}
	private static void initDBTables() throws SQLException {
		try { 
			
			stmt.executeUpdate("CREATE TABLE ParkingLot (\n"
					+ "	ParkingLotID INT AUTO_INCREMENT,\n"
					+ "	NAME VARCHAR(20) NOT NULL,\n"
					+ "	Address VARCHAR(50) NOT NULL,\n"
					+ "	PRIMARY KEY (ParkingLotID)\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Zone (\n"
					+ "	ParkingLotID INT,\n"
					+ "	ZoneID ENUM(\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS'),\n"
					+ "	PRIMARY KEY (ParkingLotID,ZoneID),\n"
					+ "	FOREIGN KEY (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ "	)");
			
			stmt.executeUpdate("CREATE TABLE Space (\n"
					+ "  ParkingLotID INT,\n"
					+ "  ZoneID ENUM(\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS'),\n"
					+ "  SpaceType ENUM('Electric', 'Handicap', 'Regular', 'Compact') DEFAULT 'Regular',\n"
					+ "  SpaceNumber INT,\n"
					+ "  AvailabilityStatus ENUM('Y', 'N') NOT NULL DEFAULT 'Y',\n"
					+ "  PRIMARY KEY (\n"
					+ "    ParkingLotID,\n"
					+ "    ZoneID,\n"
					+ "    SpaceType,\n"
					+ "    SpaceNumber\n"
					+ "  ),\n"
					+ "  FOREIGN KEY (ParkingLotID, ZoneID) REFERENCES Zone(ParkingLotID, ZoneID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  FOREIGN KEY (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Driver(\n"
					+ "  ID varchar(10),\n"
					+ "  DriverName varchar(20) NOT NULL,\n"
					+ "  Status ENUM('E', 'S', 'V') NOT NULL,\n"
					+ "  Primary Key(ID)\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Vehicle (\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  Model varchar(15) NOT NULL,\n"
					+ "  Color varchar(15) NOT NULL,\n"
					+ "  Manufacturer varchar(15) NOT NULL,\n"
					+ "  `Year` Year NOT NULL,\n"
					+ "  Primary Key(CarLicenseNumber)\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Owns (\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  ID varchar(10),\n"
					+ "  Primary Key (CarLicenseNumber, ID),\n"
					+ "  Foreign Key (CarLicenseNumber) REFERENCES Vehicle(CarLicenseNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ID) REFERENCES Driver(ID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Permit (\n"
					+ "  PermitID int AUTO_INCREMENT,\n"
					+ "  ParkingLotID int,\n"
					+ "  ZoneID ENUM(\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS', 'V'),\n"
					+ "  SpaceType ENUM('Electric', 'Handicap', 'Regular', 'Compact') DEFAULT 'Regular',\n"
					+ "  StartDate Date NOT NULL,\n"
					+ "  ExpirationDate Date NOT NULL,\n"
					+ "  ExpirationTime Time NOT NULL,\n"
					+ "  PermitType ENUM('Residential', 'Commuter', 'PeakHours','SpecialEvent','ParkandRide') NOT NULL,\n"
					+ "  Primary Key(PermitID),\n"
					+ "  Foreign Key (ParkingLotID, ZoneID) REFERENCES Zone(ParkingLotID, ZoneID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID, ZoneID, SpaceType) REFERENCES Space(ParkingLotID, ZoneID, SpaceType) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Given (\n"
					+ " PermitID int,\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  Primary Key (PermitID, CarLicenseNumber),\n"
					+ "  Foreign Key (PermitID) REFERENCES Permit(PermitID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (CarLicenseNumber) REFERENCES Vehicle (CarLicenseNumber) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Holds (\n"
					+ "  PermitID	 int,\n"
					+ "  ID varchar(12),\n"
					+ "  Primary Key (PermitID, ID),\n"
					+ "  Foreign Key (PermitID) REFERENCES Permit(PermitID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ID) REFERENCES  Driver(ID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Citation (\n"
					+ "  CitationNumber int AUTO_INCREMENT,\n"
					+ "  ParkingLotID int,\n"
					+ "  CitationDate Date NOT NULL,\n"
					+ "  CitationTime Time NOT NULL,\n"
					+ "  CategoryType ENUM('Invalid Permit', 'Expired Permit', 'No Permit') NOT NULL,\n"
					+ "  AmountDue float NOT NULL,\n"
					+ "  PaymentStatus ENUM('Paid', 'Unpaid') NOT NULL DEFAULT 'Unpaid',\n"
					+ "  Primary Key(CitationNumber),\n"
					+ "  Foreign Key (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE IssuedTo (\n"
					+ "  CitationNumber int,\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  Primary Key (CitationNumber,CarLicenseNumber),\n"
					+ "  Foreign Key (CitationNumber) REFERENCES Citation(CitationNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (CarLicenseNumber) REFERENCES Vehicle(CarLicenseNumber) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Associated (\n"
					+ "  ParkingLotID int,\n"
					+ "  ZoneID ENUM (\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS', 'V'),\n"
					+ "  SpaceNumber int,\n"
					+ "  SpaceType ENUM('Electric', 'Handicap', 'Regular', 'Compact') DEFAULT 'Regular',\n"
					+ "  PermitID int,\n"
					+ "  Primary Key(ParkingLotID,ZoneID,SpaceNumber,SpaceType,PermitID),\n"
					+ "  Foreign Key (PermitID) REFERENCES Permit(PermitID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID, ZoneID) REFERENCES Zone(ParkingLotID, ZoneID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID, ZoneID, SpaceType, SpaceNumber) REFERENCES Space(ParkingLotID, ZoneID, SpaceType, SpaceNumber) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Pays (\n"
					+ "  CitationNumber int ,\n"
					+ "  ID varchar(10),\n"
					+ "  Primary Key (CitationNumber, ID),\n"
					+ "  Foreign Key (CitationNumber) REFERENCES Citation(CitationNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ID) REFERENCES Driver(ID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
			
			stmt.executeUpdate("CREATE TABLE Appeals (\n"
					+ "  CitationNumber int,\n"
					+ "  ID varchar(10),\n"
					+ "  DriverRemark varchar(100),\n"
					+ "  AdminRemark varchar(100),\n"
					+ "  Primary Key (CitationNumber, ID),\n"
					+ "  Foreign Key (CitationNumber) REFERENCES Citation(CitationNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ID) REFERENCES Driver(ID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void welcomeMenu() throws SQLException {
		String line = "*".repeat(120);
		MenuOfOperations menu = new MenuOfOperations();
		String choiceNumber;
		Scanner scanner = new Scanner(System.in);
		String menuMessage = "\n\n" + line + "\n\n\t\t\tWelcome to Operations Menu:\n\n" + line;

        while (true){
			System.out.println(menuMessage);
			System.out.println("(1) Display Operations Related to Information Processing.");
			System.out.println("(2) Display Operations Related to Maintaining Permits and Vehicle Information of Drivers.");
			System.out.println("(3) Display Operations Related to Generating and Maintaining Citations.");
			System.out.println("(4) Display Operations Related to Reports.");
			System.out.println("(0) Exit the Menu.");
			System.out.print("\n Select your choice: ");
			
			choiceNumber = scanner.nextLine();
			System.out.println("\n Selected Choice: " + choiceNumber);
        	
			
			switch(choiceNumber) {
				case "0":
					System.out.println("\nSelected to Exit. Closing all associated connections, safely. Good bye!!!\n");
					scanner.close();
					close();
					System.exit(0);
					break;
				case "1":
					menu.displayInformationProcessingOperations();
					break;
				case "2":
					menu.displayMaintanenceOperationsOfPermitsAndVehicles();
					break;
				case "3":
					menu.displayCitationOperations();
					break;
				case "4":
					menu.displayReportOperations();
					break;
				default:
					System.out.println("\nBroken. Choose the Choices from the Available Options only. Try again...\n");
					break;
			}

		} 	
	}
		

	
	public static void main(String[] args) throws SQLException {
		welcomePrompt();
		initialize();
		welcomeMenu();
		close();
    }
		

}
