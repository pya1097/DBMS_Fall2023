import java.util.Scanner;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.sql.*;

public class PermitsVehiclesHelper {
	static Scanner scanner = new Scanner(System.in);
	static String  query;
	private static PreparedStatement issuePermitQuery;
	private static PreparedStatement countRegPermitQuery;
	private static PreparedStatement countSplPermitQuery;
	private static PreparedStatement updatePermitQuery;
	private static PreparedStatement addVehicleQuery;
	private static PreparedStatement updateVehicleQuery;
	private static PreparedStatement deleteVehicleQuery;
	private static PreparedStatement deletePermitQuery;
	private static PreparedStatement selectVehicleListQuery;
	private static PreparedStatement vehicleListQuery;
	private static PreparedStatement driverStatusQuery;
	private static PreparedStatement checkDriverQuery;
	private static PreparedStatement checkVehicleQuery;
	private static PreparedStatement checkPermitQuery;
	private static PreparedStatement addToOwnsQuery;
	private static PreparedStatement driverIdFromPermitQuery;
	private static PreparedStatement numOfVehiclesPerPermitQuery;
	private static PreparedStatement lastPermitIDQuery;
	private static PreparedStatement addToGivenQuery;
	private static PreparedStatement addToHoldsQuery;
	private static PreparedStatement carLicenseNumberFromPermitQuery;
	private static ResultSet result = null;
	
	public static final String updateVehicleModelQuery = "UPDATE Vehcile SET Model = ? WHERE CarLicenseNumber = ?;";
	public static final String updateVehicleColorQuery = "UPDATE Vehcile SET Color = ? WHERE CarLicenseNumber = ?;";
	public static final String updateVehicleManfQuery = "UPDATE Vehcile SET Manufacturer = ? WHERE CarLicenseNumber = ?;";
	public static final String updateVehicleYearQuery = "UPDATE Vehcile SET Year = ? WHERE CarLicenseNumber = ?;";
	public static final String updatePermitPLIDQuery = "UPDATE Permit SET ParkingLotID = ? WHERE PermitID = ?;";
	public static final String updatePermitZoneQuery = "UPDATE Permit SET ZoneID = ? WHERE PermitID = ?;";
	public static final String updatePermitSTQuery = "UPDATE Permit SET SpaceType = ? WHERE PermitID = ?;";
	public static final String updatePermitSDQuery = "UPDATE Permit SET StartDate = ? WHERE PermitID = ?;";
	public static final String updatePermitEDQuery = "UPDATE Permit SET ExpirationDate = ? WHERE PermitID = ?;";
	public static final String updatePermitETQuery = "UPDATE Permit SET ExpirationTime = ? WHERE PermitID = ?;";
	public static final String updatePermitPTQuery = "UPDATE Permit SET PermitType = ? WHERE PermitID = ?;";
	
	WolfParkingSystem wolfParkingSystem = new WolfParkingSystem();
	
	public static String getDriverStatus(Connection connection, String driverID) {
		query = "SELECT Status from Driver where DriverID = ?";
		try {
			driverStatusQuery = connection.prepareStatement(query);
		    driverStatusQuery.setString(1, driverID);
    	    result = driverStatusQuery.executeQuery();
	    	while(result.next()) {
	    		String driverStatus = result.getString("Status");
	    		return driverStatus;
	    	}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "Given driverID doesn't exist";
	}
	
	public static Integer getNumOfRegPermits(Connection connection, String driverID) {
		query = "select count(PermitID) as numOfPermits from Holds NATURAL JOIN  Permit where PermitType NOT IN (\"Special event\",\"ParkandRide\") and DriverID = ? GROUP BY DriverID";
		
		try {
			countRegPermitQuery = connection.prepareStatement(query);
			countRegPermitQuery.setString(1, driverID);
			result = countRegPermitQuery.executeQuery();
			
			while(result.next()) {
			 Integer numOfPermits = Integer.valueOf(result.getInt("numOfPermits"));
			 return numOfPermits;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Integer getNumOfSplPermits(Connection connection, String driverID) {
		query = "select count(PermitID) as numOfPermits from Holds NATURAL JOIN  Permit where PermitType IN (\"Special event\",\"ParkandRide\") and DriverID = ? GROUP BY DriverID";
		
		try {
			countSplPermitQuery = connection.prepareStatement(query);
			countSplPermitQuery.setString(1, driverID);
			result = countSplPermitQuery.executeQuery();
			
			while(result.next()) {
			 Integer numOfPermits = Integer.valueOf(result.getInt("numOfPermits"));
			 return numOfPermits;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Integer getNumOfVehiclesPerPermit(Connection connection, Integer permitID) {
		query = "select count(CarLicenseNumber) as numOfVehicles from Given where PermitID = ? ";
		
		try {
			numOfVehiclesPerPermitQuery = connection.prepareStatement(query);
			numOfVehiclesPerPermitQuery.setInt(1, permitID);
			result = numOfVehiclesPerPermitQuery.executeQuery();
			
			while(result.next()) {
			 Integer numOfVehicles = Integer.valueOf(result.getInt("numOfVehicles"));
			 return numOfVehicles;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static boolean checkDriver(Connection connection, String driverID) {
		query = "SELECT * from Driver where DriverID = ?";
		try {
			checkDriverQuery = connection.prepareStatement(query);
			checkDriverQuery.setString(1, driverID);
    	    result = checkDriverQuery.executeQuery();
	    	while(result.next()) {
	    		return true;
	    	}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
	}
	
	public static boolean checkPermit(Connection connection, Integer permitID) {
		query = "SELECT * from Permit where PermitID = ?";
		try {
			checkPermitQuery = connection.prepareStatement(query);
			checkPermitQuery.setInt(1, permitID);
    	    result = checkPermitQuery.executeQuery();
	    	while(result.next()) {
	    		return true;
	    	}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
	}
	
 	public static boolean checkVehicle(Connection connection, String carLicenseNumber) {
		query = "SELECT * from Vehicle where carLicenseNumber = ?";
		try {
			checkVehicleQuery = connection.prepareStatement(query);
			checkVehicleQuery.setString(1, carLicenseNumber);
    	    result = checkVehicleQuery.executeQuery();
	    	while(result.next()) {
	    		return true;
	    	}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
	}
	
	public static void addToOwns(Connection connection, String carLicenseNumber,String driverID) {
		
			query = "INSERT INTO Owns (CarLicenseNUmber, DriverID) VALUES (?,?);";
			try {
            	//connection.setAutoCommit(false);
            	addToOwnsQuery = connection.prepareStatement(query);
            	addToOwnsQuery.setString(1, carLicenseNumber);
            	addToOwnsQuery.setString(2, driverID);
            	addToOwnsQuery.executeUpdate();
                //connection.commit();
                //System.out.println("Added to Owns succesfully..");
            }catch(SQLException error) {
            	System.out.println(error.getMessage());
                System.out.println("Issue in addToOwns Operation. Hardware/Inputs are malformed..");
                //connection.rollback();
                System.out.println("Rollback Complete!");
            }finally {
            	//connection.setAutoCommit(true);
            }
		
	}
	
	public static void addToGiven(Connection connection, Integer permitID, String carLicenseNumber) {
		
			query = "INSERT INTO Given (PermitID,CarLicenseNUmber) VALUES (?,?);";
			try {
            	connection.setAutoCommit(false);
            	addToGivenQuery = connection.prepareStatement(query);
            	addToGivenQuery.setInt(1, permitID);
            	addToGivenQuery.setString(2, carLicenseNumber);
            	addToGivenQuery.executeUpdate();
                //connection.commit();
                //System.out.println("Added to Given succesfully..");
            }catch(SQLException error) {
            	System.out.println(error.getMessage());
                System.out.println("Issue in addToGiven Operation. Hardware/Inputs are malformed..");
                //connection.rollback();
                System.out.println("Rollback Complete!");
            }finally {
            	//connection.setAutoCommit(true);
            }
		
	}
	
	public static void addToHolds(Connection connection, Integer permitID, String driverID) {
		
			query = "INSERT INTO Holds (PermitID,driverID) VALUES (?,?);";
			try {
            	connection.setAutoCommit(false);
            	addToHoldsQuery = connection.prepareStatement(query);
            	addToHoldsQuery.setInt(1, permitID);
            	addToHoldsQuery.setString(2, driverID);
            	addToHoldsQuery.executeUpdate();
                //connection.commit();
                //System.out.println("Added to Holds succesfully..");
            }catch(SQLException error) {
            	System.out.println(error.getMessage());
                System.out.println("Issue in addToHolds Operation. Hardware/Inputs are malformed..");
                //connection.rollback();
                System.out.println("Rollback Complete!");
            }finally {
            	//connection.setAutoCommit(true);
            }
	}
	
	public static void issuePermit(Connection connection,Statement stmt) {
		 
		//call driver
		//call vehicle
		//update given
		//update holds
		//update owns
		try {
			connection.setAutoCommit(false);
			System.out.println("You are adding a Driver Details into the System..");
	        System.out.println("If the driver is a Visitor, please specify the Phone Number. Otherwise, provide the University ID: ");
			String driverID  = scanner.nextLine();
			
			
			
			if(!checkDriver(connection,driverID)) {
				// TODO should we keep it like this or ask them to add driver and come back to this??
				System.out.println("Given DriverId doesn't exist in the system. Add Driver details to the system");
				InformationProcessingOperations.addDriver(connection,stmt);
			}
			String driverStatus = getDriverStatus(connection,driverID);
			Integer numOfRegPermits = getNumOfRegPermits(connection,driverID);
			Integer numOfSplPermits = getNumOfSplPermits(connection,driverID);
			Integer totalPermits = numOfRegPermits + numOfSplPermits;
			
			if(driverStatus.equals("V") && totalPermits == 1) {
				System.out.println("Visitors can only have 1 permit and it already exists");
				return;
			}
			
			System.out.println("Enter PermitType");
			String permitType = scanner.nextLine();
			
			//System.out.println(driverStatus);
			//System.out.println(numOfSplPermits);
			
			if(permitType.equals("Special event")|| permitType.equals("ParkandRide")) {
				
				if(driverStatus.equals("S") || driverStatus.equals("E")) {
					/*if(numOfRegPermits == 0) {
						System.out.println("Students|Employee should have regular permit before requesting for special event permit");
						return;
					}*/
					if(numOfSplPermits == 1) {
						System.out.println("Students|Employee can only have 1 permit of this permit type, and it already exists");
						return;
					}
				}
			}else {
				//System.out.println(numOfRegPermits);
				if(driverStatus.equals("S") || driverStatus.equals("V")) {
					//System.out.println(numOfRegPermits);
					if(numOfRegPermits == 1) {
						System.out.println("Students|Visitors can only have 1 permit, and it already exists");
						return;
					}
				} 
				if(driverStatus.equals("E")) {
					if(numOfRegPermits == 2) {
						System.out.println("Employees can only have 2 permits, and 2 permits already exists");
						return;
					}
				}
			}
			
			System.out.println("Enter Car License Number");
			String carLicenseNumber  = scanner.nextLine();
			if(!checkVehicle(connection,carLicenseNumber)) {
				// TODO should we keep it like this or ask them to add driver and come back to this??
				System.out.println("Given Vehicle doesn't exist in the system. Add Vehicle details to the system");
				addGivenVehicle(connection,carLicenseNumber);
			}
			
			//adding vehicle driver entry to owns
			addToOwns(connection, carLicenseNumber,driverID);
			
			
			//check if that vehicle is added to an existing permit
			//remove vehicle from permit?? should we add
			//get spacenumber ??
			//associated ??
			
			System.out.println("Enter ParkingLotID:");
			Integer parkingLotID = Integer.valueOf(scanner.nextLine());
			System.out.println("Enter ZoneID:");
			String zoneID = scanner.nextLine();
			System.out.println("Enter SpaceType:");
			String spaceType = scanner.nextLine();
			System.out.println("Enter StartDate:");
			String startDate = scanner.nextLine();
			System.out.println("Enter ExpirationDate:");
			String expirationDate = scanner.nextLine();
			System.out.println("Enter ExpirationTime:");
			String expirationTime = scanner.nextLine();
			try {
				query = "INSERT INTO Permit (ParkingLotID,ZoneID,SpaceType,StartDate,ExpirationDate,ExpirationTime,PermitType) VALUES (?, ?, ?, ?, ?, ?,?);";
				try {
					
					issuePermitQuery =connection.prepareStatement(query);
					
					issuePermitQuery.setInt(1,parkingLotID);
					issuePermitQuery.setString(2,zoneID);
					issuePermitQuery.setString(3,spaceType);
					issuePermitQuery.setString(4,startDate);
					issuePermitQuery.setString(5,expirationDate);
					issuePermitQuery.setString(6,expirationTime);
					issuePermitQuery.setString(7,permitType);
					
					issuePermitQuery.executeUpdate();
		            
		            try {
		            	Integer permitID;
		    			query = "SELECT LAST_INSERT_ID()";
		    			try {
		    				lastPermitIDQuery = connection.prepareStatement(query);
		    				result = lastPermitIDQuery.executeQuery();
		    				
		    				while(result.next()) {
		    				 permitID = Integer.valueOf(result.getInt(1));
		    				 addToGiven(connection,permitID,carLicenseNumber);
			    			 addToHolds(connection,permitID,driverID);
		    				}
		    				
		    			} catch (SQLException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    		}catch(Exception e) {
		    			System.out.println("Issue in getting last permit id");
		    		}
		            
		            
		        } catch (SQLException error) {
		        	System.out.println(error.getMessage());
		            System.out.println("Issue in issuePermit Operation. Hardware/Inputs are malformed..");
		            connection.rollback();
		            System.out.println("Rollback Complete!");
		        }
			}catch(Exception e) {
				System.out.println("Issue in issuePermit Operation. Hardware/Inputs are malformed..");
			}
			connection.commit();
			System.out.println("Added to Owns succesfully..");
			System.out.println("Added to Given succesfully..");
			System.out.println("Added to Holds succesfully..");
			System.out.println("Issue Permit is succesfull..");
		}catch(Exception e) {
			System.out.println("Issue in issuePermit Operation. Hardware/Inputs are malformed..");
		} finally {
        	try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}
	
	public static void updatePermit(Connection connection,Statement stmt) {
		try {
			String value;
	        String permitID;
	        String option;
	        scanner = new Scanner(System.in);
	        System.out.println("Enter permitID of the permit:");
	        permitID = scanner.nextLine();
	        System.out.println("1. Update Parking Lot ID:");
	        System.out.println("2. Update Zone:");
	        System.out.println("3. Update Space Type:");
	        System.out.println("4. Update Start Date:");
	        System.out.println("5. Update Expiration Date:");
	        System.out.println("6. Update Expiration Time:");
	        System.out.println("7. Update Permit Type:");
	        option = scanner.nextLine();
	        System.out.println("Enter the new value:");
	        value = scanner.nextLine();

	        switch (option) {
            case "1":
                query = updatePermitPLIDQuery;
                break;
            case "2":
                query = updatePermitZoneQuery;
                break;
            case "3":
            	query = updatePermitSTQuery;
            	break;
            case "4":
            	query = updatePermitSDQuery;
            	break;
            case "5":
            	query = updatePermitEDQuery;
            	break;
            case "6":
            	query = updatePermitETQuery;
            	break;
            case "7":
            	query = updatePermitPTQuery;
            	break;
            default:
                System.out.println("Choose appropriate options only. Try again..");
                throw new IllegalArgumentException("Invalid Choice is Selected: " + option);
        }
	        try {
	        	connection.setAutoCommit(false);
	        	updatePermitQuery = connection.prepareStatement(query);
	        	//System.out.println(query);
	        	updatePermitQuery.setInt(2, Integer.parseInt(permitID));
	        	updatePermitQuery.setString(1, value);
	        	updatePermitQuery.executeUpdate();
                connection.commit();
                System.out.println("Permit is Updated Successfully!!");
	        }catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in updatePermit Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
			
		}catch (IllegalArgumentException argumentError) {
            System.out.println(argumentError.getMessage());
            System.out.println("Invalid attribute given to update..Choose and Try Again!!!");
        } catch (Exception error) {
            System.out.println("Issue in updatePermit Operation. Hardware/Inputs are malformed..");
        } 
	}
	
	public static void deletePermit(Connection connection,Statement stmt) {
		
		try {
			System.out.println("You are deleting a permit from the System..");
            System.out.println("Please provide the permit id  you want to delete: ");
            String permitID = scanner.nextLine();
            
            finalDeletePermit(connection,permitID);
		}catch(Exception e) {
            System.out.println("Issue in deleteVehicle Operation. Hardware/Inputs are malformed..");
		}
	}
	
	public static void finalDeletePermit(Connection connection, String permitID) {
		try {
			try {
				String vehicleQuery = "DELETE FROM Vehicle WHERE CarLicenseNumber = ?";
				String carLicenseNumber;
	        	connection.setAutoCommit(false);
	        	query = "SELECT CarLicenseNumber from Given WHERE PermitID = ? ";
	        	selectVehicleListQuery = connection.prepareStatement(query);
	        	selectVehicleListQuery.setInt(1, Integer.parseInt(permitID));
	        	result = selectVehicleListQuery.executeQuery();
	        	while(result.next()) {
	        		carLicenseNumber = result.getString("CarLicenseNumber");
	        		vehicleListQuery = connection.prepareStatement(vehicleQuery);
	        		vehicleListQuery.setString(1, carLicenseNumber);
	        		vehicleListQuery.executeUpdate();
	        	}
	        	
	        	query = "DELETE FROM Permit WHERE PermitID = ? ";
	        	deletePermitQuery = connection.prepareStatement(query);
	        	deletePermitQuery.setInt(1, Integer.parseInt(permitID));
	        	deletePermitQuery.executeUpdate();
	            connection.commit();
	            System.out.println("Permit is Deleted Successfully!!");
	        }catch(SQLException error) {
	        	System.out.println(error.getMessage());
	            System.out.println("Issue in deletePermit Operation. Hardware/Inputs are malformed..");
	            connection.rollback();
	            System.out.println("Rollback Complete!");
	        }finally {
	        	connection.setAutoCommit(true);
	        }
		}catch(Exception e) {
            System.out.println("Issue in deletePermit Operation. Hardware/Inputs are malformed..");
		}
	}
	
	public static void addVehicle(Connection connection,Statement stmt) {
		
		try {
			
            scanner = new Scanner(System.in);
			System.out.println("You are adding a Vehicle.");
            System.out.println("Enter Car License Number ");
            String carLicenseNumber = scanner.nextLine();
            addGivenVehicle(connection,carLicenseNumber);
            
			} catch(Exception e) {
		            System.out.println("Issue in addVehicle Operation. Hardware/Inputs are malformed..");
		    }
		
	}
	
	public static void addGivenVehicle(Connection connection,String carLicenseNumber) {
		try {
			query = "INSERT INTO Vehicle (CarLicenseNumber, Model, Color, Manufacturer, Year) VALUES (?, ?,?,?,?)";
            scanner = new Scanner(System.in);
			
            System.out.println("Enter Model of the car ");
            String model = scanner.nextLine();
            System.out.println("Enter Color of the car ");
            String color = scanner.nextLine();
            System.out.println("Enter Manufacturer of the car ");
            String manufacturer = scanner.nextLine();
            System.out.println("Enter Year of the model ");
            String year = scanner.nextLine();
            
            try {
            	connection.setAutoCommit(false);
            	addVehicleQuery = connection.prepareStatement(query);
            	addVehicleQuery.setString(1, carLicenseNumber);
            	addVehicleQuery.setString(2, model);
            	addVehicleQuery.setString(3, color);
            	addVehicleQuery.setString(4, manufacturer);
            	addVehicleQuery.setString(5, year);
            	addVehicleQuery.executeUpdate();
                connection.commit();
                System.out.println("Vehicle is Added Successfully!!");
            }catch(SQLException error) {
            	System.out.println(error.getMessage());
                System.out.println("Issue in addVehicle Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            }finally {
            	connection.setAutoCommit(true);
            }
            
			} catch(Exception e) {
		            System.out.println("Issue in addVehicle Operation. Hardware/Inputs are malformed..");
		    }
	}
	
	
	public static void updateVehicle(Connection connection,Statement stmt) {
		try {
			String value;
	        String carLicenseNumber;
	        String option;
	        scanner = new Scanner(System.in);
	        System.out.println("Enter Car License Number of the vehicle");
	        carLicenseNumber = scanner.nextLine();
	        System.out.println("1. Update Model");
	        System.out.println("2. Update Color");
	        System.out.println("3. Update Manufacturer");
	        System.out.println("4. Update Year");
	        option = scanner.nextLine();
	        System.out.println("Enter the new value");
	        value = scanner.nextLine();

	        switch (option) {
            case "1":
                query = updateVehicleModelQuery;
                break;
            case "2":
                query = updateVehicleColorQuery;
                break;
            case "3":
            	query = updateVehicleManfQuery;
            	break;
            case "4":
            	query = updateVehicleYearQuery;
            	break;
            default:
                System.out.println("Choose appropriate options only. Try again..");
                throw new IllegalArgumentException("Invalid Choice is Selected: " + option);
        }
	        try {
	        	connection.setAutoCommit(false);
	        	updateVehicleQuery = connection.prepareStatement(query);
	        	updateVehicleQuery.setString(2, carLicenseNumber);
	        	updateVehicleQuery.setString(1, value);
	        	updateVehicleQuery.executeUpdate();
                connection.commit();
                System.out.println("Vehicle is Updated Successfully!!");
	        }catch (SQLException error) {
                System.out.println(error.getMessage());
                System.out.println("Issue in updateVehicle Operation. Hardware/Inputs are malformed..");
                connection.rollback();
                System.out.println("Rollback Complete!");
            } finally {
                connection.setAutoCommit(true);
            }
			
		}catch (IllegalArgumentException argumentError) {
            System.out.println(argumentError.getMessage());
            System.out.println("Invalid attribute given to update..Choose and Try Again!!!");
        } catch (Exception error) {
            System.out.println("Issue in updateVehicle Operation. Hardware/Inputs are malformed..");
        } 
	}
	
	public static void deleteVehicle(Connection connection,Statement stmt) {
		
		//TODO check hold, driver and permit tables for deletion
		try {
			
            scanner = new Scanner(System.in);
            System.out.println("You are deleting a vehicle from the System..");
            System.out.println("Please provide the Car License Number of the vehicle you want to delete: ");
            String carLicenseNumber = scanner.nextLine();
            
            finalDeleteVehicle(connection,carLicenseNumber);
            
		}catch(Exception e) {
            System.out.println("Issue in deleteVehicle Operation. Hardware/Inputs are malformed..");
		}
	}
	public static void finalDeleteVehicle(Connection connection, String carLicenseNumber) {
		try {
			try {
				query = "DELETE FROM Vehicle WHERE CarLicenseNumber = ? ";
	        	connection.setAutoCommit(false);
	        	deleteVehicleQuery = connection.prepareStatement(query);
	        	deleteVehicleQuery.setString(1, carLicenseNumber);
	        	deleteVehicleQuery.executeUpdate();
	            connection.commit();
	            System.out.println("Vehicle is Deleted Successfully!!");
	        }catch(SQLException error) {
	        	System.out.println(error.getMessage());
	            System.out.println("Issue in deleteVehicle Operation. Hardware/Inputs are malformed..");
	            connection.rollback();
	            System.out.println("Rollback Complete!");
	        }finally {
	        	connection.setAutoCommit(true);
	        }
		}catch(Exception e) {
            System.out.println("Issue in deleteVehicle Operation. Hardware/Inputs are malformed..");
		}
		
		
	}
	
	public static String getDriverIdFromPermit(Connection connection, Integer permitID ) {
		query = "SELECT DriverID from Holds where PermitID = ?";
		try {
			driverIdFromPermitQuery = connection.prepareStatement(query);
			driverIdFromPermitQuery.setInt(1, permitID);
    	    result = driverIdFromPermitQuery.executeQuery();
	    	while(result.next()) {
	    		String driverID = result.getString("DriverID");
	    		return driverID;
	    	}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return " ";
	}
	
	public static String getCarLicenseNumberFromPermit(Connection connection, Integer permitID) {
		query = "SELECT CarLicenseNumber from Given where PermitID = ?";
		try {
			carLicenseNumberFromPermitQuery = connection.prepareStatement(query);
			carLicenseNumberFromPermitQuery.setInt(1, permitID);
    	    result = carLicenseNumberFromPermitQuery.executeQuery();
	    	while(result.next()) {
	    		String carLicenseNumber = result.getString("CarLicenseNumber");
	    		return carLicenseNumber;
	    	}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return " ";
	}
	
	public static void addVehcileToExistingPermit(Connection connection,Statement stmt) {
		try {
			scanner = new Scanner(System.in);
            System.out.println("Please provide the PermitID for which you want to add vehicle: ");
            String permitID = scanner.nextLine();
            if(!checkPermit(connection, Integer.parseInt( permitID))) {
            	System.out.println("Given PermitID doesnt exist. please enter the correct permitID");
            	return;
            }
            
            String driverID = getDriverIdFromPermit(connection, Integer.parseInt(permitID));
            
            String driverStatus = getDriverStatus(connection, driverID);
            /*
            if(driverID == " ") {
            	System.out.println("No driver associated with this permit, please check input again");
            	return;
            } else {
            	driverStatus = getDriverStatus(connection, driverID);
            } */
            
            if(driverStatus.equals("S") || driverStatus.equals("V")) {
            	System.out.println("Only Employees can have 2 vehicles for the permit");
            	return;
            } else {
            	Integer numOfVehicles = getNumOfVehiclesPerPermit(connection, Integer.parseInt(permitID));
            	if(numOfVehicles == 2) {
            		System.out.println("Employees can have 2 vehicles for the permit and 2 vehicles already exists");
                	return;
            	} else {
            		System.out.println("Enter Car License Number ");
                    String carLicenseNumber = scanner.nextLine();
            		addGivenVehicle(connection,carLicenseNumber);
            		addToGiven(connection,Integer.parseInt(permitID),carLicenseNumber);
            	}
            }
            
		}catch(Exception e) {
            System.out.println("Issue in addVehcileToExistingPermit Operation. Hardware/Inputs are malformed..");
		}
	}
}

