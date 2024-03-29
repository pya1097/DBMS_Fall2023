import java.util.*;
import java.sql.*;


public class InformationProcessingOperations {
	private static PreparedStatement finalQuery = null;
	private static PreparedStatement finalSelectPermitIDToDeleteQuery = null;
	private static PreparedStatement finalDeleteDriverQuery = null;
	private static PreparedStatement  finalDeletePermitQuery = null;
    private static ResultSet result = null;
    static Scanner scanner = new Scanner(System.in);
	
	public static final String updateParkingLotNameQuery = "UPDATE ParkingLot SET Name = ? WHERE ParkingLotID = ?";
    public static final String updateParkingLotAddressQuery = "UPDATE ParkingLot SET Address = ? WHERE ParkingLotID = ?";
    public static final String updateSpaceNumberQuery = "UPDATE Space SET SpaceNumber = ? WHERE ParkingLotID = ? AND ZoneID = ? AND SpaceType = ? AND SpaceNumber = ?";
    public static final String updateSpaceTypeQuery = "UPDATE Space SET SpaceType = ? WHERE ParkingLotID = ? AND ZoneID = ? AND SpaceType = ?";
    public static final String updateDriverInfoNameQuery = "UPDATE Driver SET DriverName = ? WHERE DriverID = ?";
    public static final String updateDriverInfoStatusQuery = "UPDATE Driver SET Status = ? WHERE DriverID = ?";
    public static final String deleteParkingLotQuery = "DELETE FROM ParkingLot WHERE ParkingLotID = ?";
    public static final String deleteZoneQuery = "DELETE FROM Zone WHERE ParkingLotID = ? AND ZoneID = ?";
    public static final String deleteSpaceTypeQuery = "DELETE FROM Space WHERE ParkingLotID = ? AND ZoneID = ? and SpaceType = ?";
    public static final String deleteSpaceNumberQuery = "DELETE FROM Space WHERE ParkingLotID = ? AND ZoneID = ? and SpaceType = ? AND SpaceNumber = ?";
    public static final String deleteDriverQuery = "DELETE FROM Driver WHERE DriverID = ?";
    public static final String deletePermitQuery = "DELETE FROM Permit WHERE PermitID = ?";
    public static final String selectPermitIDToDeleteQuery = "SELECT PermitID FROM Holds WHERE DriverID =?";

    
    
    
    public static void addParkingLot(Connection connection, Statement statement) {
    	// Adding a new parking lot
    	try {
        	String query = "INSERT INTO ParkingLot (ParkingLotID, Name, Address) VALUES (?, ?, ?)";
            scanner = new Scanner(System.in);
            
            System.out.println("You are adding a Parking Lot..");
            // isnt this auto increment?
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
    	// Adding a new zone to a given parking lot
    	try {
        	String query = "INSERT INTO Zone (ParkingLotID, ZoneID) VALUES (?, ?)";
            scanner = new Scanner(System.in);
 
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
    	// Adding a new space to a given zone and it's respective parking lot.
    	try {
        	String query = "INSERT INTO Space (ParkingLotID, ZoneID, SpaceType, SpaceNumber, AvailabilityStatus) VALUES (?, ?, ?, ?, ?)";
            scanner = new Scanner(System.in);
 
            System.out.println("You are adding a Space to a Parking Lot and as associated Zone..");
            System.out.println("Specify the Parking Lot ID where you intend to add the Space: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the associated Zone ID where you intend to add the Space: ");
            String zoneID = scanner.nextLine();
            System.out.println("Specify the Space Type you want to add (Electric, Regular, Compact Car, Handicap): ");
            String spaceType = scanner.nextLine();
            System.out.println("Specify the Space Number you want to add: ");
            String spaceNumber = scanner.nextLine();
            System.out.println("Specify the Availability Status (Y, N or Optional - Simply press Enter without entering any value): ");

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
    	// Adding a new driver information into the system.

    	try {
        	
            scanner = new Scanner(System.in);
 
            System.out.println("You are adding a Driver Details into the System..");
            System.out.println("If the driver is a Visitor, please specify the Phone Number. Otherwise, provide the University ID: ");
            String driverID = scanner.nextLine();
            addGivenDriver(connection, statement, driverID);
            
    		
    	} catch (Exception e) {
            System.out.println("Issue in addDriver Operation. Hardware/Inputs are malformed..");
        }
    }
    
    public static void addGivenDriver(Connection connection, Statement statement, String driverID) {
    	try {
        	String query = "INSERT INTO Driver (DriverID,Status,DriverName) VALUES (?, ?, ?)";
            scanner = new Scanner(System.in);
 
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
    	// Updating a parking lot.
    	try {
            scanner = new Scanner(System.in);
            
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
    	// Updating the space attributes
    	try {
            scanner = new Scanner(System.in);
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

    public static void updateDriverInfo(Connection connection, Statement statement) {
    	// Update a driver info.
    	try {
            scanner = new Scanner(System.in);
            
            System.out.println("You are updating a Driver Information..");
            System.out.println("Specify the Driver ID for the update: ");
            String driverID = scanner.nextLine();
            System.out.println("Specify what information you'd like to update for the given Driver ID - Choose Option: ");
            System.out.println("(A) Name of the Driver.");
            System.out.println("(B) Status of the Driver (Critical Operation).");
            String driverUpdateChoice = scanner.nextLine();
            System.out.println("Specify the desired value to update for Selected Option: ");
            String updatedValue = scanner.nextLine();
            String query;
            switch (driverUpdateChoice) {
                case "A":
                    query = updateDriverInfoNameQuery;
                    break;
                case "B":
                    query = updateDriverInfoStatusQuery;
                    break;
                default:
                    System.out.println("Choose appropriate options only. Try again..");
                    throw new IllegalArgumentException("Invalid Choice is Selected: " + driverUpdateChoice);
            }

            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setString(2, driverID);
                finalQuery.setString(1, updatedValue);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Driver Information is Updated Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in updateDriverInfo Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (IllegalArgumentException argumentError) {
            System.out.println(argumentError.getMessage());
            System.out.println("Invalid attribute given to update..Choose and Try Again!!!");
        } catch (Exception error) {
            System.out.println("Issue in updateDriverInfo Operation. Hardware/Inputs are malformed..");
        } 
    } 

    public static void deleteParkingLot(Connection connection, Statement statement) {
    	// Responsible for deleting the parking lot from the system.
    	try {
        	String query = deleteParkingLotQuery;
            scanner = new Scanner(System.in);
            
            System.out.println("You are deleting a Parking Lot..");
            System.out.println("Specify the Parking Lot ID to be deleted: ");
            String parkingLotID = scanner.nextLine();
            
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Parking Lot is Deleted Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in deleteParkingLot Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in deleteParkingLot Operation. Hardware/Inputs are malformed..");
        }
    }

    public static void deleteZone(Connection connection, Statement statement) {
    	// Responsible for deleting the Zone from the system.
    	try {
        	String query = deleteZoneQuery;
            scanner = new Scanner(System.in);
            
            System.out.println("You are deleting a Zone in the Parking Lot..");
            System.out.println("Specify the Parking Lot ID in which Zone to be deleted: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the Zone ID to be deleted: ");
            String zoneID = scanner.nextLine();
            
            try {
            	connection.setAutoCommit(false);
            	finalQuery = connection.prepareStatement(query);
                finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                finalQuery.setString(2, zoneID);
                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Zone is Deleted Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in deleteZone Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in deleteZone Operation. Hardware/Inputs are malformed..");
        }
    }

    public static void deleteSpace(Connection connection, Statement statement) {
    	// Responsible for deleting the Space from the system.
    	try {
        	String query;
            scanner = new Scanner(System.in);
            System.out.println("You are deleting a Space in the Zone of a Parking Lot..");
            System.out.println("Specify the Parking Lot ID in which Space is to be deleted: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Specify the associated Zone ID in which Space is to be deleted: ");
            String zoneID = scanner.nextLine();
            System.out.println("Specify what you'd like to update for the given Parking Lot and Zone - Choose Option: ");
            System.out.println("(A) Space Type in the Zone.");
            System.out.println("(B) Space Number in the Zone.");
            String spaceChoice = scanner.nextLine();
            String spaceType;
            
            try {
            	connection.setAutoCommit(false);

                switch (spaceChoice) {
                    case "A":
                        System.out.println("Specify the Space Type which you'd like to delete (Electric, Regular, Compact Car, Handicap): ");
                        spaceType = scanner.nextLine();

                        query = deleteSpaceTypeQuery;
                        finalQuery = connection.prepareStatement(query);
                        finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                        finalQuery.setString(2, zoneID);
                        finalQuery.setString(3, spaceType);
                        break;

                    case "B":
                        System.out.println("Specify the Space Type which you'd like to delete (Electric, Regular, Compact Car, Handicap): ");
                        spaceType = scanner.nextLine();
                        System.out.println("Specify the Space Number you'd like to delete: ");
                        String spaceNumber = scanner.nextLine();

                        query = deleteSpaceNumberQuery;
                        finalQuery = connection.prepareStatement(query);
                        finalQuery.setInt(1, Integer.parseInt(parkingLotID));
                        finalQuery.setString(2, zoneID);
                        finalQuery.setString(3, spaceType);
                        finalQuery.setInt(4, Integer.parseInt(spaceNumber));
                        break;

                    default:
                        System.out.println("Choose appropriate options only. Try again..");
                        throw new IllegalArgumentException("Invalid Choice is Selected: " + spaceChoice);
                }

                finalQuery.executeUpdate();
                connection.commit();
                System.out.println("Space is deleted Successfully!!");
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in deleteSpace Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (IllegalArgumentException argumentError) {
            System.out.println(argumentError.getMessage());
            System.out.println("Invalid attribute given to update..Choose and Try Again!!!");
        } catch (Exception error) {
            System.out.println("Issue in deleteSpace Operation. Hardware/Inputs are malformed..");
        } 
    }

    public static void deleteDriver(Connection connection, Statement statement) {
    	// Responsible for deleting the Driver from the system.
    	try {
            String permitID = null;
            scanner = new Scanner(System.in);
 
            System.out.println("You are deleting a Driver from the System..");
            System.out.println("Delete the Driver. If the driver is a Visitor, please specify the Phone Number. Otherwise, provide the University ID: ");
            String driverID = scanner.nextLine();
       
            try {
            	finalSelectPermitIDToDeleteQuery = connection.prepareStatement(selectPermitIDToDeleteQuery);
            	finalSelectPermitIDToDeleteQuery.setString(1, driverID);
                result = finalSelectPermitIDToDeleteQuery.executeQuery();
                
                if (result.next()) {
                	permitID = result.getString("PermitID");
                }
                

                System.out.println("Permit ID for the given Driver ID from the System is: " + permitID);
                
                connection.setAutoCommit(false);

                finalDeleteDriverQuery = connection.prepareStatement(deleteDriverQuery);
                finalDeleteDriverQuery.setString(1, driverID);
                finalDeleteDriverQuery.executeUpdate();
                System.out.println("Driver Details are deleted from Driver Successfully!!");
                finalDeletePermitQuery = connection.prepareStatement(deletePermitQuery);
                finalDeletePermitQuery.setString(1, permitID);
                finalDeletePermitQuery.executeUpdate();
                System.out.println("Driver Details are deleted from Permit Successfully!!");

                connection.commit();
                
            } catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in deleteDriver Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
    		
    	} catch (Exception e) {
            System.out.println("Issue in deleteDriver Operation. Hardware/Inputs are malformed..");
        }
    }

}


