/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;
import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PruebaPDF {
    private String A1;
    private String A2;
    private String A3;
    private String A4;
    private String A5;
    private String A6;
    private String A7;
    private String A8;
    private String A9;
    private String A10;
    public PruebaPDF(String Direccion,String a1,String a2,String a3,String a4,String a5, String a6, String a7, String a8, String a9, String a10){
        Ruta = Direccion;
        A1= a1;
        A2= a2;
        A3= a3;
        A4= a4;
        A5= a5;
        A6 = a6;
        A7 = a7;
        A8 = a8;
        A9 = a9;
        A10 = a10;
        crear();
    }
    private String Ruta;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    public void crear(){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(Ruta));
            document.open();
            addMetaData(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("REPORTE DE BALANCE DE CUENTAS");
        document.addSubject("Balance General");
        document.addKeywords("Java, PDF, Balance");
    }

    private  void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Balance General Tabla", catFont);
        anchor.setName("Balance General Tabla");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Reporte generado la fecha: "+ new Date(), subFont);
        Section subCatPart = catPart.addSection(subPara);


        // dejando espacio
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

    }


    
    private  void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        PdfPCell c1 = new PdfPCell(new Phrase("Nombre"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Cantidad"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        table.setHeaderRows(1);

        table.addCell(A1);
        table.addCell(A2);
        table.addCell(A3);
        table.addCell(A4);
        table.addCell(A5);
        table.addCell(A6);
        table.addCell(A9);
        table.addCell(A10);
        table.addCell(A7);
        table.addCell(A8);
        subCatPart.add(table);

    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}