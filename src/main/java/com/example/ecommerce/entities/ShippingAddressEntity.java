package com.example.ecommerce.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shipping_addresses")
public class ShippingAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shippingAdress_Id")
    public Long shippingAdressesId;
    @Column(name = "recipient_name",nullable = false)
    public String recipientName;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "state",nullable = false)
    private String state;
    @Column(name = "postal_code",nullable = false)
    private String postalCode;
    @Column(name = "country")
    private String country;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @OneToOne(mappedBy = "shippingAddresses", cascade = CascadeType.ALL)
    private OrderEntity order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
