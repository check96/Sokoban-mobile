package gameGui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class StartScreen implements Screen {

	private GameManager game;
	private Button play;
	private Button exit;
	private Stage stage;
	private Skin skin;
	private SpriteBatch spriteBatch;
	private Texture background;

	public StartScreen(GameManager _game) {

		this.game = _game;
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		skin = new Skin(Gdx.files.internal("skin/shade/skin/uiskin.json"));
		play = new TextButton("PLAY", skin);
		play.setBounds(500, 950, 200, 150);
		play.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	game.setScreen(new ChooseScreen(game));
                return true;
            }
        });
		
		exit = new TextButton("EXIT", skin);
		exit.setBounds(500, 700, 200, 150);
		exit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	Gdx.app.exit();
                return true;
            }
        });

		stage.addActor(exit);
		stage.addActor(play);

		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("images/start.jpg"));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
		skin.dispose();
		spriteBatch.dispose();
		background.dispose();
	}

}
