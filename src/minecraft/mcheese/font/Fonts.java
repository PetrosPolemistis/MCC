package mcheese.font;

import java.awt.Font;

import javax.swing.text.Style;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatStyle;

import org.newdawn.slick.UnicodeFont;

public class Fonts
{
	public static UnicodeFontRenderer tahoma32;
	public static UnicodeFontRenderer tahoma18;
	public static UnicodeFontRenderer tahoma50;
	public static UnicodeFontRenderer tahoma24;
	public static UnicodeFontRenderer tahoma70;
	public static UnicodeFontRenderer cicle12;
	public static UnicodeFontRenderer wingdings3;
	public static UnicodeFontRenderer prox1;
	public static UnicodeFontRenderer tahoma5;
	public static UnicodeFontRenderer iridium;

	public static void loadFonts()
	{
	    tahoma32 = new UnicodeFontRenderer(new Font("Anna", 0, 35));
	    tahoma18 = new UnicodeFontRenderer(new Font("Segoe UI", 0, 18));
	    tahoma50 = new UnicodeFontRenderer(new Font("Opticon One", 1, 50));
	    tahoma24 = new UnicodeFontRenderer(new Font("Segoe UI", 1, 24));
	    tahoma5 = new UnicodeFontRenderer(new Font("Segoe UI", 0, 15));

	    tahoma70 = new UnicodeFontRenderer(new Font("Yorkville", 0, 100));
	    prox1 = new UnicodeFontRenderer(new Font("Forgotten Futurist", 1, 12));
	    cicle12 = new UnicodeFontRenderer(new Font("Redring 1969", 0, 12));
	    wingdings3 = new UnicodeFontRenderer(new Font("Geo Sans Light", 0, 12));
	    iridium = new UnicodeFontRenderer(new Font("Mank Sans", 0, 12));
	}
}
