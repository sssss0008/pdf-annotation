package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Y {

    @Autowired
    private Z z;

    @PostMapping(value = "/annotate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/pdf")
    public ResponseEntity<byte[]> annotatePdf(@RequestBody W request) throws Exception {
        byte[] annotatedPdf = z.annotatePdf(request);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(annotatedPdf);
    }

    @GetMapping(value = "/annotated", produces = "application/pdf")
    public ResponseEntity<byte[]> getAnnotatedPdf() throws Exception {
        byte[] annotatedPdf = z.getLastAnnotatedPdf();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(annotatedPdf);
    }
}
    
}
