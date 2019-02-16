package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.controller.GameController;
import com.dat055.controller.MenuController;
import com.dat055.model.GameModel;
import com.dat055.view.GameView;

public class Game extends ApplicationAdapter {
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
			menuController.update(deltaTime);

		if(!gameController.isPaused())
			gameController.update(deltaTime);


		// Draw
		batch.begin();
		if(!gameController.isPaused())
			gameController.render(batch);
		menuController.render();
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