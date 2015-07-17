package com.example.annotation.annotation;

import com.example.annotation.util.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by automation on 7/17/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreCondition {
    Condition[] preConditions() default {Condition.NONE};
    String login();
    String password();
}
