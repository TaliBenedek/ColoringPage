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
import java.util.Optional;

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
            file = fileChooser.showOpenDialog(null);
            fileNameTextField.setText(file.getAbsolutePath());
            BufferedImage originalImage = ImageIO.read(file);
            originalImageView.setImage(SwingFXUtils.toFXImage(originalImage, null));
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
        try
        {
            String extension = getExtensionByStringHandling(saveFile.getName()).toString();
            ImageIO.write(bufferedFinalImage, extension, file);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,
                                          "There was an error in saving the file.\nPlease try again.",
                                          "Error",
                                          1);
        }
    }

    private Optional<String> getExtensionByStringHandling(String filename)
    {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}