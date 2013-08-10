package com.jakkarrlgames.NeonGame;

import java.util.Vector;
import java.util.Random;

import bloom.Bloom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class GamePlay implements Screen {
	Bloom bloom;
	public static int B2P = 32;
	private final int BOXCOUNT = 10;
	private final int BALLCOUNT = 10;
	private final int SPIKECOUNT = 10;
	private float BOXHALFWIDTH;
	private float BOXHALFHEIGHT;
	private float BALLRADIUS;
	private float SPIKERADIUS;
	public static boolean paused = false;
	private OrthographicCamera camera;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private SpriteBatch scoreBatch;
	private Texture shipTexture;
	private Texture pullTexture;
	private Texture pauseTexture;
	private Texture boxTexture;
	private Texture ballTexture;
	private Texture skyTexture;
	private Texture spikeTexture;
	private Sprite shipSprite;
	private Sprite pullSprite;
	private Sprite pauseSprite;
	private boolean start;
	private boolean finish;
	float startPos;
	private Vector<Sprite> boxSprite;
	private Vector<Sprite>ballSprite;
	private Vector<Sprite>spikeSprite;
	Box2DDebugRenderer debugRenderer;
	BitmapFont font;
	Entity ship;
	Entity pull;
	Entity pause;
	Vector <Entity> box;
	Vector <Entity> ball;
	Vector <Entity> spike;
	Entity sky;
	World world;
	Integer score;
	float w,h;
	int cRX = 0;
	int cRX1 = 0;
	int cRX2 = 0;
	int cRY = 0;
	int cRY1 = 0;
	int cRY2 = 0;
	int pRX = 0;
	int pRX1 = 0;
	int pRX2 = 0;
	int pRY = 0;
	int pRY1 = 0;
	int pRY2 = 0;
	Random rand;
	Game myGame;
	Music music;
	int i,j;
	public GamePlay(Game g) {
		myGame = g;

	}

	@Override
	public void show() {
		rand = new Random();
		if(Gdx.graphics.getGL20()!= null)
		bloom = new Bloom();
		music = Gdx.audio.newMusic(Gdx.files.internal("data/neon/Theme Menu.mp3"));
		 w = Gdx.graphics.getWidth()/B2P;
		 h = Gdx.graphics.getHeight()/B2P;
		 BOXHALFWIDTH = w/10;
		 BOXHALFHEIGHT = w/10;
		 BALLRADIUS = w/10;
		 SPIKERADIUS = w/10;
		 start = false;
		 finish = false;
		 score = 0;
		 paused = false;
		 debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0,0),true);
		camera = setCamera(camera,w,h);
		cam = setCamera(cam,w*B2P,h*B2P);
		font = new BitmapFont(Gdx.files.internal("data/neon/font.fnt"),
				Gdx.files.internal("data/neon/font_0.png"), false);
		
		batch = new SpriteBatch();
		scoreBatch = new SpriteBatch();
		ship = new Entity(world);
		pull = new Entity();
		pause = new Entity();
		box = new Vector<Entity>(BOXCOUNT);
		ball = new Vector<Entity>(BALLCOUNT);
		spike = new Vector<Entity>(SPIKECOUNT);
		boxSprite = new Vector<Sprite>(BOXCOUNT);
		ballSprite = new Vector<Sprite>(BALLCOUNT);
		spikeSprite = new Vector<Sprite>(SPIKECOUNT);
		shipTexture = neonGame.manager.get("data/neon/0.png", Texture.class);
		ship.getRegionFromTexture(shipTexture, 0, 0, 2048,2048);
		ship.createCirclePhysics(world, BodyType.DynamicBody, new Vector2(100,h/2),w/13.33f,1,0,0);
		shipSprite = ship.createSprite(new Vector2(w/6.66f,w/6.66f),new Vector2(ship.body.getPosition().x-(w/13.33f),ship.body.getPosition().y-(w/13.33f)));
		
		pullTexture = neonGame.manager.get("data/neon/pull.png", Texture.class);
		pull.getRegionFromTexture(pullTexture, 0, 0, 1024,1024);
		pullSprite = pull.createSprite(new Vector2(w*B2P/7,w*B2P/7),new Vector2(w*B2P-(w*B2P/7),0));
	
		pauseTexture = neonGame.manager.get("data/neon/pause.png", Texture.class);
		pause.getRegionFromTexture(pauseTexture, 0, 0, 1024,1024);
		pauseSprite = pause.createSprite(new Vector2(w*B2P/7,w*B2P/7),new Vector2(w*B2P-(w*B2P/7),h*B2P-(w*B2P/7)));
		
		startPos = ship.body.getPosition().x;
		boxTexture = neonGame.manager.get("data/neon/box.png", Texture.class);
		ballTexture =neonGame.manager.get("data/neon/greenball.png", Texture.class);
		spikeTexture = neonGame.manager.get("data/neon/spike.png", Texture.class);
		spikeTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		cRX = (int)w*5;
		ship.body.setUserData(1);
		for(i = 0;i<BOXCOUNT;i++) {
			do {
				cRX += rand.nextInt((int)w);
			
				cRY = rand.nextInt((int)h-(int)(h/3.75f));
				}
				while((cRX < pRX+(w/2)));
			
				Entity temp = new Entity(world);
			
			switch(rand.nextInt(3)) {
			case 0:
			{
				box.add(temp);
				
				box.get(box.lastIndexOf(temp)).getRegionFromTexture(boxTexture, 0, 0,1024, 1024);
				
				
				box.get(box.lastIndexOf(temp)).createCirclePhysics(world, BodyType.StaticBody,new Vector2(cRX,cRY),BALLRADIUS, 1000, 0, 0);
				
				pRX = cRX;
				cRY = pRY; 
				boxSprite.add(box.get(box.lastIndexOf(temp)).createSprite(new Vector2(BALLRADIUS*2,BALLRADIUS*2), new Vector2(box.get(box.lastIndexOf(temp)).body.getPosition().x-BALLRADIUS,box.get(box.lastIndexOf(temp)).body.getPosition().y-BALLRADIUS)));
				break;
				}
				
			
			case 1:
			{
			
				box.add(temp);
				box.get(box.lastIndexOf(temp)).getRegionFromTexture(ballTexture, 0, 0, 1024, 1024);
				
				box.get(box.lastIndexOf(temp)).createCirclePhysics(world, BodyType.DynamicBody,new Vector2(cRX,cRY),BALLRADIUS, 1000, 0, 0);
				
				pRX = cRX;
				cRY = pRY; 
				boxSprite.add(box.get(box.lastIndexOf(temp)).createSprite(new Vector2(BALLRADIUS*2,BALLRADIUS*2), new Vector2(box.get(box.lastIndexOf(temp)).body.getPosition().x-BALLRADIUS,box.get(box.lastIndexOf(temp)).body.getPosition().y-BALLRADIUS)));
				break;
				
			}
			case 2:
			{
			
				box.add(temp);
				box.get(box.lastIndexOf(temp)).getRegionFromTexture(spikeTexture, 0, 0, 1024, 1024);
				
				box.get(box.lastIndexOf(temp)).createCirclePhysics(world, BodyType.DynamicBody,new Vector2(cRX,cRY),SPIKERADIUS, 1000, 0, 0);
				
				pRX = cRX;
				cRY = pRY; 
				boxSprite.add(box.get(box.lastIndexOf(temp)).createSprite(new Vector2(SPIKERADIUS*2,SPIKERADIUS*2), new Vector2(box.get(box.lastIndexOf(temp)).body.getPosition().x-SPIKERADIUS,box.get(box.lastIndexOf(temp)).body.getPosition().y-SPIKERADIUS)));
				break;
				
			}
				}
			}
	
		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				Integer a = (Integer)contact.getFixtureA().getBody().getUserData();
				Integer b = (Integer)contact.getFixtureB().getBody().getUserData();

				if ( a != b )
				if(start) {
				finish = true;
				
				}
	
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}

     
   });
		}
	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//debugRenderer.render(world, camera.combined);
		update();
		batch.setProjectionMatrix(camera.combined);
	    
		if(Gdx.graphics.getGL20()!= null)
		bloom.capture();
		
		batch.begin();
		shipSprite.draw(batch);
		
		
		for(i=0;i<box.size();i++) {
		boxSprite.get(i).draw(batch);
		boxSprite.get(i).setPosition(box.get(i).body.getPosition().x-SPIKERADIUS, box.get(i).body.getPosition().y-SPIKERADIUS);
		boxSprite.get(i).rotate(box.get(i).body.getAngle()*57.2957795f);
		box.get(i).body.applyTorque(w*B2P);
		box.get(i).body.setGravityScale(0);
		}	
		shipSprite.setPosition(ship.body.getPosition().x-(w/13.33f),ship.body.getPosition().y-(w/13.33f));
		score = (int) (ship.body.getPosition().x-startPos);
		shipSprite.setRotation(ship.body.getAngle()*57.2957795f);
		
		batch.end();
		
		cam.update();
		scoreBatch.setProjectionMatrix(cam.combined);
	
		
		scoreBatch.begin();
		font.setColor(0.56f, .75f, .01f, 1.0f);
		font.draw(scoreBatch,"Score : "+score.toString(),0,(h*B2P)-20);
		if(!start)
			font.draw(scoreBatch, "Touch PULL SHIP to start", w*B2P/3,h*B2P/3);
		pullSprite.draw(scoreBatch);
		pauseSprite.draw(scoreBatch);
		scoreBatch.end();
		if(Gdx.graphics.getGL20()!= null)
		bloom.render();
		camera.position.x = ship.body.getPosition().x + camera.viewportWidth/2-shipSprite.getWidth();
		camera.update();
	}

	private void update() {
		if(paused)
			pause();
		if((start)&&(!paused)) {
		ship.body.setLinearVelocity(new Vector2(w,0));
		for(i = 0;i<box.size();i++) {
		if(box.get(i).body.getPosition().x <ship.body.getPosition().x)
		{
			do {
				cRX += rand.nextInt((int)w);
				
				cRY = rand.nextInt((int)h-(int)(h/3.75f));
				}
				while((cRX < pRX+(w/2)));
			
			
			box.get(i).body.setTransform(new Vector2(cRX,cRY),0);
			pRX = cRX;
			cRY = pRY; 
		}
		}
		}
		
		if(Gdx.input.isTouched()) {
			
	         Vector3 touchPos = new Vector3();
	         touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
	    
	         if((touchPos.x > pullSprite.getX()) && (touchPos.x < pullSprite.getX()+pullSprite.getWidth())
	        		 &&(h*B2P-touchPos.y > pullSprite.getY()) && (h*B2P-touchPos.y < pullSprite.getY() + pullSprite.getHeight())){
	        	 if(!paused) {
	        	 if(!start) {
	 				start = true;
	 				world.setGravity(new Vector2(0,-(w*B2P/1.6f)));
	 			}
	        	 
	         ship.body.setLinearVelocity(w,h*1.25f);
	         pullSprite.setScale(.8f, .8f);
	        	 }
	         }
		}
		if(Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
	         if((touchPos.x > pauseSprite.getX()) && (touchPos.x < pauseSprite.getX()+pauseSprite.getWidth())
	        		 &&(h*B2P-touchPos.y > pauseSprite.getY()) && (h*B2P-touchPos.y < pauseSprite.getY() + pauseSprite.getHeight())){
	        	 if(!paused)
	        		 paused = true;
	        	 else
	        		 paused = false;
	        		pauseSprite.setScale(1f,1f);
	        	 	ship.body.setGravityScale(1);
	        		
	        	 	
	         }
		}
		if(!(Gdx.input.isTouched())) {
		pullSprite.setScale(1.0f,1.0f);
		
		}
		
		if((shipSprite.getY()+shipSprite.getHeight()<0)||(shipSprite.getY()>h)) {
			finish = true;
		}
		world.step(1/60f, 8, 3);
		if(finish)
			myGame.setScreen(new GameOver(myGame,score));
		playMusic();
			 
	}	
	

	private void playMusic() {
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
		dispose();
	}

	@Override
	public void pause() {
		
       	 ship.body.setGravityScale(0);
       	pauseSprite.setScale(.8f,.8f);
       	ship.body.setLinearVelocity(new Vector2(0,0));

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		neonGame.manager.dispose();
		music.dispose();
		world.dispose();
	

	}

	public OrthographicCamera setCamera(OrthographicCamera camera,float w,float h) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		return camera;
	}

}



