package com.example.coloringpage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageToColoringPageConverter
{
    public BufferedImage toGrayscaleImage(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                Color originalColor = new Color(image.getRGB(j, i));
                int red = (int)(originalColor.getRed() * 0.299);
                int green = (int)(originalColor.getGreen() * 0.587);
                int blue = (int)(originalColor.getBlue() * 0.114);
                int gray = red + green + blue;
                Color newColor = new Color(gray, gray, gray);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }
}
