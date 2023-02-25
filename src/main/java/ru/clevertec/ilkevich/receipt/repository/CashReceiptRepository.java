package ru.clevertec.ilkevich.receipt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;

import java.sql.Timestamp;

@Repository
public interface CashReceiptRepository extends JpaRepository<CashReceipt, Long> {

    CashReceipt findByDateCreation(Timestamp date);
}
