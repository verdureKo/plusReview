package com.sparta.review.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message<T> {
    private String message;
    private int statusCode;
    private T data;
}