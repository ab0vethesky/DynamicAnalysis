package weka.dynamicAnalysis;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CreateExcel {
	private static final String DATE_FORMAT = "ddMMyyyyHHmmss";
	static String timestp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
   
    public static void main(String[] args) {
    	
    	 String filename ="C:\\Users\\asgard\\git\\weka\\weka\\src\\main\\java\\weka\\dynamicAnalysis\\Traces\\Trace_" + timestp + ".csv";
        try {
            FileWriter fw = new FileWriter(filename);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/log", "root", "Admin123$");
            //String query = "select * from trace_details where BatchID ='weka26122015122833'";
            String query = 
            "SELECT Method, COUNT(BatchID)AS No_of_Calls FROM TRACE_DETAILS_ WHERE BatchID in"
			+"("
			+"select MAX(BatchID) FROM trace_details_ WHERE Package_Class !='weka.dynamicAnalysis.CreateExcel'"
			+")"			
			+"GROUP BY Method";		
            		
            /*"SELECT * FROM TRACE_DETAILS WHERE BatchID IN"
            +"("
            +"SELECT MAX(BatchID) FROM trace_details"
            +")";*/
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            fw.append("Method");
            fw.append(',');
            fw.append("No of Calls");
            fw.append('\n');
            
            while (rs.next()) {                
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append('\n');
               }
            fw.flush();
            fw.close();
            conn.close();
            rs.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}