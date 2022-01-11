import com.example.coloringpage.ColoringPageController;
import com.example.coloringpage.ImageToColoringPageConverter;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ColoringPageControllerTest
{
    @Test
    void onUploadButtonClick_validFilePath()
    {
        //given
        TextField fileNameTextField = mock(TextField.class);
        doReturn("KittenBackground.jpg").when(fileNameTextField).getText();

        ImageView originalImageView = mock(ImageView.class);
        ImageView modifiedImageView = mock(ImageView.class);

        ImageToColoringPageConverter converter = new ImageToColoringPageConverter();

        ColoringPageController controller = new ColoringPageController(converter);

        //when
        controller.onUploadButtonClick();

        //then
        verify(originalImageView).setImage(any(Image.class));
        verify(modifiedImageView).setImage(any(Image.class));
    }

    @Test
    void onUploadButtonClick_invalidFilePath()
    {
        //given
        ImageToColoringPageConverter converter = new ImageToColoringPageConverter();
        ColoringPageController controller = new ColoringPageController(converter);
        TextField fileNameTextField = mock(TextField.class);
        ImageView originalImageView = mock(ImageView.class);
        ImageView modifiedImageView = mock(ImageView.class);

        //when
        controller.onUploadButtonClick();

        //then
    }
}