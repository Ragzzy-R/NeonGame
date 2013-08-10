package com.jakkarrlgames.NeonGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Help implements Screen {
	Game myGame;
	Entity help;
	Texture helpTexture;
	Sprite helpSprite;
	SpriteBatch batch;
	Music music;
	OrthographicCamera camera;
	float w,h;
	public Help(Game game) {
		myGame = game;
	}

	
	@Override
	public void show() {
		 w = Gdx.graphics.getWidth();
		 h = Gdx.graphics.getHeight();
		 setCamera();
		help = new Entity();
		batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("data/neon/Menu_loop.mp3"));
		helpTexture = new Texture(Gdx.files.internal("data/neon/help.png"));
		helpTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		help.getRegionFromTexture(helpTexture, 0, 0, helpTexture.getWidth(), helpTexture.getHeight());
		helpSprite = help.createSprite(new Vector2(w,h), new Vector2(0,0));
		
		
		
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		helpSprite.draw(batch);
		batch.end();
		if(Gdx.input.isTouched()) {
	         Vector3 touchPos = new Vector3();
	         touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
	         myGame.setScreen(new MainMenu(myGame));
	      }
		try {
			if(MainMenu.MUSICSWITCH) {
				
				if(!music.isPlaying())
					music.play();
				
			}
			else {
				
				if(music.isPlaying())
					music.stop();
			} 
			}catch(NullPointerException e) {
				
			}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void hide() {
		music.dispose();

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
