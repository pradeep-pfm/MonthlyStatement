package com.galaxy.service;

import com.galaxy.config.HeaderFooterPageEvent;
import com.galaxy.config.InsertHeaderAndFooter;
import com.galaxy.entity.MonthlyStmt;
import com.galaxy.repository.MonthlyStmtRepository;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.security.PdfEncryptionKeySize;
import com.spire.pdf.security.PdfPermissionsFlags;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonthlyStmtServiceImpl implements MonthlyStmtService {
    @Autowired
    private MonthlyStmtRepository monthlyStmtRepository;


    public MonthlyStmt saveCustomerDtls(MonthlyStmt stmt) {
        return monthlyStmtRepository.save(stmt);
    }

    @Override
    public Optional<MonthlyStmt> getMonthlyStatementById(Integer accountId) {
        Optional<MonthlyStmt> stmtOptional = monthlyStmtRepository.findById(accountId);
        MonthlyStmt stmt = stmtOptional.get();

        System.out.println(stmt.getAccountId());

        Integer acccountId = stmt.getAccountId();
        SimpleDateFormat format = new SimpleDateFormat("MMM_yyyy");
        String date = format.format(new Date());
        System.out.println(date);
        System.out.println("*********************");
        String pdfFilePath = "/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/" + date + "_Monthly_statement_" + acccountId + ".pdf"; // Replace with the path to your PDF file

        try {
            // Load the PDF document
            PDDocument document = Loader.loadPDF(new File(pdfFilePath));
            //update pdf header & footer
            //updatePDFHeaderAndFooter(document, new File(pdfFilePath));

            System.out.println("Document is : " + document);
            System.out.println("Document is : " + pdfFilePath);
            // Create a PDFTextStripper object
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            // Extract text from the PDF
            String text = pdfTextStripper.getText(document);

            // Close the PDF document
            document.close();

            // Print the extracted text
            System.out.println("Extracted text from the PDF:\n" + text);

        } catch (IOException  e) {
            e.printStackTrace();
        }

        return monthlyStmtRepository.findById(accountId);
    }


    @Override
    public String getMonthlyStatementPDFByAccountId(Integer accountId) {
        {
            Integer acccountId = 7606;
            SimpleDateFormat format = new SimpleDateFormat("MMM_yyyy");
            String date = format.format(new Date());
            System.out.println(date);
            System.out.println("*********************");
            String pdfFilePath = "/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/" + date + "_Monthly_statement_" + acccountId + ".pdf"; // Replace with the path to your PDF file

            try {
                // Load the PDF document
                PDDocument document = Loader.loadPDF(new File(pdfFilePath));
                System.out.println("Document is : " + document);
                System.out.println("Document is : " + pdfFilePath);
                // Create a PDFTextStripper object
                PDFTextStripper pdfTextStripper = new PDFTextStripper();

                // Extract text from the PDF
                String text = pdfTextStripper.getText(document);

                // Close the PDF document
                document.close();

                // Print the extracted text
                System.out.println("Extracted text from the PDF:\n" + text);
                return text;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public List<String> getPDFListFromDrive(File currentDir) {

            MonthlyStmt stmt =null;
            ArrayList<String> rtnFiles = new ArrayList<>();
            List<String> textId = new ArrayList<>();
            try {
                File[] files = currentDir.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        //textId.addAll(getPDFListFromDrive(file));
                        rtnFiles.addAll(getPDFListFromDrive(file));
                    } else {
                        if (file.getName().endsWith((".pdf"))) {
                            rtnFiles.add(file.getName());
                        }
                    }
                }
                for(String pdf : rtnFiles){
                        int startPos = pdf.lastIndexOf("_"); //Sep_2023_Monthly_statement_8001.pdf
                        int dotPos = pdf.indexOf(".", startPos);
                        System.out.println("dotpos "+dotPos+"  start pos "+startPos);
                        String freeTextId = pdf.substring(startPos + 1, dotPos);
                        System.out.println("text id "+freeTextId+" pdf "+pdf);
                        textId.add(freeTextId);
                        //get account id by free text id
                        Integer accountId = monthlyStmtRepository.findAccountIdByFreeTextId(Integer.parseInt(freeTextId));

                       //database fetch customer dob and Email
                    if(accountId != null) {
                        System.out.println("accountId  "+accountId);
                        stmt = monthlyStmtRepository.findCustomerDtlsByAccountId(accountId);
                        System.out.println("************"+stmt.getCustomerEmail());
                    }
                    if(stmt!=null && stmt.getCustomerEmail()!=null) {
                        // to find exact pdf file statement for particular email
                        String pdfFilePath = "/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/" + pdf;
                        //******update pdf header & footer METHOD - 1
                        //HeaderFooterPageEvent.updatePdfHeaderFooter(pdfFilePath);

//Load a PDF document AND UPDATE HEADER & FOOTER METHOD - 2
                        PdfDocument doc = new PdfDocument();
                        doc.loadFromFile(pdfFilePath);
                        InsertHeaderAndFooter updatePDF=new InsertHeaderAndFooter();
                        //Draw header on the document
                        updatePDF.drawHeader(doc);
                        //Draw footer on the document
                        updatePDF.drawFooter(doc);
                        //Save the document
// Here is source code to make password-protected pdf
//                        PdfEncryptionKeySize keySize = PdfEncryptionKeySize.Key_128_Bit;
//                        String openPassword = "root";
//                        String permissionPassword = "test";
//                        EnumSet flags = EnumSet.of(PdfPermissionsFlags.Print, PdfPermissionsFlags.Fill_Fields);
//                        doc.getSecurity().encrypt(openPassword, permissionPassword, flags, keySize);
                          doc.saveToFile(pdfFilePath);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception in catch fetch data "+e.getMessage());
            }
            return textId;
        }
}
