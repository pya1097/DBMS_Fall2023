import javax.xml.transform.Source;
import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;
import java.util.*;
public class CitationHelper {
    static Scanner scanner = new Scanner(System.in);

    public static void addCitation(Statement statement) {
        try{
            System.out.println("\nPlease enter the Parking Lot ID");
            Integer ParkingLotID = scanner.nextInt();

            System.out.println("Please enter the category of violation");
            String CategoryType = scanner.nextLine();

            System.out.println("\nPlease enter the amount due");
            Integer AmountDue = scanner.nextInt();

            System.out.println("\nPlease enter the Car License Number");
            String CarLicenseNumber = scanner.next();

            String Model;
            String Color;
            String Manufacturer;
            Integer Year;

            ResultSet carExists = statement.executeQuery("select * from Vehicle where CarLicenseNumber='"+CarLicenseNumber+"';");
            if(!carExists.next()){
                System.out.println("\nPlease enter the model of vehicle");
                Model = scanner.next();

                System.out.println("\nPlease enter the color of vehicle");
                Color = scanner.next();

                System.out.println("\nPlease enter the manufacturer of vehicle");
                Manufacturer = scanner.next();

                System.out.println("\nPlease enter the year of vehicle");
                Year = scanner.nextInt();

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
            System.out.println("Citation Added successfully ");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void appealCitationByDriver(Statement statement){
       try{
           System.out.println("Please enter the Citation Number");
           Integer CitationNumber = scanner.nextInt();

           System.out.println("Please enter the Driver ID");
           String DriverID = scanner.next();

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

    public static void updateCitationAppealByAdmin(Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = scanner.nextInt();

            System.out.println("Please enter the Driver ID");
            String DriverID = scanner.next();

            System.out.println("Please enter the Admin Remark");
            String AdminRemark = scanner.nextLine();

            statement.executeUpdate("Update Appeals set AdminRemark='"+AdminRemark+"' where CitationNumber="+CitationNumber+" and DriverID='"+DriverID+"';");

            System.out.println("Successfully Updated Admin Remark");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateCitationAppealByDriver(Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = scanner.nextInt();

            System.out.println("Please enter the Driver ID");
            String DriverID = scanner.next();

            System.out.println("Please enter the Admin Remark");
            String DriverRemark = scanner.nextLine();

            statement.executeUpdate("Update Appeals set DriverRemark='"+DriverRemark+"' where CitationNumber="+CitationNumber+" and DriverID='"+DriverID+"';");

            System.out.println("Successfully Updated Driver Remark");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateCitationPaymentInfo(Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = scanner.nextInt();

//       DO WE NEED TO DELETE ENTRY FROM ISSUED TO?????

            statement.executeUpdate("Update Citation set PaymentStatus='Paid', AmountDue = 0 where CitationNumber="+CitationNumber+";");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static  void updateCitation(Statement statement){
        try{
            System.out.println("Please enter the Citation Number");
            Integer CitationNumber = scanner.nextInt();

            while(true){
                System.out.println("\nWhat data would you like to update for the given Car License Number");
                System.out.println("(1) ");
                System.out.println("(2) Enter Citation Number");
                System.out.println("(3) Enter Car License Number");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void checkValidatiyOfVehicle( Statement statement){
        try {
            System.out.println("\nPlease enter the Car License Number");
            String CarLicenseNumber = scanner.next();

            System.out.println("\nPlease enter the Parking Lot ID");
            Integer ParkingLotID = scanner.nextInt();

            System.out.println("\nPlease enter the Zone ID");
            String ZoneID = scanner.next();

            System.out.println("\nPlease enter the Space Type");
            String SpaceType = scanner.next();
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

    public static void retrieveCitationDetails( Statement statement){
        try {

            System.out.println("\nHow would you like to retrieve Citation Details?");
            System.out.println("(1) Enter Citation Number and Car License Number");
            System.out.println("(2) Enter Citation Number");
            System.out.println("(3) Enter Car License Number");

            String CarLicenseNumber=null;
            Integer CitationNumber=null;

            int type = scanner.nextInt();
            ResultSet result=null;
            switch(type){
                case 1:
                    System.out.println("\nPlease enter the Citation Number");
                    CitationNumber = scanner.nextInt();

                    System.out.println("\nPlease enter the Car Lisence Number");
                    CarLicenseNumber = scanner.next();
                    result = statement.executeQuery("select c.CitationNumber,c.ParkingLotID,c.CitationDate,c.CitationTime,c.CategoryType,c.AmountDue,c.PaymentStatus from Citation c , " +
                            "IssuedTo it where c.CitationNumber =it.CitationNumber and it.CitationNumber = "+CitationNumber+" and it.CarLicenseNumber='"+CarLicenseNumber+"';");
                    break;
                case 2:
                    System.out.println("\nPlease enter the Citation Number");
                    CitationNumber = scanner.nextInt();
                    result = statement
                            .executeQuery("SELECT * FROM Citation WHERE CitationNumber = "+CitationNumber+";\n");

                    break;
                case 3:
                    System.out.println("\nPlease enter the Car Lisence Number");
                    CarLicenseNumber = scanner.next();
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

    public static void calculateFine( Statement statement){
        try {
            System.out.println("\nPlease enter the Car Lisence Number");
            String CarLicenseNumber = scanner.next();

            System.out.println("\nPlease enter the Parking Lot ID");
            Integer ParkingLotID = scanner.nextInt();

            System.out.println("\nPlease enter the Zone ID");
            String ZoneID = scanner.next();

            System.out.println("\nPlease enter the Space Type");
            String SpaceType = scanner.next();
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
