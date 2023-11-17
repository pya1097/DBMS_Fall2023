import java.sql.*;
import java.util.Scanner;
// import java.util.*;
// import MenuOfOperations;


public class WolfParkingSystem {
	static private Connection connection = null;
	static private Statement stmt = null;
	static Scanner scanner;
	static private String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/jkteluku";
    
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
	
	private static void connectToDatabase() throws ClassNotFoundException,SQLException {
		/*
		 * 
		 * Description: This method helps in connecting to
		 * the Database System of the Course
		 * 
		 * TODO: Add the drop table operations after the database is connected
		 * so as to fill the data.
		 * 
		 */
		
		Class.forName("org.mariadb.jdbc.Driver");
        String user = "jkteluku";
        String password = "200477972";
        connection = DriverManager.getConnection(url, user, password);
        stmt = connection.createStatement();
            	
	}
	
	private static void initialize() {
		/*
		 * Description: Initializes the Connection to Database.
		 * 
		 */
		try {
			connectToDatabase();
			scanner = new Scanner(System.in);
		} catch (Exception error) {
			error.printStackTrace();
		}
		
	}
	
	private static void prepareDB() {
	    try {
			stmt.executeUpdate("DROP DATABASE jkteluku;");
			stmt.executeUpdate("CREATE DATABASE jkteluku;");
			stmt.executeUpdate("USE jkteluku;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void initDBTables() throws SQLException {
		try { 
			
			stmt.executeUpdate("CREATE TABLE ParkingLot (\n"
					+ "	ParkingLotID INT AUTO_INCREMENT,\n"
					+ "	Name VARCHAR(50) NOT NULL,\n"
					+ "	Address VARCHAR(100) NOT NULL,\n"
					+ "	PRIMARY KEY (ParkingLotID)\n"
					+ ");");
			
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
					+ "	);");
			
			stmt.executeUpdate("CREATE TABLE Space (\n"
					+ "  ParkingLotID INT,\n"
					+ "  ZoneID ENUM(\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS'),\n"
					+ "  SpaceType ENUM('Electric', 'Handicap', 'Regular', 'Compact Car') DEFAULT 'Regular',\n"
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
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Driver(\n"
					+ "  DriverID varchar(10),\n"
					+ "  DriverName varchar(20) NOT NULL,\n"
					+ "  Status ENUM('E', 'S', 'V') NOT NULL,\n"
					+ "  Primary Key(DriverID)\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Vehicle (\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  Model varchar(30) NOT NULL,\n"
					+ "  Color varchar(30) NOT NULL,\n"
					+ "  Manufacturer varchar(15) NOT NULL,\n"
					+ "  `Year` Year NOT NULL,\n"
					+ "  Primary Key(CarLicenseNumber)\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Owns (\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  DriverID varchar(10),\n"
					+ "  Primary Key (CarLicenseNumber, DriverID),\n"
					+ "  Foreign Key (CarLicenseNumber) REFERENCES Vehicle(CarLicenseNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (DriverID) REFERENCES Driver(DriverID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Permit (\n"
					+ "  PermitID int AUTO_INCREMENT,\n"
					+ "  ParkingLotID int,\n"
					+ "  ZoneID ENUM(\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS'),\n"
					+ "  SpaceType ENUM('Electric', 'Handicap', 'Regular', 'Compact Car') DEFAULT 'Regular',\n"
					+ "  StartDate Date NOT NULL,\n"
					+ "  ExpirationDate Date NOT NULL,\n"
					+ "  ExpirationTime Time NOT NULL,\n"
					+ "  PermitType ENUM('Residential', 'Commuter', 'PeakHours','Special event','ParkandRide') NOT NULL,\n"
					+ "  Primary Key(PermitID),\n"
					+ "  Foreign Key (ParkingLotID, ZoneID) REFERENCES Zone(ParkingLotID, ZoneID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID, ZoneID, SpaceType) REFERENCES Space(ParkingLotID, ZoneID, SpaceType) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Given (\n"
					+ " PermitID int,\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  Primary Key (PermitID, CarLicenseNumber),\n"
					+ "  Foreign Key (PermitID) REFERENCES Permit(PermitID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (CarLicenseNumber) REFERENCES Vehicle (CarLicenseNumber) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Holds (\n"
					+ "  PermitID	 int,\n"
					+ "  DriverID varchar(12),\n"
					+ "  Primary Key (PermitID, DriverID),\n"
					+ "  Foreign Key (PermitID) REFERENCES Permit(PermitID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (DriverID) REFERENCES  Driver(DriverID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Citation (\n"
					+ "  CitationNumber int AUTO_INCREMENT,\n"
					+ "  ParkingLotID int,\n"
					+ "  CitationDate Date NOT NULL,\n"
					+ "  CitationTime Time NOT NULL,\n"
					+ "  CategoryType ENUM('Invalid Permit', 'Expired Permit', 'No Permit') NOT NULL,\n"
					+ "  AmountDue float NOT NULL,\n"
					+ "  PaymentStatus ENUM('Paid', 'Due') NOT NULL DEFAULT 'Due',\n"
					+ "  Primary Key(CitationNumber),\n"
					+ "  Foreign Key (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE IssuedTo (\n"
					+ "  CitationNumber int,\n"
					+ "  CarLicenseNumber varchar(12),\n"
					+ "  Primary Key (CitationNumber,CarLicenseNumber),\n"
					+ "  Foreign Key (CitationNumber) REFERENCES Citation(CitationNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (CarLicenseNumber) REFERENCES Vehicle(CarLicenseNumber) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Associated (\n"
					+ "  ParkingLotID int,\n"
					+ "  ZoneID ENUM (\n"
					+ "	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',\n"
					+ "	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',\n"
					+ "	'U', 'V', 'W', 'X', 'Y', 'Z', 'AS', 'BS', 'CS', 'DS',\n"
					+ "	'ES', 'FS', 'GS', 'HS', 'JS', 'KS', 'LS', 'MS', 'NS', 'OS',\n"
					+ "	'PS', 'QS', 'RS', 'SS', 'TS', 'US', 'VS', 'WS', 'XS',\n"
					+ "	'YS', 'ZS'),\n"
					+ "  SpaceNumber int,\n"
					+ "  SpaceType ENUM('Electric', 'Handicap', 'Regular', 'Compact Car') DEFAULT 'Regular',\n"
					+ "  PermitID int,\n"
					+ "  Primary Key(ParkingLotID,ZoneID,SpaceNumber,SpaceType,PermitID),\n"
					+ "  Foreign Key (PermitID) REFERENCES Permit(PermitID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID) REFERENCES ParkingLot(ParkingLotID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID, ZoneID) REFERENCES Zone(ParkingLotID, ZoneID) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (ParkingLotID, ZoneID, SpaceType, SpaceNumber) REFERENCES Space(ParkingLotID, ZoneID, SpaceType, SpaceNumber) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Pays (\n"
					+ "  CitationNumber int ,\n"
					+ "  DriverID varchar(10),\n"
					+ "  Primary Key (CitationNumber, DriverID),\n"
					+ "  Foreign Key (CitationNumber) REFERENCES Citation(CitationNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (DriverID) REFERENCES Driver(DriverID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
			
			stmt.executeUpdate("CREATE TABLE Appeals (\n"
					+ "  CitationNumber int,\n"
					+ "  DriverID varchar(10),\n"
					+ "  DriverRemark varchar(100),\n"
					+ "  AdminRemark varchar(100),\n"
					+ "  Primary Key (CitationNumber, DriverID),\n"
					+ "  Foreign Key (CitationNumber) REFERENCES Citation(CitationNumber) ON UPDATE CASCADE ON DELETE CASCADE,\n"
					+ "  Foreign Key (DriverID) REFERENCES Driver(DriverID) ON UPDATE CASCADE ON DELETE CASCADE\n"
					+ ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadDataForDemo() throws SQLException {
		try {
			stmt.executeUpdate("INSERT INTO ParkingLot (ParkingLotID, Name, Address) VALUES\n"
					+ "(1, 'Poulton Deck', '1021 Main Campus Dr Raleigh, NC, 27606'),\n"
					+ "(2, 'Partners Way Deck', '851 Partners Way Raleigh, NC, 27606'),\n"
					+ "(3, 'Dan Allen Parking Deck', '110 Dan Allen Dr Raleigh, NC, 27607');");
			
			stmt.executeUpdate("INSERT INTO Zone (ParkingLotID, ZoneID) VALUES\n"
					+ "(1, 'A'),\n" + "(1, 'B'),\n" + "(1,'BS'),\n" + "(1, 'V'),\n" + "(2, 'AS'),\n" + "(2, 'BS'),\n" + "(3, 'V');");
			
			stmt.executeUpdate("INSERT INTO Space (ParkingLotID, ZoneID, SpaceType, SpaceNumber, AvailabilityStatus) VALUES\n"
					+"(1, 'A', 'Electric', 1, 'N'),\n"
					+ "(2, 'A', 'Regular', 1, 'N'),\n"
					+ "(3, 'A', 'Regular', 2, 'N'),\n"
					+ "(1, 'AS', 'Compact Car', 1, 'N'),\n"
					+ "(2, 'V', 'Handicap', 1, 'N'),\n"
					+ "(3, 'V', 'Regular', 1, 'N');");
					
			stmt.executeUpdate("INSERT INTO Vehicle (CarLicenseNumber,Model,Color,Manufacturer,`Year`) VALUES\n"
					+ "( 'SBF', 'GT-R-Nismo', 'Pearl White TriCoat', 'Nissan', 2024),\n"
					+ "( 'Clay1', 'Model S', 'Ultra Red', 'Tesla', 2023),\n"
					+ "( 'Hicks1', 'M2 Coupe', 'Zandvoort Blue', 'BMW', 2024),\n"
					+ "( 'Garcia1', 'Continental GT Speed', 'Blue Fusion', 'Bentley', 2024),\n"
					+ "( 'CRICKET', 'Civic SI', 'Sonic Gray Pear', 'Honda', 2024),\n"
					+ "( 'PROFX', 'Taycan Sport Turismo', 'Frozenblue Metallic', 'Porsche', 2024),\n"
					+ "( 'VAN-9910', 'Macan GTS', 'Papaya Metallic', 'Porsche', 2024);");
			
			
			stmt.executeUpdate("INSERT INTO Driver (DriverID,Status,DriverName)\n"
					+ "VALUES\n"
					+ "('7729119111','V', 'Sam BankmanFried'),\n"
					+ "('266399121','E', 'John Clay'),\n"
					+ "('366399121','E', 'Julia Hicks'),\n"
					+ "('466399121','E', 'Ivan Garcia'),\n"
					+ "('122765234','S', 'Sachin Tendulkar'),\n"
					+ "('9194789124','V', 'Charles Xavier');");
			
			stmt.executeUpdate("INSERT INTO Owns(CarLicenseNumber,DriverID)\n"
					+ "values\n"
					+ "('SBF','7729119111'),\n"
					+ "('Clay1','266399121'),\n"
					+ "('Hicks1','366399121'),\n"
					+ "('Garcia1','466399121'),\n"
					+ "('CRICKET','122765234'),\n"
					+ "('PROFX','9194789124');");
			
			stmt.executeUpdate("INSERT INTO Permit (PermitID,PermitType,ParkingLotID,ZoneID,SpaceType,StartDate,ExpirationDate,ExpirationTime) VALUES			\n"
					+ "(1,'Commuter',1,'V', 'Regular','2023-01-01', '2024-01-01', '06:00:00'),\n"
					+ "(2,'Residential',1,'A', 'Electric','2010-01-01', '2030-01-01', '06:00:00'),\n"
					+ "(3,'Commuter',1,'A', 'Regular','2023-01-01', '2024-01-01', '06:00:00'),\n"
					+ "(4,'Commuter',1,'A', 'Regular','2023-01-01', '2024-01-01', '06:00:00'),\n"
					+ "(5,'Residential',2,'AS', 'Compact Car','2022-01-01', '2023-09-30', '06:00:00'),\n"
					+ "(6,'Special event',1,'V', 'Handicap','2023-01-01', '2023-11-15', '06:00:00');");
			
			stmt.executeUpdate("INSERT INTO Associated (ParkingLotID,ZoneID,SpaceType,SpaceNumber,PermitID) values\n"
					+ "(1, 'A', 'Electric', 1, 2),\n"
					+ "(1, 'A', 'Regular', 1, 3),\n"
					+ "(1, 'A', 'Regular', 2, 4),\n"
					+ "(2, 'AS', 'Compact Car', 1, 5),\n"
					+ "(1, 'V', 'Handicap', 1, 6),\n"
					+ "(1, 'V', 'Regular', 1, 1);");
			
			stmt.executeUpdate("insert into Given(PermitID,CarLicenseNumber) values\n"
					+ "(1,'SBF'),\n"
					+ "(2,'Clay1'),\n"
					+ "(3,'Hicks1'),\n"
					+ "(4,'Garcia1'),\n"
					+ "(5,'CRICKET'),\n"
					+ "(6,'PROFX');");
			
			stmt.executeUpdate("insert into Holds(PermitID, DriverID) values\n"
					+ "(1,'7729119111'),\n"
					+ "(2,'266399121'),\n"
					+ "(3,'366399121'),\n"
					+ "(4,'466399121'),\n"
					+ "(5,'122765234'),\n"
					+ "(6,'9194789124');");
			
			stmt.executeUpdate("INSERT INTO Citation (CitationNumber, ParkingLotID, CitationDate, CitationTime, CategoryType, AmountDue,PaymentStatus)			\n"
					+ "VALUES			\n"
					+ "(1,3, '2021-10-11', '08:00:00', 'No Permit',0,'Paid'),\n"
					+ "(2,1,'2023-10-01', '08:00:00', 'Expired Permit', 30.00,'Due');");
			
			stmt.executeUpdate("INSERT INTO IssuedTo (CitationNumber, CarLicenseNumber)\n"
					+ "VALUES\n"
					+ "(1, 'VAN-9910');");
			
			stmt.executeUpdate("INSERT INTO IssuedTo (CitationNumber, CarLicenseNumber)\n"
					+ "VALUES\n"
					+ "(2, 'CRICKET');");
			/*
			stmt.executeUpdate("INSERT INTO Pays (CitationNumber, DriverID)\n"
					+ "VALUES\n"
					+ "(1, '122765234');"); */
			
			stmt.executeUpdate("INSERT INTO Appeals (CitationNumber, DriverID, DriverRemark, AdminRemark)\n"
					+ "VALUES\n"
					+ "(2, '122765234', 'Requesting review of citation', 'Under review by admin');");
					
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void welcomeMenu() throws SQLException {
		String line = "*".repeat(120);
		MenuOfOperations menu = new MenuOfOperations();
		String choiceNumber;
		scanner = new Scanner(System.in);
		String menuMessage = "\n\n" + line + "\n\n\t\t\tWelcome to Operations Menu:\n\n" + line;

        while (true){
			System.out.println(menuMessage);
			System.out.println("(1) Display Operations Related to Information Processing.");
			System.out.println("(2) Display Operations Related to Maintaining Permits and Vehicle Information of Drivers.");
			System.out.println("(3) Display Operations Related to Generating and Maintaining Citations.");
			System.out.println("(4) Display Operations Related to Reports.");
			System.out.println("(5) Reload The Demo Data Again.");
			System.out.println("(6) Display the Data Tables.");
			System.out.println("(7) Car Entrance/Exit Operation.");
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
					menu.displayInformationProcessingOperations(connection, stmt);
					break;
				case "2":
					menu.displayMaintanenceOperationsOfPermitsAndVehicles(connection,stmt);
					break;
				case "3":
					menu.displayCitationOperations(connection, stmt);
					break;
				case "4":
					menu.displayReportOperations(connection, stmt);
					break;
				case "5":
					prepareDB();
					initDBTables();
					loadDataForDemo();
					break;
				case "6":
					menu.displayDataTables(connection, stmt);
					break;
				case "7":
					menu.carParkAndExitOperations(connection, stmt);
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
