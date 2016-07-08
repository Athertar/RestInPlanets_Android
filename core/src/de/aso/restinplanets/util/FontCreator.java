package de.aso.restinplanets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class FontCreator {


	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;


	public FontCreator(String fontStyle, float borderWidth, int shadowOffsetX, int shadowOffsetY, int spacingX) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal(fontStyle));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.borderWidth = borderWidth;
		parameter.shadowOffsetX = shadowOffsetX;
		parameter.shadowOffsetY = shadowOffsetY;
		parameter.spaceX = spacingX;
	}

	public BitmapFont generateFont(int size) {
		parameter.size = size;
		Gdx.app.log("Font Generator", "" + size);
		return generator.generateFont(parameter);
	}

	public void dispose() {
		generator.dispose();
	}

}
