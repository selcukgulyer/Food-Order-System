package com.example.demo.service;

import com.example.demo.controller.request.CreateCardRequest;
import com.example.demo.controller.request.UpdateCardRequest;
import com.example.demo.controller.response.CardResponse;
import com.example.demo.entities.CardDetails;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.CardDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardDetailImpl implements CardDetailService {
    private final CardDetailRepository cardDetailRepository;
    private final UserService userService;


    @Override
    public CardResponse createCardDetail(CreateCardRequest request) {
        userService.getByUser(request.getUser().getId());
        CardDetails card = new CardDetails(
                request.getIban(),
                request.getCardNumber(),
                request.getCvc(),
                request.getLastUsageDate(),
                request.getUser()
        );
        cardDetailRepository.save(card);
        return CardResponse.from(card);
    }

    @Override
    public void deleteCardDetail(int id) {
        getByIdCard(id);
        cardDetailRepository.deleteById(id);
    }

    @Override
    public CardResponse updateCardDetail(int id, UpdateCardRequest request) {
        CardDetails byIdCard = getByIdCard(id);
        CardDetails cardDetails = new CardDetails(
                byIdCard.getId(),
                request.getIban(),
                request.getCardNumber(),
                request.getCvc(),
                request.getLastUsageDate(),
                byIdCard.getUser()
        );
        cardDetailRepository.save(cardDetails);
        return CardResponse.from(cardDetails);
    }

    public CardDetails getByIdCard(int id) {
        Optional<CardDetails> cardDetailsOptional = cardDetailRepository.findById(id);
        if (cardDetailsOptional.isPresent()) {
            return cardDetailsOptional.get();
        }
        throw new AsgDataNotFoundException(ExceptionType.CARD_DATA_NOT_FOUND);
    }
}
