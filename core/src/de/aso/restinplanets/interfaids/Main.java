package de.aso.restinplanets.interfaids;

import com.badlogic.gdx.Game;

import de.aso.restinplanets.net.Client;

public class Main extends Game {

	@Override
	public void create() {
		setScreen(new IntroScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	public void showLoginScreen() {
		setScreen(new LoginScreen(this));
	}

}
