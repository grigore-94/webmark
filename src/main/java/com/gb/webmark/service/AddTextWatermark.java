package com.gb.webmark.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class AddTextWatermark {
    public byte[] addTextWatermark(String text, MultipartFile source, Float opacity) throws IOException {
        BufferedImage image = ImageIO.read(source.getInputStream());

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(source.getContentType()) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        w.setComposite(alphaChannel);
        w.setColor(Color.GRAY);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, image.getWidth() / 12));
        FontMetrics fontMetrics = w.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, w);

        // calculate center of the image
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;

        // add text overlay to the image
        w.drawString(text, (float) (centerX-(rect.getWidth()/2)), (float) (centerY-(rect.getHeight()/2)));

        w.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(watermarked, "jpg",  baos);


        return  baos.toByteArray();

    }

}
