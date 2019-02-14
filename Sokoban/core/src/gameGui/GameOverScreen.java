package gameGui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import model.Move;
import sokoban.GameConfig;

public class GameOverScreen implements Screen{
	
	private GameManager game;
	private GameScreen gameScreen;
	
	private Stage stage;
	private SpriteBatch spriteBatch;
	private Texture background;

	private FreeTypeFontGenerator generator;
	private BitmapFont font;

	private Label label;
	private Label moves;

	private Skin skin;
	private Button next;
	private Button quit;
	
	public GameOverScreen(GameManager _game, GameScreen _gameScreen) {
		this.game = _game;
		this.gameScreen = _gameScreen;
		
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("images/start.jpg"));

		generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 60;
		font = generator.generateFont(parameter);

		label = new Label("  LEVEL  " + GameConfig.LEVEL + " COMPLETED" , new Label.LabelStyle(font, Color.WHITE));
		label.setPosition(250,1200);

		parameter.size = 30;
		font = generator.generateFont(parameter);
		moves = new Label("MOVES: " + GameConfig.MOVE, new Label.LabelStyle(font, Color.WHITE));
		moves.setPosition(400,1100);

		skin = new Skin(Gdx.files.internal("skin/shade/skin/uiskin.json"));
		next = new TextButton("next", skin);
		next.setPosition(400, 700);
		next.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
    			GameConfig.LEVEL++;
    			GameConfig.MOVE = 0;
    			GameConfig.MOVES.clear();
    			GameConfig.MOVES.push(new Move(GameConfig.getMap(), GameConfig.getPlayer()));
            	game.setScreen(gameScreen);
                return true;
            }
        });
		
		quit = new TextButton("quit", skin);
		quit.setPosition(400, 600);
		quit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	game.setScreen(new StartScreen(game));
                return true;
            }
        });

		stage.addActor(label);
		stage.addActor(moves);
		stage.addActor(next);
		stage.addActor(quit);
	}

	@Override
	public void show() { Gdx.input.setInputProcessor(stage);}

	@Override
	public void render(float delta) {

		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		spriteBatch.end();
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void hide() { }

	@Override
	public void dispose() {
		stage.dispose();
		generator.dispose();
		font.dispose();
		skin.dispose();
		spriteBatch.dispose();
		background.dispose();
	}
}
