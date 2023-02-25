package ru.clevertec.ilkevich.receipt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;

/**
 * The ShopInfoRepository interface provides basic operations
 * for searching, saving, deleting data (CRUD operations)
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */

@Repository
public interface ShopInfoRepository extends JpaRepository<ShopInfo, Long> {
}
