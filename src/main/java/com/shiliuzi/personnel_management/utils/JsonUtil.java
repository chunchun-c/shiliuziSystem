package com.shiliuzi.personnel_management.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

//解析json工具
@Component
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    //解析json为map
    public static Map<String, Object> jsonToMap(String jsonString) {
        try {
            // 将 JSON 字符串转换为 Map
            return objectMapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    //解析json为jsonNode对象
    public static JsonNode jsonToJsonNode(String jsonString) {
        try {
            // 将 JSON 字符串转换为 JsonNode
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
