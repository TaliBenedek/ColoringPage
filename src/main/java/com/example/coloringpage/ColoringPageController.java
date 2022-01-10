package com.example.coloringpage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
            BufferedImage bufferedImage = converter.getColoringPage(file);
            Image finalImage = SwingFXUtils.toFXImage(bufferedImage, null);
        }
        catch (IOException e)
        {
            JOptionPane.showInputDialog("That is not a valid file path.\nPlease try again.");
        }
    }
}