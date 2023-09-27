package com.galaxy.repository;

import com.galaxy.entity.MonthlyStmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyStmtRepository extends JpaRepository<MonthlyStmt, Integer> {
    @Query(value = "select * from NICB_DB.monthly_statement where account_id=:AccountId",nativeQuery = true)
    public MonthlyStmt findCustomerDtlsByAccountId(@Param("AccountId") Integer AccountId);

    @Query(value = "select account_id from NICB_DB.Customer_pdf_id where free_text_id=:freeTextId",nativeQuery = true)
    public Integer findAccountIdByFreeTextId(@Param("freeTextId") Integer freeTextId);
}
