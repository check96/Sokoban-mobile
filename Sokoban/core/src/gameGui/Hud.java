package gameGui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import sokoban.GameConfig;

public class Hud implements Disposable {
	public Stage stage;
	private SpriteBatch spriteBatch;

	private Button undo;
	private Button restart;
	private Button pause;

	private Texture undoTexture;
	private Texture restartTexture;

	private Label moves;
	private FreeTypeFontGenerator generator;
	private BitmapFont font;

	public Hud(GameManager _game, GameScreen _gameScreen) {

		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		spriteBatch = new SpriteBatch();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 40;
		font = generator.generateFont(parameter);

		moves = new Label("MOVES: " + GameConfig.MOVE, new Label.LabelStyle(font, Color.WHITE));
		moves.setPosition(510, 1850);

		undoTexture = new Texture(Gdx.files.internal("images/undo.png"));
		restartTexture = new Texture(Gdx.files.internal("images/restart.png"));

		undo = new ImageButton(new TextureRegionDrawable(new TextureRegion(undoTexture)));
		undo.setPosition(800, 1850);
		undo.addListener(new InputListener(){
			boolean touch = false;
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				if(touch){
					touch = false;
					GameConfig.undo();
				}
				
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				touch = true;
			}
		});

		pause = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/pause.png")))));
		pause.setPosition(100, 1850);
		pause.addListener(new InputListener(){
			boolean touch = false;
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				if(touch)
					touch = false;

				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				touch = true;
			}
		});

		restart = new ImageButton(new TextureRegionDrawable(new TextureRegion(restartTexture)));
		restart.setPosition(950, 1850);
		restart.addListener(new InputListener(){
			boolean touch = false;
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(touch) {
					touch = false;
					GameConfig.restart();
				}
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				touch = true;
			}
		});

		stage.addActor(pause);
		stage.addActor(undo);
		stage.addActor(restart);
		stage.addActor(moves);
	}
	public void update() {
		moves.setColor(Color.WHITE);
		moves.setText("MOVES: " + GameConfig.MOVE);
	}

	@Override
	public void dispose() {
		undoTexture.dispose();
		restartTexture.dispose();
		stage.dispose();
		font.dispose();
		generator.dispose();
		spriteBatch.dispose();
	}
}