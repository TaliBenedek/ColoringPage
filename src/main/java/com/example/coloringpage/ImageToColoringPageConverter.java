package com.example.coloringpage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToColoringPageConverter
{
    private final int RGB_MAX = 255;

    public BufferedImage getColoringPage(File inputFile) throws IOException
    {
        BufferedImage image = ImageIO.read(inputFile);
        BufferedImage grayImage = toGrayscaleImage(image);
        BufferedImage invertedImage = invertImage(grayImage);
        return null;
    }
    public BufferedImage toGrayscaleImage(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        for(int x = 0; x < height; x++)
        {
            for(int y = 0; y < width; y++)
            {
                Color originalColor = new Color(image.getRGB(x, y));
                int red = (int)(originalColor.getRed() * 0.299);
                int green = (int)(originalColor.getGreen() * 0.587);
                int blue = (int)(originalColor.getBlue() * 0.114);
                int gray = red + green + blue;
                Color newColor = new Color(gray, gray, gray);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
        return image;
    }

    public BufferedImage invertImage(BufferedImage image)
    {
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                Color color = new Color(image.getRGB(x, y));
                color = new Color(RGB_MAX - color.getRed(),
                                RGB_MAX - color.getGreen(),
                                RGB_MAX - color.getBlue());
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }
}
