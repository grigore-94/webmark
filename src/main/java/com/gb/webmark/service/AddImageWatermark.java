package com.gb.webmark.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class AddImageWatermark {
    public byte[]  addImageWatermark(MultipartFile original, MultipartFile watermark, Float opacity) throws IOException {
        BufferedImage image = ImageIO.read(original.getInputStream());

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(original.getContentType()) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        w.setComposite(alphaChannel);

        // calculates the coordinate where the String is painted
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;
        BufferedImage overlay = resize(ImageIO.read(watermark.getInputStream()), centerX, centerY);

        // add text watermark to the image
        w.drawImage(overlay, centerX-(overlay.getWidth()/2), centerY-(overlay.getHeight()/2), null);
        w.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(watermarked, "jpg",  baos);


        return  baos.toByteArray();
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
