package com.mygdx.Dextero.android;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.Dextero.Dextero;
import com.mygdx.Dextero.sqlite.DatabaseHelper;
import com.mygdx.androidinterface.ActionResolver;


public class AndroidLauncher extends AndroidApplication implements
		ActionResolver {
	
	/*Some code was followed to interface from LibGDX to Native Android in order to 
	 * use Android libraries: 
	 * 
	 * The showMessage() method uses the code from the blog in order to show toast messages
	 * 
	 * http://carlorodriguez.github.io/blog/2014/10/05/android-platform-specific-code-with-libgdx/
	 * 
	 * */

	Handler handler;

	private Dextero dextero;

	private Intent speechIntent;

	private SpeechRecognizer recognizer;

	private int check = 1;

	private ArrayList<String> responses = new ArrayList<String>();

	private DatabaseHelper dbhelper;

	private Context context;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Gdx.app.log("Are we here yet?", "are we here?");

		try{
		/*goes through the gathered results of the player's response
		 * and makes each response a lower case */
		for (int i = 0; i < data.getStringArrayListExtra(
				(RecognizerIntent.EXTRA_RESULTS)).size(); i++) {
			data.getStringArrayListExtra((RecognizerIntent.EXTRA_RESULTS))
					.get(i).toLowerCase();
		}
		//assigns the array of lower case responses to a global variable
		responses = data
				.getStringArrayListExtra((RecognizerIntent.EXTRA_RESULTS));

		/*goes through each index of the lower case results and checks to see
		 * whether it is exact to the active phrase*/
		for (int i = 0; i < responses.size(); i++) {
			if (responses.get(i).equals(
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getPhrasesLst()[this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getRandomPhrase()])) {
				System.out.println("Correct word!");

				this.dextero.getSplashScreen().getGameScreen().getWorld()
						.setIsSpeechCorrect(1);

				/*sets a new phrase for the player to see which is different
				 * from the one that was just scored
				 */
				int prevPhrase = this.dextero.getSplashScreen().getGameScreen()
						.getWorld().getRandomPhrase();
				this.dextero.getSplashScreen().getGameScreen().getWorld()
						.setRandomPhrase();
				if (this.dextero.getSplashScreen().getGameScreen().getWorld()
						.getRandomPhrase() == prevPhrase) {
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.setRandomPhrase();
				}

				i = responses.size();
				/* if after going through all the results and none of these
				 * match the active phrase, then isSpeechCorrect variable inside 
				 * GameWorld class is set to 0 which means false
				 */
			} else if ((i == responses.size() - 1)
					&& !(responses.get(i)
							.equals(this.dextero.getSplashScreen()
									.getGameScreen().getWorld().getPhrasesLst()[this.dextero
									.getSplashScreen().getGameScreen()
									.getWorld().getRandomPhrase()]))) {
				this.dextero.getSplashScreen().getGameScreen().getWorld()
						.setIsSpeechCorrect(0);
			}
		}
		/*if any exception occurs then a toast message is shown, prompting
		 * the player to try again*/
		
		} catch(Exception e){
			this.showMessage("Something went wrong, please try again.");
		}
		/*the speechIntent is dereferenced along with the data array and responses array is cleared
		 * preventing conflicts from previous results
		 */
		speechIntent = null;
		data = null;
		responses.clear();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		handler = new Handler();
		String dbname = "Highscores.db";
		//dbpath = getDatabasePath(dbname);
		this.context = this;

		dbhelper = new DatabaseHelper(this, null, null, 1);

		initialize(this.dextero = new Dextero(this), config);

	}

	@Override
	public void implementVoiceRecognizer() {

		/*speech intent object is created which is given the 
		 * responsibility to check the player's verbal response
		 */
		speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

		//a prompt is shown to the player for them to speak
		speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
		
		setResult(Activity.RESULT_OK, speechIntent);
	
		/*if mobile data/WIFI of the device is on then indirectly fire
		 * onActivityResult() to check whether any of the results are 
		 * equivalent to the active phrase
		 */
		if(isOnline()){
		startActivityForResult(speechIntent, check);
		}else{
			/*otherwise show a toast message prompting the player to turn on
			 * mobile data/wifi 
			 */
			this.showMessage("Please turn on your WIFI or mobile data.");
		
		}
	}
	
	//shows a toast message
	public void showMessage(final CharSequence chr){
		handler.post(new Runnable(){
			@Override
			public void run() {
			Toast.makeText(context, chr, Toast.LENGTH_SHORT).show();
				
			}
			
		});
	}
//checks whether mobile data/wifi is on
	public boolean isOnline() {
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo == null) {
	        return false;
	    }
	    return true;
	}

	@Override
	public void insertHighscore() {

		if (this.dextero.getSplashScreen().getGameScreen().getWorld()
				.isEasyDexMode()) {
			dbhelper.setEasyHighscore(this.dextero.getSplashScreen()
					.getGameScreen().getWorld().getTotalScore(), this.dextero
					.getSplashScreen().getGameScreen().getWorld().getF1Score(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF2Score(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF3Score(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF4Score(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF1Errors(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF2Errors(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF3Errors(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF4Errors());
		} else if (this.dextero.getSplashScreen().getGameScreen().getWorld()
				.isMediumDexMode()) {
			dbhelper.setMediumHighscore(this.dextero.getSplashScreen()
					.getGameScreen().getWorld().getTotalScore(), this.dextero
					.getSplashScreen().getGameScreen().getWorld().getF1Score(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF2Score(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF3Score(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF4Score(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF1Errors(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF2Errors(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF3Errors(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF4Errors());
		} else if (this.dextero.getSplashScreen().getGameScreen().getWorld()
				.isHardDexMode()) {
			dbhelper.setHardHighscore(this.dextero.getSplashScreen()
					.getGameScreen().getWorld().getTotalScore(), this.dextero
					.getSplashScreen().getGameScreen().getWorld().getF1Score(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF2Score(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF3Score(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF4Score(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF1Errors(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF2Errors(), this.dextero.getSplashScreen()
							.getGameScreen().getWorld().getF3Errors(),
					this.dextero.getSplashScreen().getGameScreen().getWorld()
							.getF4Errors());
		} else if(this.dextero.getSplashScreen().getGameScreen().getWorld().isSpeechModeOver()){
			dbhelper.setSpeechHS(this.dextero.getSplashScreen().getGameScreen().getWorld().getSpeechScore());
		}

		this.dextero.getSplashScreen().getGameScreen().getWorld()
				.setHighscoreUpdated(true);
	}

	@Override
	public int[][] getHighscoreLst() {
		if (this.dextero.getSplashScreen().getGameScreen().getWorld()
				.isEasyDexMode()) {
			this.dextero.getSplashScreen().getGameScreen().getWorld().setNewHighscore(dbhelper.getNewHighscore());
		
			
			return dbhelper.getEasyScores();
			
		} else if (this.dextero.getSplashScreen().getGameScreen().getWorld()
				.isMediumDexMode()) {
			this.dextero.getSplashScreen().getGameScreen().getWorld().setNewHighscore(dbhelper.getNewHighscore());

			return dbhelper.getMediumScores();
		} else if (this.dextero.getSplashScreen().getGameScreen().getWorld()
				.isHardDexMode()) {
			this.dextero.getSplashScreen().getGameScreen().getWorld().setNewHighscore(dbhelper.getNewHighscore());

			return dbhelper.getHardScores();
		} 
		
		return new int[10][6];
	}
	
	@Override
	public int[] getSpeechHsLst() {
		if (this.dextero.getSplashScreen().getGameScreen().getWorld().isSpeechModeOver()){
			this.dextero.getSplashScreen().getGameScreen().getWorld().setNewSpeechHs(dbhelper.getNewSpeechHs());
			
			return dbhelper.getSpeechScores();
		}
		return new int[10];
	}

	public SpeechRecognizer getRecognizer() {
		return recognizer;
	}

	public Intent getSpeech() {
		return speechIntent;
	}

	public void setSpeech(Intent n) {
		speechIntent = n;
	}

	public void setResponses(ArrayList<String> arr) {
		responses = arr;
	}

	public ArrayList<String> getResponses() {
		return responses;
	}

	public Dextero getDextero() {
		return dextero;
	}
	
	

}
