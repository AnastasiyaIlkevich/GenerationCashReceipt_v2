package ru.clevertec.ilkevich.receipt.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * An object representing the Product
 *
 * @version 1.0
 * @author Ilkevich Anastasiya
 */

@Data
@Entity
@Table(name="product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_product")
    private Long id;
    @Column (name = "product_name")
    private String name;
    @Column (name = "price")
    private Double price;
//    @Transient
//    private Long count;
@JsonIgnore
@ManyToMany(mappedBy = "setProduct")
    private Set<CashReceipt> cashReceiptSet;


}
