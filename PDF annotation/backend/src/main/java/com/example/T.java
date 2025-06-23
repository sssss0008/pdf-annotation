package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class T {

    @Autowired
    private Z z;

    @Test
    public void testAnnotatePdfInvalidRequest() {
        W request = new W();
        assertThrows(IllegalArgumentException.class, () -> z.annotatePdf(request));
    }
}