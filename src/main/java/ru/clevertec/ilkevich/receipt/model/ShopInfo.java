package ru.clevertec.ilkevich.receipt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

/**
 * An object representing a ShopInfo that contains the information.
 *
 * @version 1.0
 * @author Ilkevich Anastasiya
 */
@Data
@Entity
@Table(name="shop")
@NoArgsConstructor
public class ShopInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_shop")
    private Long id;
    @Column (name = "shop_name")
    private String shopName;
    @Column (name = "address")
    private String address;
    @Column (name = "phoneNumber")
    private String phoneNumber;
@JsonIgnore
    @OneToMany(mappedBy="shopInfo", cascade = CascadeType.DETACH)
    private Set<CashReceipt> cashReceiptSet;

}
