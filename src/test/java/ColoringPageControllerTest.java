import com.example.coloringpage.ColoringPageController;
import com.example.coloringpage.ImageToColoringPageConverter;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.File;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ColoringPageControllerTest
{
    @Test
    void onBrowseButtonClick()
    {
        //given
        ImageView originalImageView = mock(ImageView.class);
        TextField fileNameTextField = mock(TextField.class);
        ImageToColoringPageConverter converter = new ImageToColoringPageConverter();
        File file = mock(File.class);
        ColoringPageController controller = new ColoringPageController(converter, file);
        doReturn(mock(File.class)).when(file).getAbsoluteFile();

        //when
        controller.onBrowseButtonClick();

        //then
        verify(fileNameTextField).setText(file.getAbsolutePath());
        verify(originalImageView).setImage(any(Image.class));
    }

    @Test
    void onConvertButtonClick()
    {
        //given
        ImageView modifiedImageView = mock(ImageView.class);
        ImageToColoringPageConverter converter = new ImageToColoringPageConverter();
        ColoringPageController controller = new ColoringPageController();

        //when
        controller.onConvertButtonClick(mock(ActionEvent.class));

        //then
        verify(modifiedImageView).setImage(any(Image.class));
    }

    @Test
    void onSaveButtonClick()
    {
        //given
        ImageToColoringPageConverter converter = new ImageToColoringPageConverter();
        File file = mock(File.class);
        ColoringPageController controller = new ColoringPageController(converter, file);

        //when
        controller.onSaveButtonClick(mock(ActionEvent.class));

        //then
    }
}