package com.mygdx.androidinterface;


public interface ActionResolver {
	
	/*Some code was followed to interface from LibGDX to Native Android in order to 
	 * use Android libraries: 
	 * 
	 * There was no direct copying of the contents from the blog with this class
	 * 
	 * http://carlorodriguez.github.io/blog/2014/10/05/android-platform-specific-code-with-libgdx/
	 * 
	 * */
	
	public void implementVoiceRecognizer();
	
	public void insertHighscore();
	
	public int[][] getHighscoreLst();
	
	public int[] getSpeechHsLst();

}







