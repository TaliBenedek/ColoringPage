package com.example.coloringpage;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    Button browseButton;

    @FXML
    ImageView originalImageView;

    @FXML
    ImageView modifiedImageView;

    @FXML
    TextField fileNameTextField;

    ImageToColoringPageConverter converter;
    File file;
    FileChooser fileChooser = new FileChooser();
    BufferedImage bufferedFinalImage;

    public ColoringPageController(ImageToColoringPageConverter converter, File file)
    {
        this.converter = converter;
        this.file = file;
    }

    public ColoringPageController()
    {
        this.converter = new ImageToColoringPageConverter();

    }

    public void onBrowseButtonClick()
    {
        try
        {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            file = fileChooser.showOpenDialog(null);
            if (file != null)
            {
                fileNameTextField.setText(file.getAbsolutePath());
                BufferedImage originalImage = ImageIO.read(file);
                originalImageView.setImage(SwingFXUtils.toFXImage(originalImage, null));
            }
        }

        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,
                                          "That is not a valid file path.\nPlease try again.",
                                          "Error",
                                          1);
        }
    }

    public void onConvertButtonClick(ActionEvent actionEvent)
    {
        try
        {
            bufferedFinalImage = converter.getColoringPage(file);
            Image finalImage = SwingFXUtils.toFXImage(bufferedFinalImage, null);
            modifiedImageView.setImage(finalImage);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,
                                          "That is not a valid file path.\nPlease try again.",
                                          "Error",
                                          1);
        }
    }

    public void onSaveButtonClick(ActionEvent actionEvent)
    {
        File saveFile = fileChooser.showSaveDialog(null);
        if (saveFile != null)
        {
            try
            {
                ImageIO.write(SwingFXUtils.fromFXImage(modifiedImageView.getImage(),
                                                       null), "png", saveFile);
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null,
                                              "There was an error in saving the file.\nPlease try again.",
                                              "Error",
                                              1);
            }
        }
    }
}