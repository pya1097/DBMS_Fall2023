import java.util.*;
import java.sql.*;


public class InformationProcessingOperations {
	private static PreparedStatement finalQuery = null;
	
	public static final String updateParkingLotNameQuery = "UPDATE ParkingLot SET Name = ? WHERE ParkingLotID = ?";
    public static final String updateParkingLotAddressQuery = "UPDATE ParkingLot SET Address = ? WHERE ParkingLotID = ?";
    public static final String updateSpaceNumberQuery = "UPDATE Space SET SpaceNumber = ? WHERE ParkingLotID = ? AND ZoneID = ? AND SpaceType = ? AND SpaceNumber = ?";
    public static final String updateSpaceTypeQuery = "UPDATE Space SET SpaceType = ? WHERE ParkingLotID = ? AND ZoneID = ? AND SpaceType = ?";
    // public static final String updateZoneIDQuery = "UPDATE Zone SET ZoneID = ? WHERE ParkingLotID = ? AND ZoneID = ?";
    
    
    
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

    public static void updateParkingLot(Connection connection, Statement statement) {
    	try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("You are updating a Parking Lot..");
            System.out.println("Specify the Parking Lot ID for Updating: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify what information you'd like to update for the given Parking Lot ID - Choose Option: ");
            System.out.println("(A) Name of the Parking Lot.");
            System.out.println("(B) Address of the Parking Lot.");
            String parkingLotChoice = scanner.nextLine();
            System.out.println("Specify the desired value to update for Selected Option: ");
            String updatedValue = scanner.nextLine();
            String query;
            switch (parkingLotChoice) {
                case "A":
                    query = updateParkingLotNameQuery;
                    break;
                case "B":
                    query = updateParkingLotAddressQuery;
                    break;
                default:
                    System.out.println("Choose appropriate options only. Try again..");
                    throw new IllegalArgumentException("Invalid Choice is Selected: " + parkingLotChoice);
            }

            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(2, Integer.parseInt(parkingLotID));
                finalQuery.setString(1, updatedValue);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Parking Lot is Updated Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in updateParkingLot Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (IllegalArgumentException argumentError) {
            System.out.println(argumentError.getMessage());
            System.out.println("Invalid attribute given to update..Choose and Try Again!!!");
        } catch (Exception error) {
            System.out.println("Issue in updateParkingLot Operation. Hardware/Inputs are malformed..");
        } 
    }

    public static void updateSpace(Connection connection, Statement statement) {
        /*
         * TODO: This method should be overloaded for dependent operations.
         *       For example - Car Entering Scenario will need to call Associated to fetch the available space, 
         *       generate parking space number. For the space number, we should update the availablity status.
         */
        
    	try {
            Scanner scanner = new Scanner(System.in);
            String query;
            System.out.println("You are updating the Space in a Parking Lot..");
            System.out.println("Specify the Parking Lot ID for the update: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the associated Zone ID for the update: ");
            String zoneID = scanner.nextLine();
            System.out.println("Specify what information you'd like to update for the given Parking Lot and Zone - Choose Option: ");
            System.out.println("(A) Space Type of Space in the Zone.");
            System.out.println("(B) Space Number of Space in the Zone.");
            String spaceChoice = scanner.nextLine();

            try {
            	connection.setAutoCommit(false);

                switch (spaceChoice) {
                    case "A":
                        System.out.println("Specify the Space Type which you'd like to update: ");
                        String oldSpaceType = scanner.nextLine();
                        System.out.println("Specify the desired Space Type to update: ");
                        String newSpaceType = scanner.nextLine();

                        query = updateSpaceTypeQuery;
                        finalQuery = connection.prepareStatement(query);
                        finalQuery.setString(1, newSpaceType);
                        finalQuery.setInt(2, Integer.parseInt(parkingLotID));
                        finalQuery.setString(3, zoneID);
                        finalQuery.setString(4, oldSpaceType);
                        break;

                    case "B":
                        System.out.println("Specify the Space Type which you'd like to update: ");
                        String spaceType = scanner.nextLine();
                        System.out.println("Specify the Space Number you'd like to update: ");
                        String oldSpaceNumber = scanner.nextLine();
                        System.out.println("Specify the desired Space Number to update: ");
                        String newSpaceNumber = scanner.nextLine();

                        query = updateSpaceNumberQuery;
                        finalQuery = connection.prepareStatement(query);
                        finalQuery.setInt(1, Integer.parseInt(newSpaceNumber));
                        finalQuery.setInt(2, Integer.parseInt(parkingLotID));
                        finalQuery.setString(3, zoneID);
                        finalQuery.setString(4, spaceType);
                        finalQuery.setInt(5, Integer.parseInt(oldSpaceNumber));
                        break;

                    default:
                        System.out.println("Choose appropriate options only. Try again..");
                        throw new IllegalArgumentException("Invalid Choice is Selected: " + spaceChoice);
                }

                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Space is Updated Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in updateSpace Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (IllegalArgumentException argumentError) {
            System.out.println(argumentError.getMessage());
            System.out.println("Invalid attribute given to update..Choose and Try Again!!!");
        } catch (Exception error) {
            System.out.println("Issue in updateSpace Operation. Hardware/Inputs are malformed..");
        } 
    }

    


    /**
    Note: I believe updateZone() does not make sense because ParkingLotID and ZoneID both are primary key.
    It would give issues when you have the ParkingLotID, ZoneID is in Permit Table. Example Error:
    (conn=55621) Cannot add or update a child row: a foreign key constraint fails (`jkteluku`.`Permit`, CONSTRAINT `Permit_ibfk_1` FOREIGN KEY (`ParkingLotID`, `ZoneID`) REFERENCES `Zone` (`ParkingLotID`, `ZoneID`) ON DELETE CASCADE ON UPDATE CASCADE)

    public static void updateZone(Connection connection, Statement statement) {
    	try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("You are updating the Zone in a Parking Lot..");
            System.out.println("Specify the ParkingLotID for the update: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the ZoneID for the update: ");
            String zoneID = scanner.nextLine();
            System.out.println("Specify the desired value to update for ZoneID: ");
            String newZoneID = scanner.nextLine();
            String query = updateZoneIDQuery;

            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setString(1, newZoneID);
                finalQuery.setInt(2, Integer.parseInt(parkingLotID));
                finalQuery.setString(3, zoneID);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Zone is Updated Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in updateZone Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception error) {
            System.out.println("Issue in updateZone Operation. Hardware/Inputs are malformed..");
        } 
    }
     * 
     */



}


