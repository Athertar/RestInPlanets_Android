package de.aso.restinplanets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;


public class FontCreator {

	public static final HashMap<String, BitmapFont> bitmapFonts = new HashMap<String, BitmapFont>();

	public static BitmapFont createFont(String fontStyle, float borderWidth, int shadowOffsetX, int shadowOffsetY, int spacingX, int size) {
		String key = fontStyle + borderWidth + shadowOffsetX + shadowOffsetY + spacingX + size;
		if (bitmapFonts.containsKey(key)) {
			return bitmapFonts.get(key);
		} else {
			FreeTypeFontGenerator generator;
			FreeTypeFontGenerator.FreeTypeFontParameter parameter;
			generator = new FreeTypeFontGenerator(Gdx.files.internal(fontStyle));
			parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.borderWidth = borderWidth;
			parameter.shadowOffsetX = shadowOffsetX;
			parameter.shadowOffsetY = shadowOffsetY;
			parameter.spaceX = spacingX;
			parameter.size = size;
			BitmapFont bitmapFont = generator.generateFont(parameter);
			bitmapFonts.put(key, bitmapFont);
			generator.dispose();
			return bitmapFont;
		}
	}
}
