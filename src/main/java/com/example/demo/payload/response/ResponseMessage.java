package com.example.demo.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // client tarafına JSON gitmeli, burada JSON gideceğini garanti etmiş oluyoruz. Bunu eklemezsek response esnasında hata alırız.
@Builder(toBuilder = true)
public class ResponseMessage<T> {

    private T object;
    private String message;
    private HttpStatus httpStatus;
}
