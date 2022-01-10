package com.example.coloringpage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
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
        BufferedImage blurredImage = blurImage(invertedImage);
        BufferedImage finalImage = dodgeAndMerge(blurredImage, grayImage);
        return finalImage;
    }

    /**
     *
     * @param image The image to be converted to Grayscale
     * @return A BufferedImage that is the Grayscale version of the given image
     */
    public BufferedImage toGrayscaleImage(BufferedImage image)
    {
        BufferedImage grayImage = clone(image);
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                Color originalColor = new Color(grayImage.getRGB(x, y)); //RGB value of pixel at location (x, y)
                int red = (int)(originalColor.getRed() * 0.299);
                int green = (int)(originalColor.getGreen() * 0.587);
                int blue = (int)(originalColor.getBlue() * 0.114);
                int gray = red + green + blue;
                Color newColor = new Color(gray, gray, gray);
                grayImage.setRGB(x, y, newColor.getRGB()); //sets the RGB of pixel (x, y) to its new grayscale color
            }
        }
        return grayImage;
    }

    /**
     *
     * @param image The image to be inverted
     * @return A BufferedImage that is the inverted version of the given image
     * An inverted image is also known as the negative of the image
     * This is done by finding how far away the R, G and B values of each pixel are from 255
     * eg: the negative of a pixel with RGB value of (200, 5, 67) is (55, 250, 188)
     */
    public BufferedImage invertImage(BufferedImage image)
    {
        BufferedImage invertedImage = clone(image);
        int width = invertedImage.getWidth();
        int height = invertedImage.getHeight();
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                Color color = new Color(invertedImage.getRGB(x, y));
                color = new Color(RGB_MAX - color.getRed(),
                                RGB_MAX - color.getGreen(),
                                RGB_MAX - color.getBlue());
                invertedImage.setRGB(x, y, color.getRGB());
            }
        }
        return invertedImage;
    }

    /**
     *
     * @param image The image to be blurred
     * @return A BufferedImage that is the blurred version of the given image
     * This is done by dulling the picture using the convolveOp class
     */
    public BufferedImage blurImage(BufferedImage image)
    {
        BufferedImage blurredImage = clone(image);
        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        blurredImage = op.filter(blurredImage, null);
        return blurredImage;
    }

    /**
     *
     * @param blurredImage that will be added and divided by the
     * @param grayImage
     * @return A BufferedImage that has sharpened the outlines and white
     * background to set up the final coloring page look that is desired
     */
    public BufferedImage dodgeAndMerge(BufferedImage blurredImage, BufferedImage grayImage)
    {
        BufferedImage dividedImage = clone(blurredImage);
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                if (grayImage.getRGB(x,y) == 0)
                {
                    grayImage.setRGB(x,y,1);
                }
                dividedImage.setRGB(x, y, (blurredImage.getRGB(x,y)+grayImage.getRGB(x,y))/grayImage.getRGB(x,y));
            }
        }
        return dividedImage;
    }

    /**
     * https://bytenota.com/java-cloning-a-bufferedimage-object/
     * @param image Image to be cloned
     * @return BufferedImage clone
     */
    private static BufferedImage clone(BufferedImage image) {
        ColorModel colorModel = image.getColorModel();
        WritableRaster raster = image.copyData(null);
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }
}
