package com.example.ecommerce.controller;

import com.example.ecommerce.entities.Card;
import com.example.ecommerce.entities.CardItem;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.*;
import com.example.ecommerce.repository.UserRepo;
import com.example.ecommerce.services.CardService;
import com.example.ecommerce.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final UserRepo userRepo;
    @PostMapping("/add_card")
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddToCardRequest request) {
        if (request.getId() == null) {
            throw new IllegalArgumentException("User ID must be provided to add product to cart.");
        }
        if (request.getCartItems() == null || request.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart must have at least one product.");
        }

        CartResponse card = cardService.addToCard(request.getCartItems(), request.getId());
        return ResponseEntity.ok(card);
    }




    @GetMapping("/get_all_card")
    public ResponseEntity<List<CardDTO>> getAllCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @GetMapping("/get_card")
    public ResponseEntity<CardDTO> getCard(@AuthenticationPrincipal User user) {
        // Get the card details for the authenticated user
        CardDTO cardDTO = cardService.getCard(user.getId());

        // Return the card details with a 200 OK status
        return ResponseEntity.ok(cardDTO);
    }

    @DeleteMapping("/delete_card")
    public ResponseEntity<String> deleteCart(@RequestParam Long id,@RequestParam Long productId) {
        cardService.deleteCard(id,productId);
        return ResponseEntity.ok("Item deleted successfull from Card: " );
    }
}
