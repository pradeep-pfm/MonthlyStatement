package com.galaxy.controller;

import com.galaxy.entity.MonthlyStmt;
import com.galaxy.service.MonthlyStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/monthly")
@EnableScheduling
public class MonthlyStmtController {
    @Autowired
    private MonthlyStmtService monthlyStmtService;
@PostMapping("/postStmt")
    public MonthlyStmt postMonthlyStmt(@RequestBody MonthlyStmt stmt) {

        return monthlyStmtService.saveCustomerDtls(stmt);
    }

    @GetMapping("accountId"+"/{accountId}")
    public Optional<MonthlyStmt> getMonthlyStmtById(@PathVariable Integer accountId){
         return  monthlyStmtService.getMonthlyStatementById(accountId);
    }

    @GetMapping("/{accountId}")
    public String getMonthlyStmtPDFByAccountId(@PathVariable Integer accountId){

        return monthlyStmtService.getMonthlyStatementPDFByAccountId(accountId);
    }

    //@GetMapping("/allPDFList")
    @Scheduled(fixedDelay = 2000)
    public List<String> getMonthlyStmtPDFList(){
        File currentDir = new File("/Users/pradeepkumar/Desktop/statement/monthly_stmt_pdf/"); // current directory
        return monthlyStmtService.getPDFListFromDrive(currentDir);
    }
}
