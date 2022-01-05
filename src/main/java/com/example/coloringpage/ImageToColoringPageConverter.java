package com.example.coloringpage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToColoringPageConverter
{
    private final int RGB_MAX = 255;

    /**
     *
     * @param inputFile File containing the image to be converted
     * @return The coloring page version of the given file as a BufferedImage
     * @throws IOException
     */
    public BufferedImage getColoringPage(File inputFile) throws IOException
    {
        BufferedImage image = ImageIO.read(inputFile);
        BufferedImage grayImage = toGrayscaleImage(image);
        BufferedImage invertedImage = invertImage(grayImage);
        return null;
    }

    /**
     *
     * @param image The image to be converted to Grayscale
     * @return A BufferedImage that is the Grayscale version of the given image
     */
    private BufferedImage toGrayscaleImage(BufferedImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        for(int x = 0; x < height; x++)
        {
            for(int y = 0; y < width; y++)
            {
                Color originalColor = new Color(image.getRGB(x, y)); //RGB value of pixel at location (x, y)
                int red = (int)(originalColor.getRed() * 0.299);
                int green = (int)(originalColor.getGreen() * 0.587);
                int blue = (int)(originalColor.getBlue() * 0.114);
                int gray = red + green + blue;
                Color newColor = new Color(gray, gray, gray);
                image.setRGB(x, y, newColor.getRGB()); //sets the RGB of pixel (x, y) to its new grayscale color
            }
        }
        return image;
    }

    /**
     *
     * @param image The image to be inverted
     * @return A BufferedImage that is the inverted version of the given image
     * An inverted image is also known as the negative of the image
     * This is done by finding how far away the R, G and B values of each pixel are from 255
     * eg: the negative of a pixel with RGB value of (200, 5, 67) is (55, 250, 188)
     */
    private BufferedImage invertImage(BufferedImage image)
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

    private BufferedImage blurImage(BufferedImage image)
    {
        //TODO code
        return null;
    }

    private BufferedImage dodgeAndMerge(BufferedImage blurredImage, BufferedImage grayImage)
    {
        //TODO code
        return null;
    }
}