package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	GameModel gameModel;
	Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(1, 80, 64, "red_penguin_64x80.png", "Towbie", 5, 5);
		gameModel = new GameModel();
	}

	@Override
	public void render () {
		player.checkKeyboardInput();
		gameModel.update();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		gameModel.draw(batch);
		player.draw(batch);
		batch.end();
	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
