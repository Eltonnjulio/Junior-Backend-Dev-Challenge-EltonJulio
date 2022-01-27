package mz.co.blog.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Converter
public class JsonObjectConverter implements AttributeConverter<List<String>, String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<String> jsonAttributes) {

        String jsonAttrs = null;
        try {
            jsonAttrs = objectMapper.writeValueAsString(jsonAttributes);
        } catch (final JsonProcessingException e) {
        }

        return jsonAttrs;
    }

    @Override
    public List<String> convertToEntityAttribute(String customerInfoJSON) {

        List<String> jsonAttributes= null;
        try {
            jsonAttributes = objectMapper.readValue(customerInfoJSON, List.class);
        } catch (final IOException e) {
        }

        return jsonAttributes;
    }

}