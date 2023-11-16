import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
 
public class ReportOperations {
	private static PreparedStatement finalQuery = null;
    static Scanner scanner = new Scanner(System.in);

    //Case1:
    public static void getNoOfCitations(Connection connection, Statement statement) {
    	try {
            System.out.println("You are generating a report for Citations a Driver Information..");
    		System.out.println("\nHow would you like to retrieve number of Citation Details ?");
    		System.out.println("(1) Display Number of Citations Per Parking Lot.");
    		System.out.println("(2) Display Number of Citations Per Month.");
    		System.out.println("(3) Display Number of Citations Per Year.");
            System.out.println("(4) Display Number of Citations for a Given Parking Lot Id.");
            System.out.println("(5) Display Number of Citations for a Given Month.");
            System.out.println("(6) Display Number of Citations for a Given Year.");
            System.out.println("(7) Display Number of Citations Per Month for a Particular Parking Lot Id.");
            System.out.println("Provide your choice: ");
    		
			String choice = scanner.nextLine();
    		ResultSet result =null;
    		switch(choice) {
    		case "1":
    			result = statement.executeQuery("SELECT ParkingLotId, count(*) as NumberOfCitations from Citation group by ParkingLotId;");
    			if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Parking Lot Id:"+ result.getString("ParkingLotId") + "\n Number of Citations: "+result.getString("NumberOfCitations"));
    				}
    			}
    			break;
    		case "2":
    			result = statement.executeQuery("SELECT MONTH(CitationDate) as MonthNumber, COUNT(*) as NumberOfCitations FROM Citation GROUP BY MONTH(CitationDate);");
    			if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Month:"+ result.getString("MonthNumber") + "\n Number of Citations:"+result.getString("NumberOfCitations"));
    				}
    			}
    			break;
    		case "3":
    			result = statement.executeQuery("SELECT YEAR(CitationDate) AS YearNumber, COUNT(*) AS NumberOfCitations FROM Citation GROUP BY YEAR(CitationDate);");
    			if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Year:"+ result.getString("YearNumber") + "\n Number of Citations:"+result.getString("NumberOfCitations"));
    				}
    			}
    			break;
            case "4":
                System.out.println("\nProvide the Parking Lot ID for which you need the Number of Citations:");  
                String parkingLotID = scanner.nextLine();
                result = statement.executeQuery("SELECT ParkingLotId, count(*) as NumberOfCitations FROM Citation WHERE ParkingLotID =" + parkingLotID+";");
                if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Parking Lot ID:"+ parkingLotID + "\n Number of Citations:"+result.getString("NumberOfCitations"));
    				}
    			}
                break;
            case "5":
                System.out.println("\nProvide the Month Number for which you need the Number of Citations:"); 
                String monthNumber = scanner.nextLine();
                result = statement.executeQuery("SELECT MONTH(CitationDate) as MonthNumber, COUNT(*) as NumberOfCitations FROM Citation WHERE MONTH(CitationDate) =" +monthNumber+";");
                if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Month Number:"+ monthNumber + "\n Number of Citations:"+result.getString("NumberOfCitations"));
    				}
    			}
                break;
            case "6":
                System.out.println("\nProvide the Year for which you need the Number of Citations:"); 
                String year = scanner.nextLine();
                result = statement.executeQuery("SELECT YEAR(CitationDate) as Year, COUNT(*) as NumberOfCitations FROM Citation WHERE YEAR(CitationDate) =" +year+";");
                if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Year:"+ year + "\n Number of Citations:"+result.getString("NumberOfCitations"));
    				}
    			}
                break;
    		case "7":
    			System.out.println("\nProvide a Parking Lot ID For Which You Want Monthly Number of Citations:");
    			int parkingLotId = scanner.nextInt();
    			result = statement.executeQuery("SELECT MONTH(CitationDate) AS MonthNumber, COUNT(*) AS NumberOfCitations FROM Citation WHERE ParkingLotID=" + parkingLotId + " GROUP BY MONTH(CitationDate);");
    			if(!result.isBeforeFirst()) {
                    System.out.println("No Citation Data is Available!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Month:"+ result.getString("MonthNumber") + "\n Number of Citations:"+result.getString("NumberOfCitations"));
    				}
    			}
    			break;
    		}
    	} catch (SQLException e) {
            System.out.println("Issue in getNoOfCitations() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }

    //Case2:
    public static void getNoOfViolatingVehicles(Connection connection, Statement statement) {
    	try {
            System.out.println("You are generating a report for number of vehicles that are currently viol..");
        	String query = "SELECT count(distinct(CarLicenseNumber)) as NumberOfViolatingVehicles from IssuedTo i INNER JOIN Citation c on i.CitationNumber = c.CitationNumber WHERE c.PaymentStatus='Due';";
        	ResultSet result = statement.executeQuery(query);
        	if(!result.isBeforeFirst()) {
                System.out.println("No violating vehicles present!");
        	} else{
        		while(result.next()) {
    			System.out.println("The number of currently violating vehicles are: "+result.getString("NumberOfViolatingVehicles"));
        		}
        	}
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfViolatingVehicles() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    
    //Case3:
    public static void getParkingLotAndZones(Connection connection, Statement statement) {
    	try {
        	String query = "SELECT * from Zone order by ParkingLotID;";
        	ResultSet result = statement.executeQuery(query);
        	if(!result.isBeforeFirst()) {
                System.out.println("No data present!");
        	}else{
        		while(result.next()) {
        			System.out.println("(Parking Lot Id: "+result.getString("ParkingLotID")+", Zone Id: "+result.getString("ZoneID") + ")");
        		}
        	} 
    	} catch (Exception e) {
            System.out.println("Issue in getParkingLotAndZones() Operation. Hardware/Inputs are malformed..");
        }
    }
 
    //Case4:
    public static void getEmployeesWithPermits(Connection connection, Statement statement) {
    	try {
    		System.out.println("\nHow Would You Like to Retrieve Number of Employees having Permits for a Given Parking Zone?");
    		System.out.println("(1) Display Number of Employees having Permits for a given Parking Lot.");
    		System.out.println("(2) Display Number of Employees having Permits for a given Zone in the associated Parking Lot.");
            System.out.println("\nProvide your choice: ");
            String choice = scanner.nextLine();
            String parkingLotId;
     		switch(choice) {
    		case "1":
        		System.out.println("Enter Parking Lot ID: ");
                parkingLotId = scanner.nextLine();
        		ResultSet result = statement.executeQuery("select p.ParkingLotID, count(distinct(d.DriverID)) as numEmployee from Permit p, Holds h, Driver d where d.DriverID = h.DriverID and h.PermitID = p.PermitID and p.ParkingLotID=" + parkingLotId + " and d.Status='E';");
    			if(!result.isBeforeFirst()) {
                    System.out.println("No permit data present!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Parking Lot Id: "+ parkingLotId + "\n Number of employees: "+result.getString("numEmployee"));
    				}
    			}
    			break;
    		case "2":
    			System.out.println("Enter Parking Lot ID: ");
        		parkingLotId = scanner.nextLine();
        		System.out.println("Enter Zone ID: ");
                String zoneId = scanner.nextLine();
        		result = statement.executeQuery("select p.ParkingLotID, p.ZoneId, count(distinct(d.DriverID)) as numEmployee from Permit p, Holds h, Driver d where d.DriverID = h.DriverID and h.PermitID=p.PermitID and p.ParkingLotID=" + parkingLotId +" and p.ZoneID='" + zoneId + "' and d.Status='E';");
    			if(!result.isBeforeFirst()) {
                    System.out.println("No permit data present!");
    			} else {
    				while(result.next()) {
    					System.out.println("\n Parking Lot ID: "+ parkingLotId + "\n Zone ID: "+ zoneId +"\n Number of Employees: "+result.getString("numEmployee"));
    				}
    			}
    			break;
    		 
    		}
    		
    	} catch (Exception e) {
            System.out.println("Issue in getEmployeesWithPermits() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }

    //Case5:
    public static void getPermitInformation(Connection connection, Statement statement) {
    	try {           
            System.out.println("You are retrieving Permit Information..");
    		ResultSet result =null;
            
            System.out.println("\nIf the driver is a Visitor, please specify the Phone Number. Otherwise, specify the University ID: ");
            String DriverId = scanner.nextLine();
            result = statement.executeQuery("select p.PermitID, p.ParkingLotID, p.ZoneId, p.SpaceType, p.StartDate, p.ExpirationDate, p.ExpirationTime, p.PermitType, d.DriverName as Name from Permit p, Driver d, Holds h where p.PermitID=h.PermitID and h.DriverID=d.DriverID and d.DriverID='"+DriverId+"';");
            if(!result.isBeforeFirst()) {
                System.out.println("No Permit Information Matches!");
            } else {
                while(result.next()) {
                    System.out.println("\n Driver Name: "+ result.getString("Name") + "\n Permit ID:"+ result.getString("PermitID") + "\n Parking Lot ID: "+result.getString("ParkingLotID")+"\n Zone ID: "+result.getString("ZoneId")+"\n Space Type: "+result.getString("SpaceType")+"\n StartDate: "+result.getString("StartDate")+"\n Expiration Date: "+result.getString("ExpirationDate")+"\n PermitType: "+result.getString("PermitType"));
                }
    		}
    		
    	} catch (Exception e) {
            System.out.println("Issue in getPermitInformation() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }

    
    //Case6:
    public static void getAvailableSpaceNumber(Connection connection, Statement statement) {
    	try {           
            System.out.println("You are Retrieving an available space for a Parking Lot, Zone and Space..");
            System.out.println("Enter Parking Lot ID: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Enter Parking Zone ID: ");
            String parkingZoneID = scanner.nextLine();
            System.out.println("Enter Space Type: ");
            String spaceType = scanner.nextLine();
       
            ResultSet result = statement.executeQuery("select SpaceNumber from Space where AvailabilityStatus = 'Y' and ParkingLotID ="+ parkingLotID+" and ZoneID = '"+parkingZoneID+"' and SpaceType ='"+spaceType+"'  limit 1;");
            if(!result.isBeforeFirst()) {
                System.out.println("No Space Available!!!");
			} else {
				while(result.next()) {
	            	System.out.println("One of the Available Space Number For the Given Requirements: "+ result.getString("SpaceNumber"));
	            }
			}  
    	} catch (Exception e) {
            System.out.println("Issue in getAvailableSpaceNumber() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }

    //Case7:
    public static void getNoOfAvailableSpaces(Connection connection, Statement statement) {
    	try {           
            System.out.println("You are Retrieving the Number of Available Spaces in a Given Parking Lot..");
            System.out.println("Enter Parking Lot ID: ");
            String parkingLotID = scanner.nextLine();
            System.out.println("Enter Parking Zone ID: ");
            String parkingZoneID = scanner.nextLine();
            System.out.println("Enter Space Type: ");
            String spaceType = scanner.nextLine();
       
            ResultSet result = statement.executeQuery("select count(*) as NoOfSpaces from Space where AvailabilityStatus = 'Y' and ParkingLotID ="+ parkingLotID+" and ZoneID = '"+parkingZoneID+"' and SpaceType = '"+spaceType+"';");
            if(!result.isBeforeFirst()) {
                System.out.println("Give Appropriate Inputs!");
			} else {
				while(result.next()) {
	            	System.out.println("Number of Available Spaces: "+ result.getString("NoOfSpaces"));
	            }
			}	
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfAvailableSpaces() Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }
    
    //Case8
    public static void getNoOfExpiredPermits(Connection connection, Statement statement) {
    	try {           
    		ResultSet result = statement.executeQuery("select count(*) as NoOfExpiredPermits from Permit where ExpirationDate < CURRENT_DATE();");
            if(!result.isBeforeFirst()) {
                System.out.println("No Output Fetched..");
			}else {
				while(result.next()) {
	            	System.out.println("Number of expired permits: "+ result.getString("NoOfExpiredPermits"));
	            }
			}
            
    	} catch (Exception e) {
            System.out.println("Issue in getNoOfExpiredPermits Operation. Hardware/Inputs are malformed..");
            e.printStackTrace();
        }
    }    
}
