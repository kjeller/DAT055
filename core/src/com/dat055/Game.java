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
	private SpriteBatch batch;
	private ArrayList<Controller> controllers;
	private GameController gameController;
	private MenuController menuController;

	private float deltaTime; // Time since last update
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		controllers = new ArrayList<Controller>();

		GameModel gameModel = new GameModel();
		gameController = new GameController(gameModel, new GameView(gameModel));
		gameController.startMap("maps/map_0.json");

		menuController = new MenuController();
		controllers.add(gameController);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		deltaTime = Gdx.graphics.getDeltaTime();

		// Update
		for(Controller controller : controllers) { controller.update(deltaTime); }

		// Draw
		batch.begin();
		for(Controller controller : controllers) { controller.render(batch); }
		menuController.draw();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}