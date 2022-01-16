package coloringpage;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ColoringPageControllerTest
{
    @Test
    void onBrowseButtonClick() throws IOException
    {
        //given
        ColoringPageController controller = setupController();
        BufferedImage originalBImage = mock(BufferedImage.class);
        Image originalFxImage = mock(Image.class);
        Image finalFxImage = mock(Image.class);
        doReturn(mock(ObservableList.class)).when(controller.fileChooser).getExtensionFilters();
        doReturn(controller.file).when(controller.fileChooser).showOpenDialog(null);
        doReturn("C:Users").when(controller.fileNameTextField).getText();
        doReturn("C:Users").when(controller.file).getAbsolutePath();
        doReturn(originalBImage).when(ImageIO).read(controller.file);
        doReturn(originalFxImage).when(SwingFXUtils.toFXImage(originalBImage, null));
        doReturn(originalFxImage).when(controller.originalImageView).getImage();
        doReturn(controller.bufferedFinalImage).when(controller.converter).getColoringPage(controller.file);
        doReturn(finalFxImage).when(SwingFXUtils.toFXImage(controller.bufferedFinalImage, null));
        doReturn(finalFxImage).when(controller.modifiedImageView).getImage();

        //when
        controller.onBrowseButtonClick();

        //then
        verify(controller.fileNameTextField).setText("C:Users");
        verify(controller.originalImageView).setImage(originalFxImage);
        verify(controller.modifiedImageView).setImage(finalFxImage);
    }

    @Test
    void onSaveButtonClick()
    {
        //given
        ImageToColoringPageConverter converter = mock(ImageToColoringPageConverter.class);
        File file = mock(File.class);
        FileChooser fileChooser = mock(FileChooser.class);
        ColoringPageController controller = new ColoringPageController(converter, file, fileChooser);

        //when
        controller.onSaveButtonClick(mock(ActionEvent.class));

        //then
    }

    private ColoringPageController setupController()
    {
        ImageView originalImageView = mock(ImageView.class);
        ImageView modifiedImageView = mock(ImageView.class);
        TextField fileNameTextField = mock(TextField.class);
        ImageToColoringPageConverter converter = mock(ImageToColoringPageConverter.class);
        File file = mock(File.class);
        FileChooser fileChooser = mock(FileChooser.class);
        BufferedImage bufferedFinalImage = mock(BufferedImage.class);
        ColoringPageController controller = new ColoringPageController(converter, file, fileChooser);
        controller.originalImageView = originalImageView;
        controller.modifiedImageView = modifiedImageView;
        controller.fileNameTextField = fileNameTextField;
        controller.bufferedFinalImage = bufferedFinalImage;
        return controller;
    }
}