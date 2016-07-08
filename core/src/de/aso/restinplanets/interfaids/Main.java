package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Game;

public class Main extends Game {

	@Override
	public void create() {
		setScreen(new LoginScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

}
