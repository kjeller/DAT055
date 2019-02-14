package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Controller.Controller;
import com.dat055.Controller.GameController;
import com.dat055.Controller.MenuController;
import com.dat055.Model.GameModel;
import com.dat055.Model.Model;
import com.dat055.View.GameView;
import com.dat055.View.MenuView;
import com.dat055.View.View;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	private boolean toggle = true;
	private SpriteBatch batch;
	private GameController gameController;
	private MenuController menuController;

	private float deltaTime; // Time since last update
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		GameModel gameModel = new GameModel();
		gameController = new GameController(gameModel, new GameView(gameModel));
		menuController = new MenuController(gameController);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime = Gdx.graphics.getDeltaTime();


		if(menuController.isVisible())
			menuController.update();

		if(!gameController.isPaused())
			gameController.update(deltaTime);


		// Draw
		batch.begin();
		if(!gameController.isPaused())
			gameController.render(batch);
		menuController.draw();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void pauseMenu() {
		menuController.swapMenu("Pause");
		menuController.toggleVisibility();
	}
}