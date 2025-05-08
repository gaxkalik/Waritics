package waritics.core;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class PixelFont
{
    public static final Font pixelFont50 = loadFont(50);
    public static final Font pixelFont40 = loadFont(40);
    public static final Font pixelFont30 = loadFont(30);
    public static final Font pixelFont20 = loadFont(20);
    public static final Font pixelFont15 = loadFont(15);
    public static final Font pixelFont10 = loadFont(10);
    public static final Font pixelFont8 = loadFont(8);
    public static final Font pixelFont5 = loadFont(5);

    public static Font loadFont( float size)
    {
        try
        {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(PixelFont.class.getResource("fonts/PixelR.ttf").getFile())).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;

        } catch (IOException | java.awt.FontFormatException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
