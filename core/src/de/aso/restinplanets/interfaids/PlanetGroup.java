package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.ArrayList;

import de.aso.restinplanets.net.Planet;
import de.aso.restinplanets.util.FontCreator;

import static com.badlogic.gdx.Input.Keys.CENTER;

public class PlanetGroup extends Group{

	private Planet planet;

	private BitmapFont smallTextFont = FontCreator.createFont("LemonMilk.otf", 4, 2, 2, 5, 46);
	private BitmapFont textFont = FontCreator.createFont("LemonMilk.otf", 4, 2, 2, 5, 96);

	private TextButton.TextButtonStyle textButtonStyle;
	private TextField.TextFieldStyle textFieldStyle;

	private TextField aluminiumAmount;
	private TextField siliconAmount;
	private TextField titaniumAmount;
	private TextField asoiumAmount;

	private Pixmap pixmap;

	private Skin buttonSkin;
	private Skin textFieldSkin;

	private float borderOffset = 20f;
	private float textFieldLetterHeight = 3*smallTextFont.getXHeight();
	private float buttonLetterHeight = 3*textFont.getXHeight();


	public PlanetGroup(Planet planet) {
		this.planet = planet;
		create();
	}

	/*public PlanetGroup(Planet planet, FontCreator fontCreator) {
		this.planet = planet;
		this.fontCreator = fontCreator;
		create();
		this.fontCreator.dispose();
	}

	public PlanetGroup(Planet planet, FontCreator fontCreator, int textFontSize, int smallTextFontSize) {
		this.planet = planet;
		this.fontCreator = fontCreator;
		this.textFont = fontCreator.generateFont(textFontSize);
		this.smallTextFont = fontCreator.generateFont(smallTextFontSize);
		create();
		this.fontCreator.dispose();
	}*/

	public void create() {
		buttonSkin = new Skin();
		textFieldSkin = new Skin();

		buttonSkin.add("default", textFont);
		textFieldSkin.add("default", smallTextFont);

		pixmap = new Pixmap(Gdx.graphics.getWidth()/4,Gdx.graphics.getWidth(), Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		buttonSkin.add("background", new Texture(pixmap));
		textFieldSkin.add("background", new Texture(pixmap));

		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = buttonSkin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = buttonSkin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = buttonSkin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = buttonSkin.getFont("default");
		buttonSkin.add("default", textButtonStyle);

		textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = textFieldSkin.getFont("default");
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.background = textFieldSkin.newDrawable("background", Color.LIGHT_GRAY);
		textFieldSkin.add("default", textFieldStyle);

		float fieldWidth = Gdx.graphics.getWidth() / 4 - borderOffset/2;

		aluminiumAmount = new TextField("", textFieldSkin);
		aluminiumAmount.setPosition(borderOffset, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		aluminiumAmount.setAlignment(CENTER);
		aluminiumAmount.setWidth(fieldWidth);
		aluminiumAmount.setHeight(textFieldLetterHeight);
		this.addActor(aluminiumAmount);

		siliconAmount = new TextField("", textFieldSkin);
		siliconAmount.setPosition(borderOffset + fieldWidth, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		siliconAmount.setAlignment(CENTER);
		siliconAmount.setWidth(fieldWidth);
		siliconAmount.setHeight(textFieldLetterHeight);
		this.addActor(siliconAmount);

		titaniumAmount = new TextField("", textFieldSkin);
		titaniumAmount.setPosition(borderOffset + 2*fieldWidth, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		titaniumAmount.setAlignment(CENTER);
		titaniumAmount.setWidth(fieldWidth);
		titaniumAmount.setHeight(textFieldLetterHeight);
		this.addActor(titaniumAmount);

		asoiumAmount = new TextField("", textFieldSkin);
		asoiumAmount.setPosition(borderOffset + 3*fieldWidth, Gdx.graphics.getHeight()-2*textFieldLetterHeight-borderOffset);
		asoiumAmount.setAlignment(CENTER);
		asoiumAmount.setWidth(fieldWidth);
		asoiumAmount.setHeight(textFieldLetterHeight);
		this.addActor(asoiumAmount);

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		planet.update(delta);
		aluminiumAmount.setText("" + planet.getAluminiumTons() + " K");
		siliconAmount.setText("" + planet.getSiliconTons()+ " K");
		titaniumAmount.setText("" + planet.getTitaniumTons()+ " K");
		asoiumAmount.setText("" + planet.getAsoiumTons()+ " K");
	}

	public TextField getAluminiumAmount() {
		return aluminiumAmount;
	}

	public TextField getSiliconAmount() {
		return siliconAmount;
	}

	public TextField getTitaniumAmount() {
		return titaniumAmount;
	}

	public TextField getAsoiumAmount() {
		return asoiumAmount;
	}
}
