package com.example.demo.controller;

import com.example.demo.controller.request.CreateCardRequest;
import com.example.demo.controller.request.UpdateCardRequest;
import com.example.demo.controller.response.CardResponse;
import com.example.demo.service.CardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/card")
@RestController
@RequiredArgsConstructor
public class CardDetailController {

    private final CardDetailService cardDetailService;

    @PostMapping("/add")
    public ResponseEntity<CardResponse> createCardDetail(@RequestBody @Valid CreateCardRequest request) {
        return new ResponseEntity<>(cardDetailService.createCardDetail(request),HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardDetail(@PathVariable int id){
        cardDetailService.deleteCardDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardResponse> updateCardDetail(@PathVariable int id,@RequestBody UpdateCardRequest request){
        return new ResponseEntity<>(cardDetailService.updateCardDetail(id,request),HttpStatus.OK) ;
    }

    // Todo : istege göre card bilgisi getirme yapılabilir
}
