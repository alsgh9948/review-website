package minho.review.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public String ObjectToJson(Object target) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(target);
    }
}
