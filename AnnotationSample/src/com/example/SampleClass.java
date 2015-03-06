package com.example;

import com.annotation.proc.CustomAnnotation;

/**
 * Created by nikolai on 02.03.2015.
 */
public class SampleClass {

    @CustomAnnotation(className = "String", type = 1, value = "Hello")
    public void annotationMethod() {
        System.out.println("annotationMethod");
    }
}
