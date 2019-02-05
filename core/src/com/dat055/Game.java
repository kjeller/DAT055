package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.View.GameView;
import com.dat055.View.View;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private ArrayList<View> views;
	private GameView gameView;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		// Add every view
		views = new ArrayList<View>();
		gameView = new GameView();
		views.add(gameView);
		//TODO: Add menuview here for example

		gameView.startMap("maps/map_0.json");

		// Initialize every view
		for(View view : views) { view.initialize(); }
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for(View view : views) { view.update(); }

		batch.begin();
		for(View view : views) { view.draw(batch);}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
