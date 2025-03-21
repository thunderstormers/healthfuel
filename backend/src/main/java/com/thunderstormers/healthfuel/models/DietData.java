package com.thunderstormers.healthfuel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DietData {

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Response {
        public String meal;
        public String description;
        public float calories;
        public float protein;
        public float carbs;
        public float fat;

        @Override
        public String toString() {
            return meal + " (" + calories + " kcal, " + protein + "g protein)";
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DietTag {
        public String tag;
        public List<String> patterns;
        public List<Response> responses;

        @Override
        public String toString() {
            return "Tag: " + tag + "\nPatterns: " + patterns + "\nResponses: " + responses;
        }
    }

    public List<DietTag> data;
}
