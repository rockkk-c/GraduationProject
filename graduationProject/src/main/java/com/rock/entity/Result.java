package com.rock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private Integer code;
    private String message;



    public static Result ok(String message) {
        return Result.builder().code(0).message(message).build();
    }



    public static Result error(String message) {
        return Result.builder().code(-1).message(message).build();
    }

}
