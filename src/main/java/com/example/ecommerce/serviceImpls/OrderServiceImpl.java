package com.example.ecommerce.serviceImpls;
import com.example.ecommerce.entities.*;
import com.example.ecommerce.modals.*;
import com.example.ecommerce.repository.OrderRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.repository.ShippingAddressRepo;
import com.example.ecommerce.repository.UserRepo;
import com.example.ecommerce.services.CardItemService;
import com.example.ecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ShippingAddressRepo shippingAddressRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    CardItemService cardItemService;
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User user = userRepo.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found!"));

        // Build shipping address (do NOT save separately)
        ShippingAddressEntity shippingAddress = ShippingAddressEntity.builder()
                .recipientName(orderRequest.getShippingAddresses().getRecipientName())
                .street(orderRequest.getShippingAddresses().getStreet())
                .city(orderRequest.getShippingAddresses().getCity())
                .state(orderRequest.getShippingAddresses().getState())
                .postalCode(orderRequest.getShippingAddresses().getPostalCode())
                .country(orderRequest.getShippingAddresses().getCountry())
                .phoneNumber(orderRequest.getShippingAddresses().getPhoneNumber())
                .user(user)
                .build();

        List<OrderItemEntity> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (OrderItemEntity items : orderRequest.getOrderItems()) {
            ProductEntity product = productRepo.findById(items.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found"));

            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .product(product)
                    .quantity(items.getQuantity())
                    .orderItemPrice(BigDecimal.valueOf(product.getPrice()))
                    .build();

            totalAmount += product.getPrice() * items.getQuantity();
            orderItems.add(orderItem);
        }

        OrderEntity order = OrderEntity.builder()
                .orderNumber(orderRequest.getOrderNumber())
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .user(user)
                .shippingAddresses(shippingAddress)
                .orderItems(orderItems)
                .orderDate(LocalDateTime.now())
                .isDeleted(false)
                .build();

        shippingAddress.setOrder(order); // set both sides

        for (OrderItemEntity item : orderItems) {
            item.setOrder(order);
        }

        OrderEntity saveOrder = orderRepo.save(order);

        return OrderResponse.builder()
                .orderId(saveOrder.getOrderId())
                .userId(saveOrder.getUser().getId())
                .orderDate(saveOrder.getCreatedAt())
                .status(String.valueOf(saveOrder.getStatus()))
                .shippingAddresses(mapToShippingAddressDTO(saveOrder.getShippingAddresses()))
                .orderItems(saveOrder.getOrderItems().stream()
                        .map(this::mapToOrderItemDTO)
                        .collect(Collectors.toList()))
                .totalAmount(saveOrder.getTotalAmount())
                .message("Order created successfully")
                .build();
    }

    @Transactional
    public CheckoutSummaryResponse getCheckoutSummary(Long id) {
        // 1. Get Cart Items
        List<CardItem> cartItems = cardItemService.getCartItemsByUserId(id);

        // 2. Get Address
        ShippingAddressEntity addressEntity = shippingAddressRepo.findByUser_Id(id)
                .orElseThrow(() -> new RuntimeException("User Not found with the address"));

        // 3. Convert Entity to DTO
        SippingAddressDTO addressDTO = mapToShippingAddressDTO(addressEntity);

        // 4. Calculations
        BigDecimal subtotal = calculateSubtotal(cartItems);
        BigDecimal discount = getApplicableDiscount(id,subtotal);
        BigDecimal shippingCharge = new BigDecimal("50.00");
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.05")); // 5% tax
        BigDecimal totalAmount = subtotal.subtract(discount).add(tax).add(shippingCharge);
        LocalDate estimatedDeliveryDate = LocalDate.now().plusDays(3);

        // 5. Convert CartItems to DTOs
        List<CardItemDTO> cartItemDTOs = cartItems.stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());

        // 6. Return Checkout Summary
        return CheckoutSummaryResponse.builder()
                .orderItems(cartItemDTOs)
                .shippingAddresses(addressDTO)
                .subtotal(subtotal)
                .discount(discount)
                .shippingCharge(shippingCharge)
                .tax(tax)
                .totalAmount(totalAmount)
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .build();
    }



    // Mapper for CartItem to CartItemDTO
    private CardItemDTO convertToCartItemDTO(CardItem item) {
        CardItemDTO dto = new CardItemDTO();
        dto.setProductId(item.getProduct().getProductId());
        dto.setName(item.getProduct().getName());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        return dto;
    }

    private BigDecimal getApplicableDiscount(Long userId, BigDecimal subtotal) {
        BigDecimal discountPercentage = BigDecimal.ZERO;

        // Business logic example
        if (userId != null && userId > 1000) {
            discountPercentage = new BigDecimal("0.10"); // 10%
        }

        return subtotal.multiply(discountPercentage);
    }


    // Calculate subtotal
    private BigDecimal calculateSubtotal(List<CardItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    private AddressDTO convertToAddressDTO(ShippingAddressEntity entity){
        if(entity==null) return null;
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setRecipientName(entity.getRecipientName());
        addressDTO.setStreet(entity.getStreet());
        addressDTO.setCity(entity.getCity());
        addressDTO.setState(entity.getState());
        addressDTO.setPostalCode(entity.getPostalCode());
        addressDTO.setCountry(entity.getCountry());
        addressDTO.setPhoneNumber(entity.getPhoneNumber());
        return addressDTO;
    }

    private SippingAddressDTO mapToShippingAddressDTO(ShippingAddressEntity address){
        return SippingAddressDTO.builder().
                recipientName(address.getRecipientName())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItemEntity order){
        return OrderItemDTO.builder()
                .productId(order.getProduct().getProductId())
                .productName(order.getProduct().getName())
                .price(BigDecimal.valueOf(order.getProduct().getPrice()))
                .quantity(order.getQuantity())
                .build();
    }

    public OrderResponse deleteOrder(Long orderId){
    OrderEntity order = orderRepo.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Order not found"));
    order.setIsDeleted(true);
    OrderEntity deleteOrder = orderRepo.save(order);
    return OrderResponse.builder()
                .orderId(deleteOrder.getOrderId())
                .userId(deleteOrder.getUser().getId())
                .orderDate(deleteOrder.getOrderDate())
                .status(deleteOrder.getStatus().name())
                .totalAmount(deleteOrder.getTotalAmount())
                .isDeleted(deleteOrder.getIsDeleted())
                .shippingAddresses(mapToShippingAddressDTO(deleteOrder.getShippingAddresses()))
                .message("Order soft deleted successfully")
                .build();

    }
}
