package ru.clevertec.ilkevich.receipt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * An object representing the DiscountCard
 *
 * @version 1.0
 * @author Ilkevich Anastasiya
 */
@Data
@Entity
@Table(name="discount_card")
@NoArgsConstructor
public class DiscountCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_discount_card")
    private Long id;
    @Column (name = "number_card")
    private Long cardNumber;
    @Column (name = "discount")
    private byte discount;
   // private Long ransomAmount;

    @JsonIgnore
    @OneToMany(mappedBy="discountCard", cascade = CascadeType.DETACH)
    private Set<CashReceipt> cashReceiptSet;
}

