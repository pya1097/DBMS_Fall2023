import java.util.*;
import java.sql.*;


public class InformationProcessingOperations {
	private static PreparedStatement finalQuery = null;

    public static void addParkingLot(Connection connection, Statement statement) {
    	try {
        	String query = "INSERT INTO ParkingLot (ParkingLotID, Name, Address) VALUES (?, ?, ?)";
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("You are adding a Parking Lot..");
            System.out.println("Specify the Parking Lot ID: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the Parking Lot Name: ");
            String parkingLotName = scanner.nextLine();
            System.out.println("Specify the Parking Lot Address: ");
            String parkingLotAddress = scanner.nextLine();
       
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                finalQuery.setString(2, parkingLotName);
                finalQuery.setString(3, parkingLotAddress);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Parking Lot is Added Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in addParkingLot Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in addParkingLot Operation. Hardware/Inputs are malformed..");
        }
    }

    public static void addZone(Connection connection, Statement statement) {
    	try {
        	String query = "INSERT INTO Zone (ParkingLotID, ZoneID) VALUES (?, ?)";
            Scanner scanner = new Scanner(System.in);
 
            System.out.println("You are adding a Zone to a Parking Lot..");
            System.out.println("Specify the Parking Lot ID where you intend to add a Zone: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the Zone: ");
            String zoneID = scanner.nextLine();
       
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                finalQuery.setString(2, zoneID);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Zone is Added Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in addZone Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in addZone Operation. Hardware/Inputs are malformed..");
        }
    }
    
    public static void addSpace(Connection connection, Statement statement) {
    	try {
        	String query = "INSERT INTO Space (ParkingLotID, ZoneID, SpaceType, SpaceNumber, AvailabilityStatus) VALUES (?, ?, ?, ?, ?)";
            Scanner scanner = new Scanner(System.in);
 
            System.out.println("You are adding a Space to a Parking Lot and as associated Zone..");
            System.out.println("Specify the Parking Lot ID where you intend to add the Space: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the associated Zone ID where you intend to add the Space: ");
            String zoneID = scanner.nextLine();
            System.out.println("Specify the Space Type you want to add (Electric, Regular, Compact Car, Handicap, Regular): ");
            String spaceType = scanner.nextLine();
            System.out.println("Specify the Space Number you want to add: ");
            String spaceNumber = scanner.nextLine();
            System.out.println("Specify the Availability Status (Y, N or Optional - Simply press Enter without entering any value): ");
            // If user does not want to pass anything, then length will remain zero. So, based on that we can add the check on Optional Param.
            String availabilityStatus = "";
            availabilityStatus = scanner.nextLine();
       
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                finalQuery.setString(2, zoneID);
                finalQuery.setString(3, spaceType);
                finalQuery.setInt(4, Integer.parseInt(spaceNumber));
                finalQuery.setString(5, availabilityStatus.length() > 0 ? availabilityStatus : "Y");
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Space is Added Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in addSpace Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in addSpace Operation. Hardware/Inputs are malformed..");
        }
    }

    public static void addDriver(Connection connection, Statement statement) {
        /*
         * TODO: This method should be overloaded for dependent operations.
         *       For example - IssuePermit Will need to call addDriver and add details.
         *       Needed for such cases. Coordinate with Owner of Operations 2.
         */
    	try {
        	String query = "INSERT INTO Driver (DriverID,Status,DriverName) VALUES (?, ?, ?)";
            Scanner scanner = new Scanner(System.in);
 
            System.out.println("You are adding a Driver Details into the System..");
            System.out.println("If the driver is a Visitor, please specify the Phone Number. Otherwise, provide the University ID: ");
            String driverID = scanner.nextLine();
            System.out.println("Specify the Status of the Driver (V, E, S): ");
            String driverStatus = scanner.nextLine();
            System.out.println("Specify the Driver Name: ");
            String driverName = scanner.nextLine();
       
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setString(1, driverID);
                finalQuery.setString(2, driverStatus);
                finalQuery.setString(3, driverName);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Driver Details are Added Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in addDriver Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in addDriver Operation. Hardware/Inputs are malformed..");
        }
    }

    

}
