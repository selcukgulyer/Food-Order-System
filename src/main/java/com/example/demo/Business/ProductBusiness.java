package com.example.demo.Business;

import com.example.demo.event.CreatedOrderEvent;

public interface ProductBusiness {
    void controlStock(CreatedOrderEvent event);

    void updateOrder(CreatedOrderEvent event);
}
