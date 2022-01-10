package com.example.coloringpage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColoringPageController
{
    @FXML
    Label uploadLabel;

    @FXML
    Button uploadButton;

    @FXML
    ImageView originalImageView;

    @FXML
    ImageView modifiedImageView;

    @FXML
    TextField fileNameTextField;

    ImageToColoringPageConverter converter;

    public ColoringPageController(ImageToColoringPageConverter converter)
    {
        this.converter = converter;
    }

    public ColoringPageController()
    {
       this.converter = new ImageToColoringPageConverter();
    }

    public void onUploadButtonClick()
    {
        try
        {
            File file = new File(fileNameTextField.getText());
            BufferedImage originalImage = ImageIO.read(file);
            originalImageView.setImage(SwingFXUtils.toFXImage(originalImage, null));
            
            BufferedImage bufferedImage = converter.getColoringPage(file);
            Image finalImage = SwingFXUtils.toFXImage(bufferedImage, null);
            modifiedImageView.setImage(finalImage);
        }
        catch (IOException e)
        {
            JOptionPane.showInputDialog("That is not a valid file path.\nPlease try again.");
        }
    }
}