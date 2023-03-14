package pl.neverendingcode.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonObjectMapper {

    private final ObjectMapper objectMapper;

    public String toJSON(Object object) {
        objectMapper.registerModule(new JavaTimeModule());
        try (StringWriter result = new StringWriter()) {
            objectMapper.writeValue(result, object);
            return result.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    public <T> T fromJSON(String json, Class<T> object) {
        T result;
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JavaType javaType = typeFactory.constructType(object);
            result = objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

}