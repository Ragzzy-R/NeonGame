package com.jakkarrlgames.NeonGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Loading implements Screen {
	Game myGame;
	SpriteBatch batch;
	OrthographicCamera camera;
	Entity loading;
	Entity progressBar;
	Texture loadingTexture;
	Sprite loadingSprite;
	Texture progressTexture;
	Sprite progressSprite;
	ShapeRenderer line;
	BitmapFont font;
	float w,h,x;
	Float progress;
	public Loading(Game game) {
		myGame = game;
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 
		progress = neonGame.manager.getProgress();
		progress *= 100;
		if(neonGame.manager.update()) {
			myGame.setScreen(new GamePlay(myGame));
		}
		 
		 
		batch.setProjectionMatrix(camera.combined);
		
		progressSprite.setPosition((progress*w)/100-w,loadingSprite.getY()+loadingSprite.getHeight());
		batch.begin();
		loadingSprite.draw(batch);
		progressSprite.draw(batch);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		 w = Gdx.graphics.getWidth();
		 h = Gdx.graphics.getHeight();
		setCamera();
		neonGame.manager = new AssetManager();
		loading = new Entity();
		progressBar = new Entity();
		batch = new SpriteBatch();
		line = new ShapeRenderer();
		loadingTexture = new Texture(Gdx.files.internal("data/neon/loading.png"));
		loadingTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		loading.getRegionFromTexture(loadingTexture, 0, 0, loadingTexture.getWidth(), loadingTexture.getHeight());
		loadingSprite  = loading.createSprite(new Vector2(w,h/4),new Vector2(0,0));
		progressTexture = new Texture(Gdx.files.internal("data/neon/progress.png"));
		progressTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		progressBar.getRegionFromTexture(progressTexture, 0, 0, progressTexture.getWidth(), progressTexture.getHeight());
		progressSprite  = progressBar.createSprite(new Vector2(w,h/4),new Vector2(0,loadingSprite.getY()+loadingSprite.getHeight()));
		
		
		font = new BitmapFont(Gdx.files.internal("data/neon/font.fnt"),
				Gdx.files.internal("data/neon/font_0.png"), false);
		neonGame.manager.load("data/neon/0.png", Texture.class);
		neonGame.manager.load("data/neon/pull.png", Texture.class);
		neonGame.manager.load("data/neon/pause.png", Texture.class);
		neonGame.manager.load("data/neon/box.png", Texture.class);
		neonGame.manager.load("data/neon/greenball.png", Texture.class);
		neonGame.manager.load("data/neon/spike.png", Texture.class);
		 

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void setCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
	}

}



