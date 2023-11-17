import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CarParkOperations {
	private static PreparedStatement finalQueryToInsertIntoAssociated = null;
	private static PreparedStatement finalUpdateAvailabilityInSpaceQuery = null;
	private static PreparedStatement finaldeleteFromAssociateTable = null;
	
    public static void carEntrance(Connection connection, Statement statement) {
    	try {
    		Scanner scanner = new Scanner(System.in);
			String parkingLotID = null;
			String parkingZoneID = null;
			String spaceType = null;

			System.out.println("Flash your Permit ID: ");
			String permitID = scanner.nextLine();

			String fetchParkingLotAttributesQuery = "SELECT ParkingLotID, ZoneID, SpaceType FROM Permit WHERE PermitID ="+permitID+";";
            ResultSet result = statement.executeQuery(fetchParkingLotAttributesQuery);
			

			while(result.next()) {
        		parkingLotID = result.getObject(1).toString();
            	parkingZoneID = result.getObject(2).toString();
				spaceType = result.getObject(3).toString();
        	}
            
			String query = "select SpaceNumber from Space where AvailabilityStatus = 'Y' and ParkingLotID ="+ parkingLotID+" and ZoneID = '"+parkingZoneID+"' and SpaceType ='"+spaceType+"' limit 1;";
			String availableSpaceNumber = ReportOperations.getAvailableSpaceNumber(connection, statement, parkingLotID, parkingZoneID, spaceType, query);
        	
			if (availableSpaceNumber.equals("NoSpace")) {
				System.out.println("Sorry. No Space Available at this Point of Time, reach the Onsite Parking Team");
			} else {
				try {
					connection.setAutoCommit(false);
					String queryToInsertIntoAssociated = "INSERT INTO Associated (ParkingLotID,ZoneID,SpaceType,SpaceNumber,PermitID) values (?, ?, ?, ?, ?);";
					finalQueryToInsertIntoAssociated = connection.prepareStatement(queryToInsertIntoAssociated);
					finalQueryToInsertIntoAssociated.setInt(1,Integer.parseInt(parkingLotID));
					finalQueryToInsertIntoAssociated.setString(2,parkingZoneID);
					finalQueryToInsertIntoAssociated.setString(3,spaceType);
					finalQueryToInsertIntoAssociated.setInt(4,Integer.parseInt(availableSpaceNumber));
					finalQueryToInsertIntoAssociated.setInt(5,Integer.parseInt(permitID));
					finalQueryToInsertIntoAssociated.executeUpdate();

					System.out.println("Vehicle is Inserted Into Associated Table...");
					System.out.println("Status is Updating In Space...");

					String updateAvailabilityInSpaceQuery = "UPDATE Space SET AvailabilityStatus = 'N' WHERE ParkingLotID = ? AND ZoneID = ? AND SpaceType = ? AND SpaceNumber = ?;";
					finalUpdateAvailabilityInSpaceQuery = connection.prepareStatement(updateAvailabilityInSpaceQuery);
					finalUpdateAvailabilityInSpaceQuery.setInt(1,Integer.parseInt(parkingLotID));
					finalUpdateAvailabilityInSpaceQuery.setString(2,parkingZoneID);
					finalUpdateAvailabilityInSpaceQuery.setString(3,spaceType);
					finalUpdateAvailabilityInSpaceQuery.setInt(4,Integer.parseInt(availableSpaceNumber));
					finalUpdateAvailabilityInSpaceQuery.executeUpdate();
					System.out.println("Status is Updated In Space...");
					connection.commit();
				} catch (SQLException error) {
					System.out.println(error.getMessage());
					System.out.println("Issue in InsertInToAssociated/UpdateInSpace Operation. Hardware/Inputs are malformed..");
					connection.rollback();
					System.out.println("Rollback Complete!");
				} finally {
					connection.setAutoCommit(true);
				}
        	}
			} catch (Exception e) {
            System.out.println("Issue in carEntrance() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }


	public static void carExit(Connection connection, Statement statement) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Flash your Permit ID: ");
			String permitID = scanner.nextLine();

			String parkingLotID = null;
			String parkingZoneID = null;
			String spaceType = null;

			String fetchParkingLotAttributesQuery = "SELECT ParkingLotID, ZoneID, SpaceType FROM Permit WHERE PermitID ="+permitID+";";
            ResultSet result = statement.executeQuery(fetchParkingLotAttributesQuery);
			

			while(result.next()) {
        		parkingLotID = result.getObject(1).toString();
            	parkingZoneID = result.getObject(2).toString();
				spaceType = result.getObject(3).toString();
        	}
            
			try {
				String query = "select SpaceNumber from Associated where ParkingLotID ="+ parkingLotID+" and PermitID =" +permitID +" and ZoneID = '"+parkingZoneID+"' and SpaceType ='"+spaceType+"';";
				String availableSpaceNumber = ReportOperations.getAvailableSpaceNumber(connection, statement, parkingLotID, parkingZoneID, spaceType, query);
				
				connection.setAutoCommit(false);
				String deleteFromAssociateTable = "DELETE FROM Associated WHERE PermitID ="+permitID+";";
				finaldeleteFromAssociateTable = connection.prepareStatement(deleteFromAssociateTable);
				finaldeleteFromAssociateTable.executeUpdate();
				System.out.println("Car is Deleted from Associated Successfully!!");

				String updateAvailabilityInSpaceQuery = "UPDATE Space SET AvailabilityStatus = 'Y' WHERE ParkingLotID = ? AND ZoneID = ? AND SpaceType = ? AND SpaceNumber = ?";
				finalUpdateAvailabilityInSpaceQuery = connection.prepareStatement(updateAvailabilityInSpaceQuery);
				finalUpdateAvailabilityInSpaceQuery.setInt(1,Integer.parseInt(parkingLotID));
				finalUpdateAvailabilityInSpaceQuery.setString(2,parkingZoneID);
				finalUpdateAvailabilityInSpaceQuery.setString(3,spaceType);
				finalUpdateAvailabilityInSpaceQuery.setInt(4,Integer.parseInt(availableSpaceNumber));
				finalUpdateAvailabilityInSpaceQuery.executeUpdate();
				System.out.println("Status is Updated In Space...");
				connection.commit();
			}  catch (SQLException error) {
				System.out.println(error.getMessage());
				System.out.println("Issue in DeleteFromAssociated/UpdateInSpace Operation. Hardware/Inputs are malformed..");
				connection.rollback();
				System.out.println("Rollback Complete!");
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (Exception e) {
            System.out.println("Issue in carExit() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
	}
}
    
    


