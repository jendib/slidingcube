package com.slidingcube;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.slidingcube.screen.BaseScreen;
import com.slidingcube.screen.GameScreen;
import com.slidingcube.screen.HomeScreen;
import com.slidingcube.screen.NumberOfPlayerScreen;

class GdxGame extends Game {
	private SpriteBatch batch;

	public void create() {
		batch = new SpriteBatch();

        // Start by NumberOfPlayerScreen
        this.setScreen(new NumberOfPlayerScreen(this));

    }

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
	}
}
