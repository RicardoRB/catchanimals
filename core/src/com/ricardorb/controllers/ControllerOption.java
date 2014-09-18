package com.ricardorb.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class ControllerOption {
	
	private static boolean barFinger;
	private static boolean musicOn;
	private static boolean effectsOn;
	private static Preferences prefs;
	
	
	public static void load(){
		
		prefs = Gdx.app.getPreferences("options");
		
		if(!prefs.getBoolean("notFirstTime")){
			setBarFinger(false);
			setEffectsOn(true);
			setMusicOn(true);
			setNotFirstTime(true);
		}
		
		barFinger = prefs.getBoolean("barFinger");
		musicOn = prefs.getBoolean("musicOn");
		effectsOn = prefs.getBoolean("effectsOn");
	}
	

	public static boolean isBarFinger() {
		return barFinger;
	}


	public static void setBarFinger(boolean barFinger) {
		ControllerOption.barFinger = barFinger;
		prefs.putBoolean("barFinger", barFinger);
		prefs.flush();
	}


	public static boolean isMusicOn() {
		return musicOn;
	}


	public static void setMusicOn(boolean musicOn) {
		ControllerOption.musicOn = musicOn;
		prefs.putBoolean("musicOn", musicOn);
		prefs.flush();
	}


	public static boolean isEffectsOn() {
		return effectsOn;
	}


	public static void setEffectsOn(boolean effectsOn) {
		ControllerOption.effectsOn = effectsOn;
		prefs.putBoolean("effectsOn", effectsOn);
		prefs.flush();
	}
	
	public static void setNotFirstTime(boolean notFirstTime) {
		prefs.putBoolean("notFirstTime", notFirstTime);
		prefs.flush();
	}
	
	public static void playSound(Sound sound){
		if(isEffectsOn()){
			sound.play();
		}
	}
	
}
