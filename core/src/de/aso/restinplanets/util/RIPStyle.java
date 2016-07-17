package de.aso.restinplanets.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RIPStyle {

	public static Skin getButtonSkin(float buttonHeight) {
		Skin buttonSkin = new Skin();

		BitmapFont bitmapFont = FontCreator.createFont("LemonMilk.otf", 4, 2, 2, 5, (int) buttonHeight / 3);

		buttonSkin.add("default", bitmapFont);

		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		buttonSkin.add("background", new Texture(pixmap));

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = buttonSkin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = buttonSkin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = buttonSkin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = buttonSkin.getFont("default");
		buttonSkin.add("default", textButtonStyle);
		return buttonSkin;
	}

	public static Skin getTextFieldSkin(float textFieldHeight) {
		Skin textFieldSkin = new Skin();
		BitmapFont bitmapFont = FontCreator.createFont("LemonMilk.otf", 4, 2, 2, 5, (int) textFieldHeight / 3);
		textFieldSkin.add("default", bitmapFont);

		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth(), Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		textFieldSkin.add("background", new Texture(pixmap));

		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = textFieldSkin.getFont("default");
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.background = textFieldSkin.newDrawable("background", Color.LIGHT_GRAY);
		textFieldSkin.add("default", textFieldStyle);
		return textFieldSkin;
	}

}
