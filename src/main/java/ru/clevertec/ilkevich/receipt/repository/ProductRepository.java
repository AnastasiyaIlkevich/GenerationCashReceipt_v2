package ru.clevertec.ilkevich.receipt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ilkevich.receipt.model.Product;

/**
 * The ProductRepository interface provides basic operations
 * for searching, saving, deleting data (CRUD operations)
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}