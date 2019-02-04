package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.GameModel;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	GameModel gameModel;
	Texture img;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("textures/debug_texture.png");
		gameModel = new GameModel();
	}

	@Override
	public void render () {
		gameModel.update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		gameModel.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
