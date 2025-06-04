package com.example.ecommerce.serviceImpls;
import com.example.ecommerce.entities.Card;
import com.example.ecommerce.entities.CardItem;
import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.*;
import com.example.ecommerce.repository.CardItemRepo;
import com.example.ecommerce.repository.CardRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.repository.UserRepo;
import com.example.ecommerce.services.CardService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private final ProductRepo productRepo;
    private final CardRepo cardRepo;
    private final CardItemRepo cardItemRepo;
    private final UserRepo userRepo;

    public CardServiceImpl(ProductRepo productRepo, CardRepo cardRepo, CardItemRepo cardItemRepo, UserRepo userRepo) {
        this.productRepo = productRepo;
        this.cardRepo = cardRepo;
        this.cardItemRepo = cardItemRepo;
        this.userRepo = userRepo;
    }
    @Transactional
    @Override
    public CartResponse addToCard(List<CartItemRequest> cartItems, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID must be provided.");
        }

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        Card card = cardRepo.findByUser(user);
        if (card == null) {
            card = new Card();
            card.setUser(user);
            card = cardRepo.save(card);
        }

        for (CartItemRequest item : cartItems) {
            Long productId = item.getProductId();
            if (productId == null) {
                throw new IllegalArgumentException("Product ID cannot be null");
            }

            ProductEntity product = productRepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

            Optional<CardItem> existingItem = cardItemRepo.findByCardAndProduct(card, product);
            if (existingItem.isPresent()) {
                CardItem cardItem = existingItem.get();
                cardItem.setQuantity(cardItem.getQuantity() + item.getQuantity());
                cardItem.setPrice(BigDecimal.valueOf(cardItem.getQuantity() * product.getPrice()));
                cardItemRepo.save(cardItem);
            } else {
                CardItem newItem = new CardItem();
                newItem.setCard(card);
                newItem.setProduct(product);
                newItem.setQuantity(item.getQuantity());
                newItem.setPrice(BigDecimal.valueOf(item.getQuantity() * product.getPrice()));
                cardItemRepo.save(newItem);
            }
        }

        // Build response
        List<CardItem> allItems = cardItemRepo.findByCard(card);
        List<CartItemResponse> itemResponses = allItems.stream().map(item -> {
            CartItemResponse response = new CartItemResponse();
            response.setProductId(item.getProduct().getProductId());
            response.setProductName(item.getProduct().getName());
            response.setProductImages(item.getProduct().getImageUrls());
            response.setQuantity(item.getQuantity());
            response.setUnitPrice(BigDecimal.valueOf(item.getProduct().getPrice()));
            response.setTotalPrice(item.getPrice());
            return response;
        }).toList();

        CartResponse response = new CartResponse();
        response.setCardId(card.getCardId());
        response.setUserId(user.getId());
        response.setItems(itemResponses);

        return response;
    }


    @Transactional
    public List<CardDTO> getAllCard() {
        List<Card> cards = cardRepo.findAll();
        return cards.stream().map(card -> {
            CardDTO dto = new CardDTO();
            dto.setCardId(card.getCardId());
            dto.setCartItems(card.getCartItems().stream().map(item -> {
                CardItemDTO itemDTO = new CardItemDTO();
                itemDTO.setCardItemId(item.getCardItemId());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());
                return itemDTO;
            }).toList());
            return dto;
        }).toList();
    }
    @Transactional
    public CardDTO getCard(Long id) {
        // Fetch the card by ID
        Card card = cardRepo.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Card not found for user: " + id));

        // Convert CardItems to DTO
        List<CardItemDTO> cardItems = card.getCartItems().stream()
                .map(item -> {
                    String name = item.getProduct().getName();
                    int quantity = item.getQuantity();
                    BigDecimal unitPrice = BigDecimal.valueOf(item.getProduct().getPrice());
                    BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
                    double totalPriceAsDouble = totalPrice.doubleValue();

                    List<String> productImages = item.getProduct().getImageUrls(); // Assuming this is how you store images
                    Long productId = item.getProduct().getProductId();
                    Long userId = item.getCard().getUser().getId();
                    return new CardItemDTO(
                            item.getCardItemId(),
                            userId,
                            productId,
                            name,
                            quantity,
                            unitPrice,
                            totalPriceAsDouble,
                            productImages
                    );
                })
                .collect(Collectors.toList());

        return new CardDTO(card.getCardId(), cardItems);
    }
    @Transactional
    public void deleteCard(Long productId,Long id) {
        Card card = cardRepo.findByUserId(id).orElseThrow(()-> new RuntimeException("Card not found"));

        CardItem cartItem = card.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in Card"));
        card.getCartItems().remove(cartItem);
        cardItemRepo.delete(cartItem);
        cardRepo.save(card);
    }
//    public Card getOrCreateCartForUser(User user) {
//        Card existingCart = cardRepo.findByUser(user);
//
//        if (existingCart != null) {
//            return existingCart;
//        } else {
//            Card newCart = new Card();
//            newCart.setUser(user);
//            newCart.setCartItems(new ArrayList<>());
//            return cardRepo.save(newCart);
//        }
//    }


}
