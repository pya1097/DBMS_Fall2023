import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This file is responsible for displaying the data of respective tables of the system.
 */
public class DisplayOperation {
    public static void getNoOfCitations(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from ParkingLot;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String ParkingLotID = result.getObject(1).toString();
        		String NAME = result.getObject(2).toString();
        		String Address = result.getObject(3).toString();
                System.out.println("ParkingLotID: " + ParkingLotID + "; Name: " + NAME+"; Address: "+ Address);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    
    public static void getZone(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Zone;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String ParkingLotID = result.getObject(1).toString();
        		String Zone = result.getObject(2).toString();
                System.out.println("ParkingLotID: " + ParkingLotID + "; Zone : " + Zone);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    
    public static void getSpace(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Space;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String ParkingLotID = result.getObject(1).toString();
        		String Zone = result.getObject(2).toString();
        		String SpaceType = result.getObject(3).toString();
        		String SpaceNumber = result.getObject(4).toString();
        		String AvailabilityStatus = result.getObject(5).toString();
                System.out.println("ParkingLotID: " + ParkingLotID + "; Zone: " + Zone+ "; Space Type: "+SpaceType+ "; Space Number: "+ SpaceNumber+ "; AvailabilityStatus: "+ AvailabilityStatus);
        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getDriver(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Driver;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String DriverId = result.getObject(1).toString();
        		String DriverName = result.getObject(2).toString();
        		String Status = result.getObject(3).toString();
                System.out.println("DriverID: " + DriverId + "; DriverName: " + DriverName+ "; Status: " + Status);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getVehicle(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Vehicle;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String CarLicenseNumber = result.getObject(1).toString();
        		String Model = result.getObject(2).toString();
        		String Color = result.getObject(3).toString();
        		String Manufacturer = result.getObject(4).toString();
        		String Year = result.getObject(5).toString();

                System.out.println("CarLicenseNumber: " + CarLicenseNumber + "; Model: " + Model+ "; Color: " + Color+ "; Manufacturer: " + Manufacturer+ "; Year: " + Year);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getOwns(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Owns;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String CarLicenseNumber = result.getObject(1).toString();
        		String PhoneNumber = result.getObject(2).toString();
                System.out.println("CarLicenseNumber: " + CarLicenseNumber + "; PhoneNumber: " + PhoneNumber);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getPermit(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Permit;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String PermitID = result.getObject(1).toString();
        		String ParkingLotID = result.getObject(2).toString();
        		String ZoneID = result.getObject(3).toString();
        		String SpaceType = result.getObject(4).toString();
        		String StartDate = result.getObject(5).toString();
        		String ExpirationDate = result.getObject(6).toString();
        		String ExpirationTime = result.getObject(7).toString();
        		String PermitType = result.getObject(8).toString();

        		System.out.println("PermitID: " + PermitID + "; ParkingLotID: " + ParkingLotID+ "; ZoneID: " + ZoneID+ "; SpaceType: " + SpaceType+ "; StartDate: " + StartDate+ "; ExpirationDate: " + ExpirationDate+ "; ExpirationTime: " + ExpirationTime+ "; PermitType: " + PermitType);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getAssociated(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Associated;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String ParkingLotID = result.getObject(1).toString();
        		String Zone = result.getObject(2).toString();
        		String SpaceNumber = result.getObject(3).toString();
        		String SpaceType = result.getObject(4).toString();
        		String PermitID = result.getObject(5).toString();
        		
                System.out.println("ParkingLotID: " + ParkingLotID + "; Zone: " + Zone+ "; SpaceNumber: " + SpaceNumber+ "; SpaceType: " + SpaceType+ "; PermitID: " + PermitID);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getGiven(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Given;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String PermitId = result.getObject(1).toString();
        		String CarLicenseNumber = result.getObject(2).toString();
                System.out.println("PermitId: " + PermitId + " CarLicenseNumber: " + CarLicenseNumber);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getHolds(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Holds;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String PermitID = result.getObject(1).toString();
        		String PhoneNumber = result.getObject(2).toString();
                System.out.println("PermitID: " + PermitID + "; PhoneNumber: " + PhoneNumber);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getCitation(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Citation;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String CitationNumber = result.getObject(1).toString();
        		String ParkingLotID = result.getObject(2).toString();
        		String CitationDate = result.getObject(3).toString();
        		String CitationTime = result.getObject(4).toString();
        		String CategoryType = result.getObject(5).toString();
        		String AmountDue = result.getObject(6).toString();
        		String PaymentStatus = result.getObject(7).toString();

                System.out.println("CitationNumber: " + CitationNumber + "; ParkingLotID: " + ParkingLotID+ "; CitationDate: " + CitationDate+ "; CitationTime: " + CitationTime+ "; CategoryType: " + CategoryType+ "; AmountDue: " + AmountDue+ "; PaymentStatus: " + PaymentStatus);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getIssuedTo(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from IssuedTo;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String CitationNumber = result.getObject(1).toString();
        		String CarLicenseNumber = result.getObject(2).toString();
                System.out.println("CitationNumber: " + CitationNumber + "; CarLicenseNumber: " + CarLicenseNumber);

        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    public static void getAppeals(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Appeals;";
        	ResultSet result = statement.executeQuery(query);
        	while(result.next()) {
        		String CitationNumber = result.getObject(1).toString();
        		String PhoneNumber = result.getObject(2).toString();
        		String DriverRemark = result.getObject(3).toString();
        		String AdminRemark = result.getObject(4).toString();

                System.out.println("CitationNumber: " + CitationNumber + "; PhoneNumber: " + PhoneNumber+ "; DriverRemark: " + DriverRemark+ "; AdminRemark: " + AdminRemark);
        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    

}
