package com.jakkarrlgames.NeonGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;



public class neonGame extends Game {

	public static AssetManager manager;
	public int a;
	@Override
	public void create() {
		
		this.setScreen(new Intro(this));
		
	}
}