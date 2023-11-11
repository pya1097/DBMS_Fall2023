import java.util.*;
import java.sql.*;


public class InformationProcessingOperations {
	private static PreparedStatement finalQuery = null;

    public static void addParkingLot(Connection connection, Statement statement) {
    	try {
        	String query = "INSERT INTO ParkingLot (ParkingLotID, Name, Address) VALUES (?, ?, ?)";
            Scanner scanner = new Scanner(System.in);
            
 
            System.out.println("You are adding a Parking Lot..");
            System.out.println("Enter Parking Lot ID: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Enter Parking Lot Name: ");
            String parkingLotName = scanner.nextLine();
            System.out.println("Enter Parking Lot Address: ");
            String parkingLotAddress = scanner.nextLine();
       
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                finalQuery.setString(2, parkingLotName);
                finalQuery.setString(3, parkingLotAddress);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("ParkingLot is Added Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in addParkingLot Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
                //scanner.close();
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in addParkingLot Operation. Hardware/Inputs are malformed..");
        }
    }

}
