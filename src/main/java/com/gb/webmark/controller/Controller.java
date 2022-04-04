package com.gb.webmark.controller;

import com.gb.webmark.service.AddImageWatermark;
import com.gb.webmark.service.AddTextWatermark;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class Controller {

    private final AddTextWatermark addTextWatermark;
    private final AddImageWatermark addImageWatermark;
    @GetMapping("/hello")
    public String hello() throws IOException {
        return "hello!";
    }

    @PostMapping(value = "mark-image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] markImage(@RequestParam("original") MultipartFile original, @RequestParam("watermark") MultipartFile watermark,
                            @RequestParam(value = "opacity", required = false) Float opacity) throws IOException {

        if (opacity == null) {
            opacity = 0.4f;
        }

        if (opacity < 0 || opacity > 1) {
            opacity = 1f;
        }

        return addImageWatermark.addImageWatermark(original, watermark, opacity);
    }

    @PostMapping(value = "mark-text", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] markText(@RequestParam("original") MultipartFile original, @RequestParam("text") String text,
                           @RequestParam(value = "opacity", required = false) Float opacity) throws IOException {
        if (opacity == null) {
            opacity = 0.4f;
        }

        if (opacity < 0 || opacity > 1) {
            opacity = 1f;
        }

        return addTextWatermark.addTextWatermark(text, original, opacity);
    }
}
