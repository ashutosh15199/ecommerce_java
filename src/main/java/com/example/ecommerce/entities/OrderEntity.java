package com.example.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long orderId;
    @Column(name = "order_number")
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // e.g., PENDING, PAID, SHIPPED, CANCELLED

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id",referencedColumnName = "shippingAdress_Id")
    private ShippingAddressEntity shippingAddresses;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "is_delete")
    private Boolean isDeleted=false;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
