/*
 *Created by Oladipo Oyekanmi -  14 Nov 2015
 *This file logs the runtime trace details into a MySQL database for further analysis / mining
 *To get the number of calls per method in a CSV format, run the file CreateExcels.java after generating the trace data
 */
package weka.dynamicAnalysis;
 
import java.text.SimpleDateFormat;
//import static java.text.DateFormatSymbols.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.EventListener;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.sql.*;
import java.lang.reflect.*;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


import org.aspectj.lang.Signature;


public aspect Trace
{
	
	// The following date format is used to get the time each method is run
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	// This format is used to get the current running batch which is later used to generate the CSV file
	private static final String DATE_FORMAT_ = "yyyyddMMHHmmss";
	
	// Generate a batch ID
	public static String timestp = "weka" + new SimpleDateFormat(DATE_FORMAT_).format(new Date());
	
	public static String timestps = new SimpleDateFormat(DATE_FORMAT).format(new Date());
    pointcut logMethods() : (execution(* *(..))&& !cflow(within(Trace)));
               
    Object around() : logMethods() 
    
    {
        
      Object obj = proceed();
      long time = System.currentTimeMillis();
        time = System.currentTimeMillis() - time;

        // Get the current run time details
        String Date_Time = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        
        //Get the class name of the current running method
        String Package_Class = thisJoinPointStaticPart.getSignature().getDeclaringTypeName();
      
        //Get the current running method
        String Method = thisJoinPointStaticPart.getSignature().getName();
       
        String Method_ = Package_Class +"."+ Method;
        

       	// Get the execution time
        String Execution_Time = String.valueOf(time);

        // Here we log the trace details into a mysql database
        /* Database Information
         * DB Name - Log
         * Table Name - trace_details_
         * Create Statement for the table is shown below
          Create Statement - 
          CREATE TABLE `trace_details_` (
  			`ID` int(11) NOT NULL AUTO_INCREMENT,
			  `Date_Time` datetime DEFAULT NULL,
			  `BatchID` varchar(45) DEFAULT NULL,
			  `Package_Class` varchar(250) DEFAULT NULL,
			  `Method` varchar(200) DEFAULT NULL,
			  `Execution_Time` varchar(45) DEFAULT NULL,
			  PRIMARY KEY (`ID`)
			) ENGINE=InnoDB AUTO_INCREMENT=360 DEFAULT CHARSET=cp850;

         */
        
        try 
        {
        
        	// Set the connection parameters
        	Class.forName("com.mysql.jdbc.Driver");        	
        	String connectionUrl = "jdbc:mysql://localhost:3306/log?user=root&password=Admin123$";
        	Connection con = DriverManager.getConnection(connectionUrl);
        	
        	//Insert the trace details into the log table
        	PreparedStatement st=con.prepareStatement("INSERT INTO trace_details_(Date_Time, BatchID, Package_Class, Method, Execution_Time) VALUES (?, ?, ?, ?, ?)");
        	st.setString(1, Date_Time);
        	st.setString(2, timestp);
        	st.setString(3, Package_Class);
        	st.setString(4, Method_);
        	st.setString(5, Execution_Time);
        	st.executeUpdate();
    
        }
        	catch (SQLException s) 
        {
        	System.out.println("SQL Exception: "+ s.toString());
        }
        	catch (ClassNotFoundException cE) 
        {
        	System.out.println("Class Not Found Exception: "+ cE.toString());
        }

     return obj;
   
       }
   
}   
    