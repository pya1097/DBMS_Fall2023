import javax.xml.transform.Source;
import java.sql.*;
import java.util.Scanner;
import java.util.*;
import java.time.LocalDate;
public class CitationHelper {
    static Scanner scanner = new Scanner(System.in);

    public static void addCitation(Connection connection,Statement statement) {
    	// Responsible for adding citations
        try{
            System.out.println("\nPlease enter the Parking Lot ID");
            Integer ParkingLotID = Integer.parseInt(scanner.nextLine());

            System.out.println("Please enter the category of violation");
            String CategoryType = scanner.nextLine();

            System.out.println("\nPlease enter the amount due");
            Integer AmountDue = Integer.parseInt(scanner.nextLine());

            System.out.println("\nPlease enter the Car License Number");
            String CarLicenseNumber = scanner.nextLine();

            String Model;
            String Color;
            String Manufacturer;
            String Year;
            try {
            	// START THE TRANSACTION
            	connection.setAutoCommit(false);
                ResultSet carExists = statement.executeQuery("select * from Vehicle where CarLicenseNumber='"+CarLicenseNumber+"';");
                if(!carExists.next()){
                    System.out.println("\nPlease enter the model of vehicle");
                    Model = scanner.nextLine();

                    System.out.println("\nPlease enter the color of vehicle");
                    Color = scanner.nextLine();

                    System.out.println("\nPlease enter the manufacturer of vehicle");
                    Manufacturer = scanner.nextLine();

                    System.out.println("\nPlease enter the year of vehicle");
                    Year = scanner.nextLine();

                    statement.executeUpdate("INSERT INTO Vehicle (CarLicenseNumber,Model,Color,Manufacturer,`Year`) VALUES\n"
                            + "('"+CarLicenseNumber+"', '"+Model+"', '"+Color+"', '"+Manufacturer+"', "+Year+")");
                }

                statement.executeUpdate("INSERT INTO Citation (ParkingLotID, CitationDate, CitationTime, CategoryType, AmountDue,PaymentStatus)			\n"
                        + "VALUES			\n"
                        + "("+ParkingLotID+", CURRENT_DATE(), CURRENT_TIME(), '"+CategoryType+"',"+AmountDue+",'Due');");

                ResultSet CitationNumber = statement.executeQuery("select max(CitationNumber) as CitationNumber from Citation;");

                if(CitationNumber.next()){
                    statement.executeUpdate("INSERT INTO IssuedTo (CitationNumber, CarLicenseNumber)\n"
                            + "VALUES\n"
                            + "("+CitationNumber.getInt("CitationNumber")+", '"+CarLicenseNumber+"');");
                }
                // IF NO ERROR DURING THE EXECUTION OF THE ABOVE OPERATIONS, COMMIT THE CHANGES
                connection.commit();
                System.out.println("Citation Added successfully ");
            }catch(SQLException error) {
	        	System.out.println(error.getMessage());
	            System.out.println("Issue in addCitation Operation. Hardware/Inputs are malformed..");
	            // IF THERE ARE ANY ISSUES DURING THE ADDITION OF A CITATION, THEN WE ROLLBACK THE TRANSACTION BACK TO THE BEGINNING
	            connection.rollback();
	            System.out.println("Rollback Complete!");
	        }finally {
	        	// END OF THE TRANSACTION
	        	connection.setAutoCommit(true);
	        }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void appealCitationByDriver(Connection connection,Statement statement){
       try{
           System.out.println("Please enter the Citation Number");
           Integer CitationNumber = Integer.parseInt(scanner.nextLine());

           System.out.println("Please enter the Driver ID");
           String DriverID = scanner.nextLine();

           System.out.println("Please enter the Driver Remark");
           String DriverRemark = scanner.nextLine();

           statement.executeUpdate("INSERT INTO Appeals (CitationNumber, DriverID, DriverRemark)\n"
                   + "VALUES\n"
                   + "("+CitationNumber+", '"+DriverID+"', '"+DriverRemark+"');");

           System.out.println("Successfully Added Driver Appeal");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static void updateCitationAppealByAdmin(Connection connection,Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = Integer.parseInt(scanner.nextLine());

            System.out.println("Please enter the Driver ID");
            String DriverID = scanner.nextLine();

            System.out.println("Please enter the Admin Remark");
            String AdminRemark = scanner.nextLine();

            statement.executeUpdate("Update Appeals set AdminRemark='"+AdminRemark+"' where CitationNumber="+CitationNumber+" and DriverID='"+DriverID+"';");

            System.out.println("Successfully Updated Admin Remark");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateCitationAppealByDriver(Connection connection,Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = Integer.parseInt(scanner.nextLine());

            System.out.println("Please enter the Driver ID");
            String DriverID = scanner.nextLine();

            System.out.println("Please enter the Admin Remark");
            String DriverRemark = scanner.nextLine();

            statement.executeUpdate("Update Appeals set DriverRemark='"+DriverRemark+"' where CitationNumber="+CitationNumber+" and DriverID='"+DriverID+"';");

            System.out.println("Successfully Updated Driver Remark");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateCitationPaymentInfo(Connection connection,Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = Integer.parseInt(scanner.nextLine());
            String DriverID = getDriverIdOfCitation(statement,CitationNumber);
            if(DriverID.equals(" ")) {
            	 System.out.println("Payment Status cannot be updated for No Permit Category Citations.");
            	 return;
            }

            statement.executeUpdate("Update Citation set PaymentStatus='Paid', AmountDue = 0 where CitationNumber="+CitationNumber+";");
            //add to pays table
            statement.executeUpdate("INSERT INTO Pays (CitationNumber, DriverID)\n"
                    + "VALUES\n"
                    + "("+CitationNumber+", '"+DriverID+"');");
            
            System.out.println("Payment Status updated successfully..");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getDriverIdOfCitation(Statement statement, Integer CitationNumber) {
    	String DriverId = " ";
    	try {
    		ResultSet result = statement.executeQuery("SELECT CarLicenseNumber FROM IssuedTo WHERE CitationNumber ="+CitationNumber+";");
    		while(result.next()) {
    			String CarLicenseNumber = result.getString("CarLicenseNumber");
    			ResultSet result1 = statement.executeQuery("SELECT DriverID FROM Owns WHERE CarLicenseNumber ='"+CarLicenseNumber+"';");
    			while(result1.next()) {
    				DriverId = result1.getString("DriverID");
        			return DriverId;
    			}
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return DriverId;
    }
    
    public static  void updateCitation(Connection connection,Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = Integer.parseInt(scanner.nextLine());
            String DriverID = getDriverIdOfCitation(statement,CitationNumber);
            String value;
	        String option;
            
            System.out.println("What data would you like to update for the given Citation\n");
            System.out.println("1. Update ParkingLotID:");
	        System.out.println("2. Update CitationDate:");
	        System.out.println("3. Update CitationTime:");
	        System.out.println("4. CategoryType: (No Permit category cannot be updated)");
	        System.out.println("5. AmountDue:");
	        System.out.println("6. PaymentStatus:");
	        option = scanner.nextLine();
	        System.out.println("Enter the new value:");
	        value = scanner.nextLine();
	        
	        switch(option) {
	        case "1" :
	        	statement.executeUpdate("Update Citation set ParkingLotID ="+ Integer.parseInt(value)+" where CitationNumber="+CitationNumber+";");
	        	break;
	        case "2" :
	        	statement.executeUpdate("Update Citation set CitationDate ='"+ value +"' where CitationNumber="+CitationNumber+";");
	        	break;
	        case "3":
	        	statement.executeUpdate("Update Citation set CitationTime ='"+ value +"' where CitationNumber="+CitationNumber+";");
	        	break;
	        case "4":
	        	statement.executeUpdate("Update Citation set CategoryType ='"+ value +"' where CitationNumber="+CitationNumber+";");
	        	break;
	        case "5":
	        	statement.executeUpdate("Update Citation set AmountDue ="+ value +" where CitationNumber="+CitationNumber+";");
	        	break;
	        case "6":
	        	if(DriverID.equals(" ")) {
	            	System.out.println("Payment Status cannot be updated for No Permit Category Citations.");
	            	return;
	            }
	        	statement.executeUpdate("Update Citation set PaymentStatus ='"+ value +"' where CitationNumber="+CitationNumber+";");
	        	if(value.equals("Paid")) {
	        		statement.executeUpdate("INSERT INTO Pays (CitationNumber, DriverID)\n"
	                        + "VALUES\n"
	                        + "("+CitationNumber+", '"+DriverID+"');");
	        	}
	        	break;
	        default:
                System.out.println("Choose appropriate options only. Try again..");
                throw new IllegalArgumentException("Invalid Choice is Selected: " + option);
	        }
              System.out.println("Citation updated Successfully..") ;
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void checkValidatiyOfVehicle(Connection connection, Statement statement){
        try {
            System.out.println("Please enter the Car License Number:");
            String CarLicenseNumber = scanner.nextLine();

            System.out.println("Please enter the Parking Lot ID:");
            Integer ParkingLotID = Integer.parseInt(scanner.nextLine());

            System.out.println("Please enter the Zone ID:");
            String ZoneID = scanner.nextLine();

            System.out.println("Please enter the Space Type:");
            String SpaceType = scanner.nextLine();
             ResultSet result = statement
                    .executeQuery("SELECT\n" +
                            "CASE \n" +
                            "WHEN "+ParkingLotID+"<>(SELECT p.ParkingLotID FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 'Invalid'\n" +
                            "when '"+ZoneID+"'<>(SELECT p.ZoneID FROM Permit p, Given g WHERE p.PermitID=g.PermitID and g.CarLicenseNumber='"+CarLicenseNumber+"') then 'Invalid'\n" +
                            "when '"+SpaceType+"'<>(select SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and g.CarLicenseNumber='"+CarLicenseNumber+"') then 'Invalid'\n" +
                            "when CURRENT_DATE()> (select p.ExpirationDate FROM Permit p, Given g WHERE p.PermitID=g.PermitID and g.CarLicenseNumber='"+CarLicenseNumber+"') then 'Expired'\n" +
                            "when CURRENT_TIME() > (select p.ExpirationTime FROM Permit p, Given g WHERE p.PermitID=g.PermitID and g.CarLicenseNumber='"+CarLicenseNumber+"' and p.ExpirationDate=CURRENT_DATE()) then 'Expired'\n" +
                            "when (SELECT g.CarLicenseNumber FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') is null then 'No Permit'\n" +
                            "else 'Valid'\n" +
                            "end as CategoryType;");

            if (result.next()) {
                System.out.println(result.getString("CategoryType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void retrieveCitationDetails(Connection connection, Statement statement){
        try {

            System.out.println("How would you like to retrieve Citation Details?\n");
            System.out.println("(1) Enter Citation Number and Car License Number");
            System.out.println("(2) Enter Citation Number");
            System.out.println("(3) Enter Car License Number");

            String CarLicenseNumber=null;
            Integer CitationNumber=null;

            String type = scanner.nextLine();
            ResultSet result=null;
            switch(type){
                case "1":
                    System.out.println("Please enter the Citation Number");
                    CitationNumber = Integer.parseInt(scanner.nextLine());

                    System.out.println("Please enter the Car Lisence Number");
                    CarLicenseNumber = scanner.nextLine();
                    result = statement.executeQuery("select c.CitationNumber,c.ParkingLotID,c.CitationDate,c.CitationTime,c.CategoryType,c.AmountDue,c.PaymentStatus from Citation c , " +
                            "IssuedTo it where c.CitationNumber =it.CitationNumber and it.CitationNumber = "+CitationNumber+" and it.CarLicenseNumber='"+CarLicenseNumber+"';");
                    break;
                case "2":
                    System.out.println("Please enter the Citation Number");
                    CitationNumber = Integer.parseInt(scanner.nextLine());
                    result = statement
                            .executeQuery("SELECT * FROM Citation WHERE CitationNumber = "+CitationNumber+";\n");

                    break;
                case "3":
                    System.out.println("\nPlease enter the Car Lisence Number");
                    CarLicenseNumber = scanner.nextLine();
                    result = statement.executeQuery("select c.CitationNumber,c.ParkingLotID,c.CitationDate,c.CitationTime,c.CategoryType,c.AmountDue,c.PaymentStatus from Citation c , " +
                            "IssuedTo it where c.CitationNumber =it.CitationNumber and it.CarLicenseNumber='"+CarLicenseNumber+"';");
                    break;
            }

            if(!result.isBeforeFirst()){
                System.out.println("No citation data present!");
            }else{
                while(result.next()) {
                    System.out.println("CitationNumber: "+result.getString("CitationNumber")+
                            "\nParkingLotID: "+result.getString("ParkingLotID")+
                            "\nCitationDate: "+result.getString("CitationDate")+
                            "\nCitationTime: "+result.getString("CitationTime")+
                            "\nCategoryType: "+result.getString("CategoryType")+
                            "\nAmountDue: "+result.getString("AmountDue")+
                            "\nPaymentStatus: "+result.getString("PaymentStatus"));
                    System.out.println("--------------------------------------------------------------------");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void calculateFine( Connection connection, Statement statement){
        try {
            System.out.println("\nPlease enter the Car Lisence Number");
            String CarLicenseNumber = scanner.nextLine();

            System.out.println("\nPlease enter the Parking Lot ID");
            Integer ParkingLotID = Integer.parseInt(scanner.nextLine());

            System.out.println("\nPlease enter the Zone ID");
            String ZoneID = scanner.nextLine();

            System.out.println("\nPlease enter the Space Type");
            String SpaceType = scanner.nextLine();
            ResultSet result = statement
                    .executeQuery("SELECT\n" +
                            "case \n" +
                            "\twhen "+ParkingLotID+"<>(select p.ParkingLotID FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') AND 'Handicap'=(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 12.5\n" +
                            "\twhen '"+ZoneID+"'<>(select p.ZoneID FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') AND 'Handicap'=(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 12.5\n" +
                            "\twhen '"+SpaceType+"'<>(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') AND 'Handicap'=(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 12.5\n" +
                            "\twhen CURRENT_DATE() > (select p.ExpirationDate FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') AND 'Handicap'=(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 15\n" +
                            "\twhen CURRENT_TIME() > (select p.ExpirationTIme FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') AND 'Handicap'=(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 15\n" +
                            "\twhen "+ParkingLotID+"<>(select p.ParkingLotID FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 25\n" +
                            "\twhen '"+ZoneID+"'<>(select p.ZoneID FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 25\n" +
                            "\twhen '"+SpaceType+"'<>(select p.SpaceType FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 25\n" +
                            "\twhen CURRENT_DATE() > (select p.ExpirationDate FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') then 30\t\n" +
                            " \twhen CURRENT_TIME()> (select p.ExpirationTIme FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"' and p.ExpirationDate=CURRENT_DATE()) then 30\n" +
                            " \twhen (SELECT g.CarLicenseNumber FROM Permit p, Given g WHERE p.PermitID=g.PermitID and  g.CarLicenseNumber='"+CarLicenseNumber+"') is null then 40\n" +
                            "else 0\n" +
                            "end as AmountDue;");

            if (result.next()) {
                System.out.println(result.getInt("AmountDue"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
