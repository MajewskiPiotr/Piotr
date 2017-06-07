package core.Reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import core.Tools.Configuration.Property;
import org.openqa.selenium.Keys;
import org.testng.ITestResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-07.
 */
public class RaporPDF {
    private static String FILE = Property.getProperty("basePath") + "/Lion/DaneTestowe/raport" + new Date().getTime() + ".pdf";

    public void create(List<ITestResult> listOfResult) throws FileNotFoundException, DocumentException {
        //tabela pomocnicza
        List<String> tempList = new ArrayList<>();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        addMetaData(document);
        document.add(new Paragraph("TEST REGRESYJNE - RAPORT"));
        Chapter listaTestow = new Chapter(new Paragraph("Wyniki przeprowadzonych testów"), 1);

        for (ITestResult result : listOfResult) {
            Font fontScenariusza = setFontScenariusza(result);
            Font fontTestu = setFontTestu(result);


            String nazwaScenar = result.getInstance().getClass().getSimpleName();
            if (!tempList.contains(nazwaScenar)) {
                Paragraph paragraph = new Paragraph(nazwaScenar, fontScenariusza);
                listaTestow.addSection(paragraph);
                tempList.add(nazwaScenar);
            }
            Paragraph test = new Paragraph(setResult(result) + result.getName(), fontTestu);
            listaTestow.add(test);
        }
        document.add(listaTestow);
        document.close();


    }
    public String setResult(ITestResult result){
        if (result.isSuccess()){return "        PASS : ";}
        else {return "      FAIL : ";}
    }

    public Font setFontScenariusza(ITestResult result){
        Font font= new Font(Font.FontFamily.HELVETICA,12, Font.BOLD);

        if(result.isSuccess()){
            font.setColor(BaseColor.GREEN);
        }
        else {
            font.setColor(BaseColor.RED);
        }
        return font;
    }

    public Font setFontTestu(ITestResult result){
        Font font= new Font();
        if(result.isSuccess()){
            font.setColor(BaseColor.GREEN);
            font.setSize(10);
        }
        else {
            font.setColor(BaseColor.RED);
            font.setSize(10);
        }
        return font;
    }

    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }
}


