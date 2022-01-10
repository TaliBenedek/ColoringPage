package com.example.coloringpage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageToColoringPageConverter
{
    private final int RGB_MIN = 0;
    private final int RGB_MAX = 255;
    private final int BLACK_INPUT_LEVEL = 140;
    private final int WHITE_INPUT_LEVEL = 245;
    private final int INPUT_LEVEL_RANGE = WHITE_INPUT_LEVEL - BLACK_INPUT_LEVEL;

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
    private BufferedImage toGrayscaleImage(BufferedImage image)
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
    private BufferedImage invertImage(BufferedImage image)
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
    private BufferedImage blurImage(BufferedImage image)
    {
        BufferedImage blurredImage = clone(image);
        int radius = 11;
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];

        for (int i = 0; i < data.length; i++)
        {
            data[i] = weight;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        blurredImage = op.filter(blurredImage, null);
        return blurredImage;
    }

    /**
     *
     * @param top that will be added and divided by the
     * @param bottom
     * @return A BufferedImage that has sharpened the outlines and white
     * background to set up the final coloring page look that is desired
     */
    private BufferedImage dodgeAndMerge(BufferedImage top, BufferedImage bottom)
    {
        BufferedImage image = clone(top);

        int width = top.getWidth();
        int height = top.getHeight();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int topValue = new Color(top.getRGB(x, y)).getRed();
                int bottomValue = new Color(bottom.getRGB(x, y)).getRed();

                //ensures that the next statement won't divide by zero
                if(bottomValue == RGB_MAX)
                {
                    bottomValue--;
                }
                int newValue = (topValue + 1) * RGB_MAX / (RGB_MAX - bottomValue);

                newValue = adjustPixelValue(newValue);

                Color newColor = new Color(newValue, newValue, newValue);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
        return image;
    }

    /**
     * This method adjusts the pixel value to ensure it is within RGB range and
     * corrects the color balance
     * @param pixel whose value is to be adjusted
     * @return adjusted pixel value
     */
    private int adjustPixelValue(int pixel)
    {
        if (pixel < BLACK_INPUT_LEVEL)
        {
            pixel = RGB_MIN;
        }
        else if (pixel > RGB_MAX)
        {
            pixel = RGB_MAX;
        }
        else
        {
            pixel = ((pixel - BLACK_INPUT_LEVEL) / INPUT_LEVEL_RANGE) * RGB_MAX;
        }
        return pixel;
    }

    /**
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
