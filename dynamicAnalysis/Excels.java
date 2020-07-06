package weka.dynamicAnalysis;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
 
/**
 * Created by Oladipo Oyekanmi - 14 Nov 2015
 *
 */
public class Excels 
{
	private static final String DATE_FORMAT = "ddMMyyyyHHmmss";
    private HSSFWorkbook book = null;
    private HSSFSheet sheet = null;
    private HSSFCellStyle style = null;
    
    
   //Create a timestamp to be appended to the excel file upon creation, to allow for a new file to be created anytime the trace is executed.
    static String timestp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
    // Specify the location and filename for the excel file, change the value here to indicate the location wwhere the CSV file will be saved
    //public static final String FILE_NAME = "C:\\Users\\asgard\\workspace\\JHotDraw\\src\\dynamicAnalysis\\Traces\\Trace_" + timestp + ".csv";
    public static final String FILE_NAME = "C:\\Users\\asgard\\git\\weka\\weka\\src\\main\\java\\weka\\dynamicAnalysis\\Traces\\Trace_" + timestp + ".csv";
 
    /*
     *
     * The constructor checks if the file exists. If the file exists it loads all the information in
     * our book else, it will create a new instance for HSSFWorkbook. It also create the sheets
     * and call the different functions to create the styles and headers.
     *
     */
    public Excels() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
        	
            try {
            	//System.out.println("I got here");
                book = new HSSFWorkbook(new FileInputStream(file));
                createStyle();
                sheet = book.getSheetAt(0);
               
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            book = new HSSFWorkbook();
            createStyle();
            sheet = book.createSheet("JHotDraw Methods Traced");
        
            header();
        }
    }
 
    /*
     *
     * This method creates the sheet style including font and background
     *
     */
    void createStyle() {
        style = book.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.ORANGE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = book.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
    }
 
    /*
     *
     * This method establishes the header for the excel sheet
     *
     */
    public void header() {
        HSSFRow file = sheet.createRow(0);
        HSSFCell cell = null;
 
        int i = 0;
   /*    
        cell = file.createCell(i);
        cell.setCellValue("Package");
        cell.setCellStyle(style);
        sheet.setColumnWidth(i++, 20 * 256);
 */
//The first header in the CSV file, called Method
        cell = file.createCell(i);
        cell.setCellValue("Method");
        cell.setCellStyle(style);
        sheet.setColumnWidth(i++, 20 * 256);
 //The second header in the CSV file called Stack
        cell = file.createCell(i);
       cell.setCellValue("Stack");
       cell.setCellStyle(style);
       sheet.setColumnWidth(i++, 10 * 256);

    }
 
 
    /*
     *
     * This method saves the book into the specified file
     *
     */
    public void writeFile() {
        try {
            FileOutputStream elFichero = new FileOutputStream(FILE_NAME);
            book.write(elFichero);
            elFichero.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /*
     *
     * This method records a row into sheet
     *            This is a row in the first sheet. The information that it stores is in this order:
     *            Package/Class + Method as "Method", Stack
     *
     */
    public void writeRow(List<String> rowData) {
        HSSFRow file = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < rowData.size(); i++) {
            HSSFCell celda = file.createCell(i);
            celda.setCellValue(rowData.get(i));
        }
        writeFile();
    }
}