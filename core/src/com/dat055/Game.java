package com.dat055;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Entity.Enemy;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	GameModel gameModel;
	Player player;
	Enemy enemy;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(1, 80, 64, "red_penguin_64x80.png", "Towbie", 5, 10);
		enemy = new Enemy(2, 80, 64, "blue_penguin_64x80.png", "Djellie", 5, 10);
		gameModel = new GameModel();
	}

	@Override
	public void render () {
		player.checkKeyboardInput();
        player.update();
		gameModel.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		gameModel.draw(batch);
		player.draw(batch);
		enemy.draw(batch);
		batch.end();
	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
