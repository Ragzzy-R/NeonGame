package com.jakkarrlgames.NeonGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Intro implements Screen {

	Game myGame;
	Entity intro;
	Texture introTexture;
	Sprite introSprite;
	SpriteBatch batch;
	OrthographicCamera camera;
	float w,h,a;
	float startTime,currentTime;
	public Intro(Game game) {
		myGame = game;
	}

	
	@Override
	public void show() {
		 w = Gdx.graphics.getWidth();
		 h = Gdx.graphics.getHeight();
		 setCamera();
		intro = new Entity();
		batch = new SpriteBatch();
		introTexture = new Texture(Gdx.files.internal("data/neon/logo2.png"));
		introTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		intro.getRegionFromTexture(introTexture, 0, 0, introTexture.getWidth(), introTexture.getHeight());
		introSprite = intro.createSprite(new Vector2(w/3.12f,h/1.75f), new Vector2(w/2-(w/6.25f),h-((h/2)+(h/3.5f))));
		startTime = Gdx.graphics.getDeltaTime();
	}

	@Override
	public void render(float delta) {
		startTime +=Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(new Color(.2f,.3f,.5f,1.0f));
	
		a+=1;
		introSprite.draw(batch);
		batch.end();

		if(startTime > 2f) {
			myGame.setScreen(new MainMenu(myGame));
		}
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void hide() {
		dispose();

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
		introTexture.dispose();
		batch.dispose();
		intro = null;
		introSprite = null;

	}
	public void setCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
	}
}
