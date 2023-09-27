package com.galaxy.config;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.automaticfields.PdfCompositeField;
import com.spire.pdf.automaticfields.PdfPageCountField;
import com.spire.pdf.automaticfields.PdfPageNumberField;
import com.spire.pdf.graphics.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

public class InsertHeaderAndFooter {

    //Here is the code to update Header and Footer of an Existing PDF
    public static void drawHeader(com.spire.pdf.PdfDocument doc)
    {
        //Get page size
        Dimension2D pageSize = doc.getPages().get(0).getSize();

        //Declare two float variable
        float x = 90;
        float y = 20;

        for (int i = 0; i < doc.getPages().getCount(); i++)
        {
            //Draw image at the specified position
            com.spire.pdf.graphics.PdfImage headerImage = com.spire.pdf.graphics.PdfImage.fromFile("/Users/pradeepkumar/Desktop/workspaceStatement/MonthlyStatement/images (1).jpeg");
            float width = headerImage.getWidth()/3;
            float height = headerImage.getHeight()/3;
            doc.getPages().get(i).getCanvas().drawImage(headerImage, x,  y, width, height);

            //Draw a line under the image
            PdfPen pen = new PdfPen(PdfBrushes.getGray(), 0.5f);
            doc.getPages().get(i).getCanvas().drawLine(pen, x, y + height + 2,  pageSize.getWidth() - x, y + height + 2);
        }
    }

    public  static void drawFooter(PdfDocument doc)
    {
        //Get page size
        Dimension2D pageSize = doc.getPages().get(0).getSize();

        //Declare two float variable
        float x = 90;
        float y = (float) pageSize.getHeight()- 72;

        for (int i = 0; i < doc.getPages().getCount(); i++)
        {
            //Draw a line at the specified position
            PdfPen pen = new PdfPen(PdfBrushes.getGray(), 0.5f);
            doc.getPages().get(i).getCanvas().drawLine(pen, x, y, pageSize.getWidth() - x, y);

            //Draw text at the specified position
            y = y + 5;
            PdfTrueTypeFont font = new PdfTrueTypeFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
            PdfStringFormat format = new PdfStringFormat(PdfTextAlignment.Left);
            String footerText = "Your Name Here\nTel:0101112222\nWebsite:www.yourwebsite.com";
            doc.getPages().get(i).getCanvas().drawString(footerText, font, PdfBrushes.getBlack(), x, y, format);

            //Draw page number
            PdfPageNumberField number = new PdfPageNumberField();
            PdfPageCountField count = new PdfPageCountField();
            PdfCompositeField compositeField = new PdfCompositeField(font, PdfBrushes.getBlack(), "Page {0} of {1}", number, count);
            compositeField.setStringFormat(new PdfStringFormat(PdfTextAlignment.Right, PdfVerticalAlignment.Top));
            Dimension2D fontSize = font.measureString(compositeField.getText());
            compositeField.setBounds(new Rectangle2D.Float((float) (pageSize.getWidth() - x - fontSize.getWidth()), y, (float)fontSize.getWidth(), (float)fontSize.getHeight()));
            compositeField.draw(doc.getPages().get(i).getCanvas());
        }
    }

//    public static void main(String[] args) {
//
//        //Load a PDF document
//        PdfDocument doc = new PdfDocument();
//        doc.loadFromFile("/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/Sep_2023_Monthly_statement_7601.pdf");
//
//        //Draw header on the document
//        drawHeader(doc);
//
//        //Draw footer on the document
//        drawFooter(doc);
//
//        //Save the document
//        doc.saveToFile("/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/Sep_2023_Monthly_statement_7601.pdf");
//    }

}