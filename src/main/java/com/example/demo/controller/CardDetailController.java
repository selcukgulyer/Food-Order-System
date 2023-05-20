package com.example.demo.controller;

import com.example.demo.controller.request.CreateCardRequest;
import com.example.demo.controller.request.UpdateCardRequest;
import com.example.demo.controller.response.CardResponse;
import com.example.demo.service.CardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/card")
@RestController
@RequiredArgsConstructor
public class CardDetailController {

    private final CardDetailService cardDetailService;

    @PostMapping("/add")
    public CardResponse createCardDetail(@RequestBody CreateCardRequest request) {
        return cardDetailService.createCardDetail(request);

    }
    @DeleteMapping("/{id}")
    public CardResponse deleteCardDetail(@PathVariable int id){
        return cardDetailService.deleteCardDetail(id);
    }

    @PutMapping("/{id}")
    public CardResponse updateCardDetail(@PathVariable int id,@RequestBody UpdateCardRequest request){
        return cardDetailService.updateCardDetail(id,request);
    }

    // Todo : istege göre card bilgisi getirme yapılabilir
}
