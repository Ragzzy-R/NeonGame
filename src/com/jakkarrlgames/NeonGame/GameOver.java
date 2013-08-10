package com.jakkarrlgames.NeonGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameOver implements Screen {
	Game myGame;
	Entity gameOver;
	Texture gameOverTexture;
	Sprite gameOverSprite;
	Entity playAgain;
	Texture playAgainTexture;
	Sprite playAgainSprite;
	Entity backToMenu;
	Texture backToMenuTexture;
	Sprite backToMenuSprite;
	BitmapFont font;
	SpriteBatch batch;
	Integer score;
	OrthographicCamera camera;
	float w,h;
	public GameOver(Game g,Integer s) {
		myGame  = g;
		score = s;
	}

	@Override
	public void show() {
		w = Gdx.graphics.getWidth();
		 h = Gdx.graphics.getHeight();
		 setCamera();
		gameOver = new Entity();
		playAgain = new Entity();
		backToMenu = new Entity();
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("data/neon/font.fnt"),
				Gdx.files.internal("data/neon/font_0.png"), false);
		
		gameOverTexture = new Texture(Gdx.files.internal("data/neon/gameover.png"));
		gameOverTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		gameOver.getRegionFromTexture(gameOverTexture, 0, 0, gameOverTexture.getWidth(), gameOverTexture.getHeight());
		gameOverSprite = gameOver.createSprite(new Vector2(w/1.25f,h/3.75f), new Vector2(w/2-(w/1.25f)/2,h-(h/4)));

		playAgainTexture = new Texture(Gdx.files.internal("data/neon/play.png"));
		playAgainTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		playAgain.getRegionFromTexture(playAgainTexture, 0, 0, playAgainTexture.getWidth(), playAgainTexture.getHeight());
		playAgainSprite = playAgain.createSprite(new Vector2(w/2.5f,h/6.25f),new Vector2(w/2-(w/2.5f)/2,(h/2)));
	
		backToMenuTexture = new Texture(Gdx.files.internal("data/neon/back.png"));
		backToMenuTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		backToMenu.getRegionFromTexture(backToMenuTexture, 0, 0, backToMenuTexture.getWidth(), backToMenuTexture.getHeight());
		backToMenuSprite = backToMenu.createSprite(new Vector2(w/2.5f,h/6.25f), new Vector2(w/2-(w/2.5f)/2,(h/3.5f)));
		
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//bloom.blurPasses = -1;
		
		batch.begin();
		
		gameOverSprite.draw(batch);
		playAgainSprite.draw(batch);
		backToMenuSprite.draw(batch);
		font.setColor(0.56f, .75f, .01f, 1.0f);
		font.draw(batch,"Your Score : "+score.toString(),w/2-(w/2.5f)/2,(h/3.5f));
		batch.end();
	

		if(Gdx.input.justTouched()) {
	         Vector3 touchPos = new Vector3();
	         touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
	        if((touchPos.x > playAgainSprite.getX()) && (touchPos.x < playAgainSprite.getX()+playAgainSprite.getWidth())
	        		 &&(h-touchPos.y > playAgainSprite.getY()) && (h-touchPos.y < playAgainSprite.getY() + playAgainSprite.getHeight())){
	        	 
	        	myGame.setScreen(new Loading(myGame));
		         
	        }
	        
	        if((touchPos.x > backToMenuSprite.getX()) && (touchPos.x < backToMenuSprite.getX()+backToMenuSprite.getWidth())
	        		 &&(h-touchPos.y > backToMenuSprite.getY()) && (h-touchPos.y < backToMenuSprite.getY() + backToMenuSprite.getHeight())){
	        	 myGame.setScreen(new MainMenu(myGame));
		         
	        }
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
			gameOverTexture.dispose();
			playAgainTexture.dispose();

	}

	public void setCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
	}

}
