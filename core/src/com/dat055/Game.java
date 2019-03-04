package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.dat055.controller.GameController;
import com.dat055.controller.MenuController;

/**
 * Class generated by framework.
 * This class is called when a window is rendered.
 * @author everyone
 */
public class Game extends ApplicationAdapter {
	private PolygonSpriteBatch batch;
	private GameController gameController;
	private MenuController menuController;

	private float deltaTime; // Time since last update

	/**
	 * Initialize
	 */
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();

		gameController = new GameController();
		menuController = new MenuController();

		menuController.setController(gameController);
		gameController.setController(menuController);
	}

	/**
	 * Draws sprites to screen.
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.4f, 0.4f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime = Gdx.graphics.getDeltaTime();


		if(menuController.isVisible())
			menuController.update(deltaTime);

		if(gameController.isRunning())
			gameController.update(deltaTime);

		// Draw
		batch.enableBlending();
		batch.begin();
		if(gameController.isRunning())
			gameController.render(batch);
		batch.end();
		menuController.render();
	}

	@Override
	public void resize(int width, int height) { menuController.resize(width,height); }

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void pauseMenu() {
		menuController.swapMenu("Pause");
		menuController.toggleVisibility();
	}
}