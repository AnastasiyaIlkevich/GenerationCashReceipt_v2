package ru.clevertec.ilkevich.receipt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

/**
 * An object representing the CashReceipt
 *
 * @version 1.0
 * @author Ilkevich Anastasiya
 */
@Data
@Entity
@Table(name="cash_receipt")
@NoArgsConstructor
public class CashReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_cash_receipt")
    private Long checkNumber;
    @Column (name = "date_creation")
    private Timestamp dateCreation;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "receipt_product",
            joinColumns = @JoinColumn(name = "id_cash_receipt"),
            inverseJoinColumns = @JoinColumn(name = "id_product")
    )
    private Set<Product> setProduct;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_shop")
    private ShopInfo shopInfo;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_discount_card")
    private DiscountCard discountCard;
}

