import java.util.Scanner;
import java.sql.*;

public class MenuOfOperations {
    String line = "----------";
    String menuMessage = "Choose Operations for";
    Scanner scanner = new Scanner(System.in);

    public void displayInformationProcessingOperations(Connection connection, Statement statement) {
        while (true) {
        	System.out.println("\n\n" + line + menuMessage + " Information Processing " + line);
            System.out.println("(0)  Return to Main Menu.");
            System.out.println("(1)  Add a Parking Lot.");
            System.out.println("(2)  Add a Zone.");
            System.out.println("(3)  Add a Space.");
            System.out.println("(4)  Add a Driver.");
            System.out.println("(5)  Update a Parking Lot.");
            System.out.println("(6)  Update a Space.");
            System.out.println("(7)  Update a Driver.");
            System.out.println("(8)  Delete a Parking Lot.");
            System.out.println("(9) Delete a Zone.");
            System.out.println("(10) Delete a Space.");
            System.out.println("(11) Delete a Driver.");
            System.out.print("\n\n Select your choice: \t");
            
            int choiceNumber = scanner.nextInt();
            scanner.nextLine();

            switch(choiceNumber) {
                case 0:
                    System.out.println("\nGoing Back to the Main Menu..\n");
                    return;
                case 1:
                    InformationProcessingOperations.addParkingLot(connection, statement); 
                    break;
                case 2:
                    InformationProcessingOperations.addZone(connection, statement);
                    break;
                case 3:
                	InformationProcessingOperations.addSpace(connection, statement);
                    break;
                case 4:
                    InformationProcessingOperations.addDriver(connection, statement);
                    break;
                case 5:
                    InformationProcessingOperations.updateParkingLot(connection, statement);
                    break;
                case 6:
                    InformationProcessingOperations.updateSpace(connection, statement);
                    break;
                case 7:
                    InformationProcessingOperations.updateDriverInfo(connection, statement);
                    break;
                case 8:
                    InformationProcessingOperations.deleteParkingLot(connection, statement);                    
                    break;
                case 9:
                    InformationProcessingOperations.deleteZone(connection, statement);
                    break;
                case 10:
                	InformationProcessingOperations.deleteSpace(connection, statement);
                    break;
                case 11:
                    InformationProcessingOperations.deleteDriver(connection, statement);
                    break;
                default:
                    System.out.println("\nBroken. Choose the Choices from the Available Options only. Try again...\n");
                    break;
            }

        }

    }

    public void displayMaintanenceOperationsOfPermitsAndVehicles(Connection connection, Statement stmt) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n" + line + menuMessage + " Maintaining Permits and Vehicle Information of Drivers " + line);
            System.out.println("(0) Return to Main Menu.");
            System.out.println("(1) Issue Permit.");
            System.out.println("(2) Update Permit Information.");
            System.out.println("(3) Delete Permit Information.");
            System.out.println("(4) Add Vehicle Information.");
            System.out.println("(5) Update Vehicle Information.");
            System.out.println("(6) Delete Vehicle Information.");
            System.out.println("(7) Adding Vehicle to Existing Permit.");
            System.out.print("\n Select your choice: ");

            String choiceNumber = scanner.nextLine().strip();

            switch(choiceNumber) {
            
            case "0":
                System.out.println("\nGoing Back to the Main Menu..\n");
                return;
            case "1":
                PermitsVehiclesHelper.issuePermit(connection, stmt);
            	break;
            case "2":
            	PermitsVehiclesHelper.updatePermit(connection, stmt);
            	break;
            case "3":
            	PermitsVehiclesHelper.deletePermit(connection, stmt);
                break;
            case "4":
            	PermitsVehiclesHelper.addVehicle(connection,stmt);
                break;
            case "5":
            	PermitsVehiclesHelper.updateVehicle(connection,stmt);
                break;
            case "6":
            	PermitsVehiclesHelper.deleteVehicle(connection,stmt);
                break;
            case "7":
            	PermitsVehiclesHelper.addVehcileToExistingPermit(connection,stmt);
                break;
            default:
                System.out.println("\nBroken. Choose the Choices from the Available Options only. Try again...\n");
                break;
            }
        }   
    }

    public void displayCitationOperations(Connection connection,Statement statement) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n" + line + menuMessage + " Generating and Maintaining Citations " + line);
            System.out.println("(0) Return to Main Menu.");
            System.out.println("(1) Add Citation Information.");
            System.out.println("(2) Adding an Appeal to a Citation by a Driver.");
            System.out.println("(3) Update a Citation Appeal by Admin.");
            System.out.println("(4) Update Payment Information of a Citation.");
            System.out.println("(5) Retrieve Citation Information.");
            System.out.println("(6) Check Validity of Current Parked Vehicle.");
            System.out.println("(7) Update Citation Information.");
            System.out.println("(8) Calculate Fine Amount.");
            System.out.print("\n\nSelect your choice: ");

            int choiceNumber = scanner.nextInt();

            switch(choiceNumber) {
                case 0:
                    System.out.println("\nGoing Back to the Main Menu..\n");
                    return;
                case 1:
                    CitationHelper.addCitation(connection,statement);
                    break;
                case 2:
                    CitationHelper.appealCitationByDriver(connection,statement);
                    break;
                case 3:
                    CitationHelper.updateCitationAppealByAdmin(connection,statement);
                    break;
                case 4:
                    CitationHelper.updateCitationPaymentInfo(connection,statement);
                    break;
                case 5:
                    CitationHelper.retrieveCitationDetails(connection,statement);
                    break;
                case 6:
                    CitationHelper.checkValidatiyOfVehicle( connection,statement);
                    break;
                case 7:
                    CitationHelper.updateCitation(connection,statement);
                    break;
                case 8:
                    CitationHelper.calculateFine(connection, statement);
                    break;
                default:
                    System.out.println("\nBroken. Choose the Choices from the Available Options only. Try again...\n");
                    break;
            }
        }

    }

    public void displayReportOperations(Connection connection, Statement stmt) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n" + line + menuMessage + " Generating and Maintaining Citations " + line);
            System.out.println("(0) Return to Main Menu.");
            System.out.println("(1) Retrieve Number of Citations.");
            System.out.println("(2) Retrieve Number of Current Vehicles in Parking Violation.");
            System.out.println("(3) Retrieve of ParkingLot and Zone as Tuples.");
            System.out.println("(4) Retrieve Number of Employees having Permits for a given Parking Zone.");
            System.out.println("(5) Retrieve Permit Information.");
            System.out.println("(6) Retrieve an Available Space Number for a Space Type in the Given Parking Lot.");
            System.out.println("(7) Retrieve Number of Available Spaces in a Given Space Type in the Given Parking Lot.");
            System.out.println("(8) Retrieve Number of Expired Permits.");
            System.out.print("\n\nSelect your choice: ");

            int choiceNumber = scanner.nextInt();

            switch(choiceNumber) {
                case 0:
                    System.out.println("\nGoing Back to the Main Menu..\n");
                    return;
                case 1:
                    ReportOperations.getNoOfCitations(connection, stmt);
                    break;
                case 2:
                    ReportOperations.getNoOfViolatingVehicles(connection, stmt);
                    break;
                case 3:
                    ReportOperations.getParkingLotAndZones(connection, stmt);
                    break;
                case 4:
                    ReportOperations.getEmployeesWithPermits(connection, stmt);
                    break;
                case 5:
                    ReportOperations.getPermitInformation(connection, stmt);
                    break;
                case 6:
                	ReportOperations.getAvailableSpaceNumber(connection, stmt);
                    break;
                case 7:
                    ReportOperations.getNoOfAvailableSpaces(connection, stmt);
                    break;
                case 8:
                    ReportOperations.getNoOfExpiredPermits(connection, stmt);
                    break;
                default:
                    System.out.println("\nBroken. Choose the Choices from the Available Options only. Try again...\n");
                    break;
            }
        }
        
    }

    public void displayDataTables(Connection connection, Statement stmt) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n" + line + menuMessage + " Displaying Data tables " + line);
            System.out.println("(0)  Return to Main Menu.");
            System.out.println("(1)  Display Table: Paking Lot.");
            System.out.println("(2)  Display Table: Zone.");
            System.out.println("(3)  Display Table: Space.");
            System.out.println("(4)  Display Table: Driver.");
            System.out.println("(5)  Display Table: Vehicle.");
            System.out.println("(6)  Display Table: Owns.");
            System.out.println("(7)  Display Table: Permit.");
            System.out.println("(8)  Display Table: Associated.");
            System.out.println("(9)  Display Table: Given.");
            System.out.println("(10) Display Table: Holds.");
            System.out.println("(11)  Display Table: Citation.");
            System.out.println("(12)  Display Table: Issued.");
            System.out.println("(13)  Display Table: Appeals.");
            System.out.print("\n\nSelect your choice: ");

            String choiceNumber = scanner.nextLine();

            switch(choiceNumber) {
                case "0":
                    System.out.println("\nGoing Back to the Main Menu..\n");
                    return;
                case "1":
                	DisplayOperation.getNoOfCitations(connection, stmt);
                    break;
                case "2":
                	DisplayOperation.getZone(connection, stmt);
                    break;  
                case "3":
                	DisplayOperation.getSpace(connection, stmt);
                    break;
                case "4":
                	DisplayOperation.getDriver(connection, stmt);
                    break;
                case "5":
                	DisplayOperation.getVehicle(connection, stmt);
                    break;
                case "6":
                	DisplayOperation.getOwns(connection, stmt);
                    break;
                case "7":
                	DisplayOperation.getPermit(connection, stmt);
                    break;
                case "8":
                	DisplayOperation.getAssociated(connection, stmt);
                    break;
                case "9":
                	DisplayOperation.getGiven(connection, stmt);
                    break;
                case "10":
                	DisplayOperation.getHolds(connection, stmt);
                    break;
                case "11":
                	DisplayOperation.getCitation(connection, stmt);
                    break;
                case "12":
                	DisplayOperation.getIssuedTo(connection, stmt);
                    break;
                case "13":
                	DisplayOperation.getAppeals(connection, stmt);
                    break;
                default:
                    System.out.println("\nBroken. Choose the Choices from the Available Options only. Try again...\n");
                    break;
            }
        }
        
    }


}
