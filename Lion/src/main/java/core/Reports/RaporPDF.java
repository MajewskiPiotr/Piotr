package core.Reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import core.Tools.Configuration.Property;
import org.testng.ITestResult;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-07.
 */
public class RaporPDF {
    private static final BaseColor green = new BaseColor(64, 128, 0);
    private static final BaseColor red = BaseColor.RED;
    private static Timestamp time = new Timestamp(System.currentTimeMillis());
    private static String FILE = Property.getProperty("basePath") + "/Lion/DaneTestowe/raport_z_testow_" + time.toString().substring(0,10) + ".pdf";



    public void create(List<ITestResult> listOfResult) throws FileNotFoundException, DocumentException {
        System.out.println(FILE);
        //tabela pomocnicza
        List<String> tempList = new ArrayList<>();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();

        addMetaData(document);
        Paragraph naglowek = new Paragraph("Raport z testów regresji", new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD));
        naglowek.setAlignment(Element.ALIGN_CENTER);
        document.add(naglowek);
        Paragraph p = new Paragraph();

        addEmptyLine(p, 3);
        Paragraph date = new Paragraph(time.toString().substring(0, 16));
        date.setAlignment(Element.ALIGN_CENTER);
        document.add(date);

        addEmptyLine(p, 5);
        document.add(p);
        addResultTable(document, listOfResult);

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
            setErrorCouse(result, test);
        }
        document.add(listaTestow);
        document.close();
    }

    private void setErrorCouse(ITestResult result, Paragraph paragraph) {
        Font font = new Font();
        font.setColor(BaseColor.RED);
        font.setSize(8);
        if (!result.isSuccess()) {
            paragraph.add(new Paragraph(result.getThrowable().getMessage(), font));
        }
    }

    private void addResultTable(Document document, List<ITestResult> listOfResult) {
        int pass = 0;
        int fail = 0;
        for (ITestResult result : listOfResult) {
            if (result.isSuccess()) {
                pass++;
            } else fail++;
        }

        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Total PASSED:"));
        PdfPCell cell2 = new PdfPCell(new Phrase("Total FAILED:"));
        table.addCell(cell);
        table.addCell(cell2);
        table.addCell("" + pass);
        table.addCell("" + fail);
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    public String setResult(ITestResult result) {
        if (result.isSuccess()) {
            return "        PASS : ";
        } else {
            return "      FAIL : ";
        }
    }

    public Font setFontScenariusza(ITestResult result) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        if (result.isSuccess()) {
            font.setColor(green);
        } else {
            font.setColor(red);
        }
        return font;
    }

    public Font setFontTestu(ITestResult result) {
        Font font = new Font();
        if (result.isSuccess()) {
            font.setColor(green);
            font.setSize(10);
        } else {
            font.setColor(red);
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

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}


