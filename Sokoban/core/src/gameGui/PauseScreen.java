package gameGui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import sokoban.GameConfig;

public class PauseScreen implements Screen {

    private GameManager game;
    private GameScreen gameScreen;
    private Stage stage;
    private Button quit;
    private Button resume;
    private Skin skin;
    private SpriteBatch spriteBatch;
    private Texture background;

    public PauseScreen(GameManager _game, GameScreen _gameScreen){
        this.game = _game;
        this.gameScreen = _gameScreen;

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skin/shade/skin/uiskin.json"));

        quit = new TextButton("QUIT",skin);
        quit.setBounds(500, 700,200,150);
        quit.addListener(new InputListener(){
            boolean touch = false;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(touch){
                    touch = false;
                    game.setScreen(new StartScreen(game));
                }

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touch = true;
            }
        });

        spriteBatch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/start.jpg"));

        resume = new TextButton("RESUME",skin);
        resume.setBounds(500, 950,200,150);
        resume.addListener(new InputListener(){
            boolean touch = false;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(touch){
                    touch = false;
                    game.setScreen(gameScreen);
                }

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touch = true;
            }
        });

        stage.addActor(quit);
        stage.addActor(resume);
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
        spriteBatch.draw(background,0,0);
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
        background.dispose();
        spriteBatch.dispose();
    }
}
