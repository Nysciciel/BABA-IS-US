/*package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.MyTextInputListener;

public class MainMenu2 extends GameState {
    private Texture background, ground, character;
    private BitmapFont gameTitleText, touchText, toText, startText;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout gameTitleGlyph, touchGlyph, toGlyph, startGlyph;
    private Stage stage;
    public MainMenu2(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture("Menu_background.jpg");

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        this.parameter.size = 128;

        this.gameTitleText = this.generator.generateFont(this.parameter);
        this.gameTitleGlyph = new GlyphLayout();

        this.gameTitleGlyph.setText(this.gameTitleText, Constants.GAME_TITLE);

        this.camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);


    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            this.gsm.set(new EditorState(gsm));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.gsm.set(new LevelState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
       sb.setProjectionMatrix(this.camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        this.gameTitleText.draw(sb, this.gameTitleGlyph, this.camera.viewportWidth/2 - this.gameTitleGlyph.width/2, this.camera.viewportHeight/2);
        sb.end();
    }

    @Override
    public void dispose() {
        this.gameTitleText.dispose();
        this.background.dispose();
    }

}*/
