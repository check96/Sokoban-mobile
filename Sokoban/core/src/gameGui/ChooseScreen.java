package gameGui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import sokoban.GameConfig;
import sokoban.World;

public class ChooseScreen implements Screen {
	private GameManager game;
	
	private String player = "Walter";
	private String box = "EmptyFlask";
	private String finalBox = "FullFlask";
	private Stage stage;
	private Skin skin;
	private Button play;
	private Button george;
	private Button walter;
	private Button death;

	private Label label;
	private FreeTypeFontGenerator generator;
	private BitmapFont font;

	private SpriteBatch spriteBatch;
	private Texture background;

	
	public ChooseScreen(GameManager _game) {
		this.game = _game;
		
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		skin = new Skin(Gdx.files.internal("skin/shade/skin/uiskin.json"));
		play = new TextButton("PLAY", skin);
		play.setBounds(500, 300, 100, 60);
		play.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	GameConfig.playerTexture = new Texture(Gdx.files.internal("players/"+player+".png"));
            	GameConfig.box = new Texture(Gdx.files.internal("images/"+box+".png"));
            	GameConfig.finalBox = new Texture(Gdx.files.internal("images/"+finalBox+".png"));
        		
            	GameConfig.WORLD = new World();
            	game.setScreen(new GameScreen(game));
                return true;
            }
        });
		
		george = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("players/George.png")))));
		george.setPosition(150, 650);
		george.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	player = "George";
            	box = "box";
            	finalBox = "finalBox";
				update();
                return true;
            }
        });
		
		death = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("players/Death.png")))));
		death.setPosition(150, 850);
		death.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	player = "Death";
            	box = "Soul";
            	finalBox = "DeadSoul";
				update();
            	return true;
            }
        });
		
		walter = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("players/Walter.png")))));
		walter.setPosition(150, 1050);
		walter.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
            	player = "Walter";
            	box = "EmptyFlask";
            	finalBox = "FullFlask";
            	update();
                return true;
            }
        });
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font/comic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 40;
		font = generator.generateFont(parameter);

		label = new Label(player, new Label.LabelStyle(font, Color.WHITE));
		label.setPosition(700,1060);

		stage.addActor(label);
		stage.addActor(george);
		stage.addActor(death);
		stage.addActor(walter);
		stage.addActor(play);

		spriteBatch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("images/start.jpg"));

	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	public void update(){
		label.setText(player);
		if(player.equals("George"))
			label.setPosition(700,660);
		else if(player.equals("Death"))
			label.setPosition(700,860);
		else if(player.equals("Walter"))
			label.setPosition(700, 1060);
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
		font.dispose();
		generator.dispose();
	}

}
