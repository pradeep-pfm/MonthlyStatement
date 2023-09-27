package com.galaxy.service;

import com.galaxy.entity.MonthlyStmt;
import com.itextpdf.text.pdf.PdfDocument;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface MonthlyStmtService {
    public MonthlyStmt saveCustomerDtls(MonthlyStmt stmt);
    public Optional<MonthlyStmt> getMonthlyStatementById(Integer accountId);
    public String getMonthlyStatementPDFByAccountId(Integer accountId);

    public List<String> getPDFListFromDrive(File currentDir);

}
