package minho.review.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public String ObjectToJson(Object target) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(target);
    }

    public String getRamdomPassword(int len){
        char [] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int idx = (int) (charSet.length * Math.random());
            sb.append(charSet[idx]); }
        return sb.toString();
    }
}
