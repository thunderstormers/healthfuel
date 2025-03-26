package com.thunderstormers.healthfuel.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.thunderstormers.healthfuel.ai.DietRecommendationModel;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequest;
import com.thunderstormers.healthfuel.models.UserDietDetailsRequestBatch;

import ai.onnxruntime.OrtException;

@Slf4j
@Service
@Validated
public class DietRecommenderService {

    private DietRecommendationModel model;

    public DietRecommenderService(DietRecommendationModel model) throws Exception {
        this.model = model;
    }

    public String predict(@Valid UserDietDetailsRequest request) {
        try {
            return model.predict(UserDietDetailsRequestBatch.of(request))[0];
        } catch (OrtException e) {
            throw new IllegalArgumentException("Error processing request: ", e);
        }
    }

}
