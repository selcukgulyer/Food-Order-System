package com.example.demo.service;

import com.example.demo.controller.request.CreateCardRequest;
import com.example.demo.controller.request.UpdateCardRequest;
import com.example.demo.controller.response.CardResponse;
import com.example.demo.entities.CardDetails;
import com.example.demo.entities.User;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.repository.CardDetailRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardDetailServiceTest {

    private  CardDetailRepository cardDetailRepository;
    private UserRepository userRepository;
    private  UserService userService;
    private CardDetailService cardDetailService;
    private ObjectMapper objectMapper= new ObjectMapper();

    @BeforeEach
    void setUp(){
        cardDetailRepository = Mockito.mock(CardDetailRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        userService = Mockito.mock(UserService.class);
        cardDetailService = new CardDetailImpl(cardDetailRepository,userService);
    }

    @Test
    void createCardDetail() {
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);
        CreateCardRequest request = new CreateCardRequest();
        request.setCardNumber("2423424");
        request.setCvc("123");
        request.setIban("TR12312312313");
        request.setLastUsageDate("12-12-2027");
        request.setUser(user);
        CardResponse cardResponse = new CardResponse("TR12312312313","2423424","123","12-12-2027");
        CardDetails card = objectMapper.convertValue(request, CardDetails.class);

        CardDetails savedCard = new CardDetails();
        savedCard.setId(1);
        savedCard.setIban("TR12312312313");
        savedCard.setCvc("123");
        savedCard.setCardNumber("2423424");
        savedCard.setLastUsageDate("12-12-2027");
        savedCard.setUser(user);

        Mockito.when(cardDetailRepository.save(card)).thenReturn(savedCard);

        CardResponse response = cardDetailService.createCardDetail(request);

        assertEquals(response,cardResponse);

        Mockito.verify(cardDetailRepository).save(card);



    }


    @Test
    void deleteCardDetail() {
        int id = 1;
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);
        CardDetails card = new CardDetails();
        card.setId(1);
        card.setIban("TR12312312313");
        card.setCvc("123");
        card.setCardNumber("2423424");
        card.setLastUsageDate("12-12-2027");
        card.setUser(user);

        Mockito.when(cardDetailRepository.findById(id)).thenReturn(Optional.of(card));

        cardDetailService.deleteCardDetail(id);
        Mockito.verify(cardDetailRepository).findById(id);
        Mockito.verify(cardDetailRepository).deleteById(id);

    }

    @Test
    void deleteCardDetail_whenCardIdDoesNotExist() {
        int id = 1;

        Mockito.when(cardDetailRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class,() ->cardDetailService.deleteCardDetail(id));

        Mockito.verify(cardDetailRepository).findById(id);
        Mockito.verifyNoMoreInteractions(cardDetailRepository);

    }
    @Test
    void updateCardDetail() {
        int id =1;
        UpdateCardRequest request = new UpdateCardRequest();
        request.setCardNumber("123123");
        request.setCvc("345");
        request.setIban("TR12121212");
        request.setLastUsageDate("12-12-2023");
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);

        CardDetails card = new CardDetails();
        card.setId(1);
        card.setIban("TR12312312313");
        card.setCvc("123");
        card.setCardNumber("2423424");
        card.setLastUsageDate("12-12-2027");
        card.setUser(user);
        CardDetails updateCard = new CardDetails(1, request.getIban(), request.getCardNumber(), request.getCvc(), request.getLastUsageDate(),user);
        CardDetails savedCard = new CardDetails(1,"TR12121212","123123","345","12-12-2023",user);
        CardResponse cardResponse = new CardResponse("TR12121212","123123","345","12-12-2023");
        Mockito.when(cardDetailRepository.findById(id)).thenReturn(Optional.of(card));
        Mockito.when(cardDetailRepository.save(updateCard)).thenReturn(savedCard);

        CardResponse response = cardDetailService.updateCardDetail(id,request);

        assertEquals(cardResponse,response);

        Mockito.verify(cardDetailRepository).findById(id);
        Mockito.verify(cardDetailRepository).save(updateCard);
    }

    @Test
    void updateCardDetail_whenCardIdDoesNotExists_itShouldThrowsCardNotFoundException() {
        int id =1;
        UpdateCardRequest request = new UpdateCardRequest();
        request.setCardNumber("123123");
        request.setCvc("345");
        request.setIban("TR12121212");
        request.setLastUsageDate("12-12-2023");

        Mockito.when(cardDetailRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class,() ->cardDetailService.updateCardDetail(id,request));

        Mockito.verify(cardDetailRepository).findById(id);
        Mockito.verifyNoMoreInteractions(cardDetailRepository);
    }
    @Test
    void getByIdCard(){
        int id = 1;
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);
        CardDetails card = new CardDetails();
        card.setId(1);
        card.setIban("TR12312312313");
        card.setCvc("123");
        card.setCardNumber("2423424");
        card.setLastUsageDate("12-12-2027");
        card.setUser(user);


        Mockito.when(cardDetailRepository.findById(id)).thenReturn(Optional.of(card));

        CardDetails response = cardDetailService.getByIdCard(id);

        assertEquals(response,card);

        Mockito.verify(cardDetailRepository).findById(id);

    }

    @Test
    void getByIdCard_whenCardIdDoesNotExist(){
        int id = 1;

        Mockito.when(cardDetailRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class,() -> cardDetailService.getByIdCard(id));

        Mockito.verify(cardDetailRepository).findById(id);
        Mockito.verifyNoMoreInteractions(cardDetailRepository);

    }
}