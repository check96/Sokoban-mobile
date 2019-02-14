package gameGui;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import model.Direction;
import model.FinalBox;
import model.Move;
import model.Objects;
import model.Player;
import sokoban.GameConfig;

public class GameScreen implements Screen {

	private GameManager game;

	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;

	private Hud hud;

	private Texture background;
	private Texture wall;
	private TextureRegion[][] player;
	private float timer = 0;

	public GameScreen(GameManager _game) {
		this.game = _game;

		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		player = TextureRegion.split(GameConfig.playerTexture, GameConfig.CELL, GameConfig.CELL);

		background = new Texture(Gdx.files.internal("images/gameBackground.jpg"));
		wall = new Texture(Gdx.files.internal("images/wall.png"));

		GameConfig.MOVE = 0;
		GameConfig.MOVES.push(new Move(GameConfig.getMap(), GameConfig.getPlayer()));

		hud = new Hud(game,this);
	}

	@Override
	public void show() { Gdx.input.setInputProcessor(hud.stage);}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleSwipe();
		if(timer > 0.3)
		{
			handleInput();
			timer = 0;
		}

		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		spriteBatch.end();
		
		List<FinalBox> finalPositions = GameConfig.FINAL_POSITIONS.get(GameConfig.LEVEL);

		int height = 400;
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Filled);
		for (FinalBox position : finalPositions)
			shapeRenderer.circle((position.getX()+0.5f)*GameConfig.CELL, height+(position.getY()+0.5f)*GameConfig.CELL,20);
		shapeRenderer.end();
		
		spriteBatch.begin();
		Objects[][] map = GameConfig.getMap().getMap();
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == null)
					continue;

				switch (map[i][j]) {
					case PLAYER:
						int x = GameConfig.PLAYERS.get(GameConfig.LEVEL).getDirection().getTag();
						spriteBatch.draw(player[0][x], i * GameConfig.CELL, height +j * GameConfig.CELL);
						break;
					case WALL:
						spriteBatch.draw(wall, i * GameConfig.CELL, height +j * GameConfig.CELL);
						break;
					case BOX:
						spriteBatch.draw(GameConfig.box, i * GameConfig.CELL, height +j * GameConfig.CELL);
						for(FinalBox finalBox : GameConfig.FINAL_POSITIONS.get(GameConfig.LEVEL))
							if(finalBox.getX() == i && finalBox.getY() == j)
								spriteBatch.draw(GameConfig.finalBox, i * GameConfig.CELL, height +j * GameConfig.CELL);
						break;
					default:
						break;
				}
			}
		spriteBatch.end();
		
		hud.update();
		hud.stage.act();
		hud.stage.draw();

		timer += delta;
		if(GameConfig.WORLD.gameOver())
			game.setScreen(new GameOverScreen(game, this));
	}


	private void handleSwipe(){
		int deltaX = Gdx.input.getDeltaX();
		int deltaY = Gdx.input.getDeltaY();
		if(Gdx.input.isTouched()){
			if(Math.abs(deltaX) > Math.abs(deltaY)) {
				if (deltaX > 10)
					GameConfig.RIGHT = true;
				if (deltaX < -10)
					GameConfig.LEFT = true;
			}
			else  {
				if (deltaY > 10)
					GameConfig.DOWN = true;
				if (deltaY < -10)
					GameConfig.UP = true;
			}
		}
	}

	private void handleInput() {

		Player player = GameConfig.PLAYERS.get(GameConfig.LEVEL);
		if (Gdx.input.isKeyJustPressed(Keys.LEFT) || GameConfig.LEFT) {
			player.setDirection(Direction.LEFT);
			GameConfig.WORLD.update(Direction.LEFT);
		}
		else if (Gdx.input.isKeyJustPressed(Keys.RIGHT) || GameConfig.RIGHT) {
			player.setDirection(Direction.RIGHT);
			GameConfig.WORLD.update(Direction.RIGHT);
		}
		else if (Gdx.input.isKeyJustPressed(Keys.UP) || GameConfig.UP) {
			player.setDirection(Direction.UP);
			GameConfig.WORLD.update(Direction.UP);
		}
		else if (Gdx.input.isKeyJustPressed(Keys.DOWN) || GameConfig.DOWN) {
			player.setDirection(Direction.DOWN);
			GameConfig.WORLD.update(Direction.DOWN);
		}

		GameConfig.LEFT = false;
		GameConfig.RIGHT = false;
		GameConfig.UP = false;
		GameConfig.DOWN = false;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		wall.dispose();
		GameConfig.playerTexture.dispose();
		GameConfig.box.dispose();
		GameConfig.finalBox.dispose();
		hud.dispose();
	}

}
