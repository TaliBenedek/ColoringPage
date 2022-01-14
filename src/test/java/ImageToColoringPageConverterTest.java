import com.example.coloringpage.ImageToColoringPageConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ImageToColoringPageConverterTest
{
    @Test
    void getColoringPage() throws IOException
    {
        //given
        ImageToColoringPageConverter converter = new ImageToColoringPageConverter();
        File file = mock(File.class);
        doReturn(mock(BufferedImage.class)).when(ImageIO.read(file));

        //when
        BufferedImage image = converter.getColoringPage(file);

        //then
        Assertions.assertEquals(image, any(BufferedImage.class));
    }
}