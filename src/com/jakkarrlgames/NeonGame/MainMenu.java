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

public class MainMenu implements Screen,Macros {
	static boolean MUSICSWITCH = true;
	Game myGame;
	Entity menu;
	Texture menuTexture;
	Sprite menuSprite;
	Entity play;
	Texture playTexture;
	Sprite playSprite;
	SpriteBatch batch;
	Entity help;
	Texture helpTexture;
	Sprite helpSprite;
	Entity exit;
	Texture exitTexture;
	Sprite exitSprite;
	Entity on;
	Texture onTexture;
	Sprite onSprite;
	Entity off;
	Texture offTexture;
	Sprite offSprite;
	Music music;
	OrthographicCamera camera;
	float w,h;
	int size;
	public MainMenu(Game game) {
		myGame = game;
	}

	
	@Override
	public void show() {
		 w = Gdx.graphics.getWidth();
		 h = Gdx.graphics.getHeight();
		 setCamera();
		menu = new Entity();
		music = Gdx.audio.newMusic(Gdx.files.internal("data/neon/Menu_loop.mp3"));
		batch = new SpriteBatch();
		menuTexture = new Texture(Gdx.files.internal("data/neon/name.png"));
		menuTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		menu.getRegionFromTexture(menuTexture, 0, 0, menuTexture.getWidth(), menuTexture.getHeight());
		menuSprite = menu.createSprite(new Vector2(w/1.25f,h/3.75f), new Vector2(w/2-(w/1.25f)/2,h-((h/4))));
		
		
		play = new Entity();
		playTexture = new Texture(Gdx.files.internal("data/neon/play1.png"));
		playTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		play.getRegionFromTexture(playTexture, 0, 0, playTexture.getWidth(), playTexture.getHeight());
		playSprite = play.createSprite(new Vector2(w/2.5f,h/6.25f), new Vector2(w/2-(w/2.5f)/2,(h/2)));
		
		help = new Entity();
		helpTexture = new Texture(Gdx.files.internal("data/neon/help1.png"));
		helpTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		help.getRegionFromTexture(helpTexture, 0, 0, helpTexture.getWidth(), helpTexture.getHeight());
		helpSprite = help.createSprite(new Vector2(w/2.5f,h/6.25f), new Vector2(w/2-(w/2.5f)/2,(h/2.5f)));
		
		exit = new Entity();
		exitTexture = new Texture(Gdx.files.internal("data/neon/exit.png"));
		exitTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		exit.getRegionFromTexture(exitTexture, 0, 0, exitTexture.getWidth(), exitTexture.getHeight());
		exitSprite = exit.createSprite(new Vector2(w/2.5f,h/6.25f), new Vector2(w/2-(w/2.5f)/2,(h/3.5f)));
		
		off = new Entity();
		offTexture = new Texture(Gdx.files.internal("data/neon/off.png"));
		offTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		off.getRegionFromTexture(offTexture, 0, 0, offTexture.getWidth(), offTexture.getHeight());
		offSprite = off.createSprite(new Vector2(w/10,h/7.5f), new Vector2(w-(w/9f),(0)));
		
		on = new Entity();
		onTexture = new Texture(Gdx.files.internal("data/neon/on.png"));
		onTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		on.getRegionFromTexture(onTexture, 0, 0, onTexture.getWidth(), onTexture.getHeight());
		onSprite = on.createSprite(new Vector2(w/10,h/7.5f), new Vector2(w-(w/9f),(0)));
		//if(MUSICSWITCH) {
		//music.play();
		//music.setLooping(true);
		//}
		}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		menuSprite.draw(batch);
		playSprite.draw(batch);
		helpSprite.draw(batch);
		exitSprite.draw(batch);
		offSprite.draw(batch);
		if(MUSICSWITCH)
			onSprite.draw(batch);
		
		batch.end();
		if(Gdx.input.justTouched()) {
	         Vector3 touchPos = new Vector3();
	         touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
	        if((touchPos.x > playSprite.getX()) && (touchPos.x < playSprite.getX()+playSprite.getWidth())
	        		 &&(h-touchPos.y > playSprite.getY()) && (h-touchPos.y < playSprite.getY() + playSprite.getHeight())){
	        	 myGame.setScreen(new Loading(myGame));
	        }
	        if((touchPos.x > helpSprite.getX()) && (touchPos.x < helpSprite.getX()+helpSprite.getWidth())
		        		 &&(h-touchPos.y > helpSprite.getY()) && (h-touchPos.y < helpSprite.getY() + helpSprite.getHeight())){
		        	 myGame.setScreen(new Help(myGame));
	        
	        	}	
	       if((touchPos.x > exitSprite.getX()) && (touchPos.x < exitSprite.getX()+exitSprite.getWidth())
	        		 &&(h-touchPos.y > exitSprite.getY()) && (h-touchPos.y < exitSprite.getY() + exitSprite.getHeight())){
	        	 Gdx.app.exit();
       
	        	}
	       if((touchPos.x > offSprite.getX()) && (touchPos.x < offSprite.getX()+offSprite.getWidth())
	        		 &&(h-touchPos.y > offSprite.getY()) && (h-touchPos.y < offSprite.getY() + offSprite.getHeight())){
	        	 if(MUSICSWITCH)
	        		 MUSICSWITCH = false;
	        	 else
	        		 MUSICSWITCH = true;
     
	        	} 
	       }
		try {
		if(MUSICSWITCH) {
			
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
		menu = null;
		menuTexture.dispose();
		play = null;
		playTexture.dispose();
		exit = null;
		exitTexture.dispose();
		/*off = null;
		offTexture.dispose();
		on = null;
		onTexture.dispose();*/
		help = null;
		helpTexture.dispose();
		music.dispose();
		batch.dispose();
	
	}
	public void setCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
	}

}
