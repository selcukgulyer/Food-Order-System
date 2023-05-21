package com.example.demo.service;

import com.example.demo.controller.request.CreateCardRequest;
import com.example.demo.controller.request.UpdateCardRequest;
import com.example.demo.controller.response.CardResponse;

public interface CardDetailService {
    CardResponse createCardDetail(CreateCardRequest request);

    void deleteCardDetail(int id);

    CardResponse updateCardDetail(int id, UpdateCardRequest request);
}
