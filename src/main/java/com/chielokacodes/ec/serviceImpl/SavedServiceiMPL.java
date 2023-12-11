package com.chielokacodes.ec.serviceImpl;

import com.chielokacodes.ec.entity.Product;
import com.chielokacodes.ec.repository.SavedProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavedServiceiMPL {

    private SavedProductRepo savedProductRepo;

    @Autowired
    public SavedServiceiMPL(SavedProductRepo savedProductRepo) {
        this.savedProductRepo = savedProductRepo;
    }

}
