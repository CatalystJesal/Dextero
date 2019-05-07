package com.mygdx.Dextero.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	
//	/data/data/com.mygdx.Dextero.android/databases/Highscores.db
	public static final String databaseName = "Highscores.db";
	
	public static final String  tbl_easyDextero = "hs_easy_dextero";
	public static final String tbl_mediumDextero = "hs_medium_dextero";
	public static final String tbl_hardDextero = "hs_hard_dextero";
	public static final String tbl_speech = "speech";
	
	public static final String column_1 = "ID";
	public static final String column_2 = "Total_Score";
	public static final String column_3 = "F1_Score";
	public static final String column_4 = "F2_Score";
	public static final String column_5 = "F3_Score";
	public static final String column_6 = "F4_Score";
	public static final String column_7 = "Total_Errors";
	
	private int[] speechHsLst = new int[10];
	
	private int newSpeechHs = 0;
	
	private int[] newDexterityHs = new int[6];
	
	private int[][] dexterityHsLst = new int[10][6];
	
	Context context;
	
	private static final int db_version = 1;
	
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, databaseName, null, db_version);
		SQLiteDatabase  db = this.getWritableDatabase();
		this.context = context;
	}


	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
		Log.e("Database Created!", "Database Created!");
		arg0.execSQL("CREATE TABLE " + tbl_easyDextero + " (ID INTEGER, Total_Score INTEGER, F1_Score INTEGER, F2_Score INTEGER, F3_Score INTEGER, F4_Score INTEGER, Total_Errors INTEGER, PRIMARY KEY(ID));");
		arg0.execSQL("CREATE TABLE " + tbl_mediumDextero + " (ID INTEGER, Total_Score INTEGER, F1_Score INTEGER, F2_Score INTEGER, F3_Score INTEGER, F4_Score INTEGER, Total_Errors INTEGER, PRIMARY KEY(ID));");
		arg0.execSQL("CREATE TABLE " + tbl_hardDextero + " (ID INTEGER, Total_Score INTEGER, F1_Score INTEGER, F2_Score INTEGER, F3_Score INTEGER, F4_Score INTEGER, Total_Errors INTEGER, PRIMARY KEY(ID));");
		
		arg0.execSQL("CREATE TABLE " + tbl_speech + " (ID INTEGER, Total_Score INTEGER, PRIMARY KEY(ID));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.e("Database Upgrade!", "Database Upgrade!");

		arg0.execSQL("DROP TABLE IF EXISTS tbl_easyDextero;");
		arg0.execSQL("DROP TABLE IF EXISTS tbl_mediumDextero;");
		arg0.execSQL("DROP TABLE IF EXISTS tbl_hardDextero;");
		arg0.execSQL("DROP TABLE IF EXISTS speech_easy");
	
		onCreate(arg0);
		
	}
	
	public int[][] getEasyScores(){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		dexterityHsLst = null;
		
		dexterityHsLst = new int[10][6];
		
		Cursor res = db.rawQuery("SELECT * FROM (SELECT * FROM " + tbl_easyDextero + " ORDER BY " + column_2 + " DESC, " + column_7 + " ASC) T", null);
		
		int numberOfRecords = res.getCount();
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}
		
		res.moveToFirst();

		for(int i=0; i< res.getCount(); i++){
			
			dexterityHsLst[i][0] = res.getInt(res.getColumnIndex(column_2));
			dexterityHsLst[i][1] = res.getInt(res.getColumnIndex(column_3));
			dexterityHsLst[i][2] = res.getInt(res.getColumnIndex(column_4));
			dexterityHsLst[i][3] = res.getInt(res.getColumnIndex(column_5));
			dexterityHsLst[i][4] = res.getInt(res.getColumnIndex(column_6));
			dexterityHsLst[i][5] = res.getInt(res.getColumnIndex(column_7));
			

			res.moveToNext();
		}
			

		
		return dexterityHsLst;
	}
	
	public int[][] getMediumScores(){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		dexterityHsLst = null;
		
		dexterityHsLst = new int[10][6];
		
		Cursor res = db.rawQuery("SELECT * FROM (SELECT * FROM " + tbl_mediumDextero + " ORDER BY " + column_2 + " DESC, " + column_7 + " ASC) T", null);
		
		int numberOfRecords = res.getCount();
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}
		
		res.moveToFirst();

	
		for(int i=0; i< res.getCount(); i++){
			
			dexterityHsLst[i][0] = res.getInt(res.getColumnIndex(column_2));
			dexterityHsLst[i][1] = res.getInt(res.getColumnIndex(column_3));
			dexterityHsLst[i][2] = res.getInt(res.getColumnIndex(column_4));
			dexterityHsLst[i][3] = res.getInt(res.getColumnIndex(column_5));
			dexterityHsLst[i][4] = res.getInt(res.getColumnIndex(column_6));
			dexterityHsLst[i][5] = res.getInt(res.getColumnIndex(column_7));
			
			res.moveToNext();
		}
		
		return dexterityHsLst;
	}
	
	public int[][] getHardScores(){
		SQLiteDatabase  db = this.getWritableDatabase();

		dexterityHsLst = null;
		
		dexterityHsLst = new int[10][6];
		
		Cursor res = db.rawQuery("SELECT * FROM (SELECT * FROM " + tbl_hardDextero + " ORDER BY " + column_2 + " DESC, " + column_7 + " ASC) T", null);
		
		int numberOfRecords = res.getCount();
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}
		
		res.moveToFirst();

		for(int i=0; i< res.getCount(); i++){
			

			dexterityHsLst[i][0] = res.getInt(res.getColumnIndex(column_2));
			dexterityHsLst[i][1] = res.getInt(res.getColumnIndex(column_3));
			dexterityHsLst[i][2] = res.getInt(res.getColumnIndex(column_4));
			dexterityHsLst[i][3] = res.getInt(res.getColumnIndex(column_5));
			dexterityHsLst[i][4] = res.getInt(res.getColumnIndex(column_6));
			dexterityHsLst[i][5] = res.getInt(res.getColumnIndex(column_7));
			
	
			res.moveToNext();
		}
			

		
		return dexterityHsLst;
	}
	
	public boolean insertEasyHscore(int totalScore, int f1score, int f2score, int f3score, int f4score, int totalerrors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		
		ContentValues cv = new ContentValues();
		cv.put(column_2, totalScore);
		cv.put(column_3, f1score);
		cv.put(column_4, f2score);
		cv.put(column_5, f3score);
		cv.put(column_6, f4score);
		cv.put(column_7, totalerrors);
		long result = db.insert(tbl_easyDextero, null, cv);
		
		if(result == -1){
			Log.e("Insert method not working!", "insert not working!");
			return false;
		}
		Log.e("Insert method works!", "insert statement works!");
		
		newDexterityHs[0] = totalScore;
		newDexterityHs[1] = f1score;
		newDexterityHs[2] = f2score;
		newDexterityHs[3] = f3score;
		newDexterityHs[4] = f4score;
		newDexterityHs[5] = totalerrors; 
		

		return true;
	}
	
	public boolean insertMediumHscore(int totalScore, int f1score, int f2score, int f3score, int f4score, int totalerrors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
	
		ContentValues cv = new ContentValues();
		cv.put(column_2, totalScore);
		cv.put(column_3, f1score);
		cv.put(column_4, f2score);
		cv.put(column_5, f3score);
		cv.put(column_6, f4score);
		cv.put(column_7, totalerrors);
		long result = db.insert(tbl_mediumDextero, null, cv);
		
		if(result == -1){
			Log.e("Insert method not working!", "insert not working!");
			return false;
		}
		Log.e("Insert method works!", "insert statement works!");
		
		newDexterityHs[0] = totalScore;
		newDexterityHs[1] = f1score;
		newDexterityHs[2] = f2score;
		newDexterityHs[3] = f3score;
		newDexterityHs[4] = f4score;
		newDexterityHs[5] = totalerrors;

		return true;
	}
	
	
	public boolean insertHardHscore(int totalScore, int f1score, int f2score, int f3score, int f4score, int totalerrors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		
		ContentValues cv = new ContentValues();
		cv.put(column_2, totalScore);
		cv.put(column_3, f1score);
		cv.put(column_4, f2score);
		cv.put(column_5, f3score);
		cv.put(column_6, f4score);
		cv.put(column_7, totalerrors);
		long result = db.insert(tbl_hardDextero, null, cv);
		
		if(result == -1){
			Log.e("Insert method not working!", "insert not working!");
			return false;
		}
		Log.e("Insert method works!", "insert statement works!");
		
		newDexterityHs[0] = totalScore;
		newDexterityHs[1] = f1score;
		newDexterityHs[2] = f2score;
		newDexterityHs[3] = f3score;
		newDexterityHs[4] = f4score;
		newDexterityHs[5] = totalerrors;

		return true;
	}
	
	

	public Cursor setEasyHighscore(int totalScore,int f1score, int f2score, int f3score, int f4score, int f1errors, int f2errors, int f3errors, int f4errors){
		SQLiteDatabase  db = this.getWritableDatabase();

		Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + tbl_easyDextero, null);
		
		res.moveToFirst();
		int numberOfRecords = res.getInt(0);
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}

		res = db.rawQuery("SELECT "+ column_1 + "," + column_2 + "," + column_7 + ", MIN(" + column_2 + ") FROM (SELECT * FROM " + tbl_easyDextero + " ORDER BY " + column_7 +" DESC) T", null);
		
		//6,5,4
		int totalErrors = f1errors+f2errors+f3errors+f4errors;
		
		
		if(numberOfRecords != 10){
			insertEasyHscore(totalScore, f1score, f2score, f3score, f4score, totalErrors);

			return res;
		} 
		

			res.moveToFirst();
		
			int smallestHs = res.getInt(res.getColumnIndex(column_2));
			int whighestError = res.getInt(res.getColumnIndex(column_7));
			if((smallestHs == totalScore && totalErrors <= whighestError) || (smallestHs < totalScore)){
		
				int id = res.getInt(res.getColumnIndex(column_1));
				
				updateEasyHighscore(id, totalScore,f1score, f2score,  f3score, f4score, totalErrors);
			} else {
				newDexterityHs[0] = 0;
				newDexterityHs[1] = 0;
				newDexterityHs[2] = 0;
				newDexterityHs[3] = 0;
				newDexterityHs[4] = 0;
				newDexterityHs[5] = 0;
				
			} 
			
			Log.e("We are returning the result of setHighscore()!", "We are returning the result of setHighscore()!");

		return res;
		
		
	}
	

	

	public Cursor setMediumHighscore(int totalScore,int f1score, int f2score, int f3score, int f4score, int f1errors, int f2errors, int f3errors, int f4errors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + tbl_mediumDextero, null);
		
		res.moveToFirst();
		int numberOfRecords = res.getInt(0);
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}
		
		res = db.rawQuery("SELECT "+ column_1 + "," + column_2 + "," + column_7 + ", MIN(" + column_2 + ") FROM (SELECT * FROM " + tbl_mediumDextero + " ORDER BY " + column_7 +" DESC) T", null);
		
		int totalErrors = f1errors+f2errors+f3errors+f4errors;
		

		if(numberOfRecords != 10){
			insertMediumHscore(totalScore, f1score, f2score, f3score, f4score, totalErrors);
			
			return res;
		} 
		

			res.moveToFirst();
		
			int smallestHs = res.getInt(res.getColumnIndex(column_2));
			int whighestError = res.getInt(res.getColumnIndex(column_7));
			if((smallestHs == totalScore && totalErrors <= whighestError) || (smallestHs < totalScore)){

				int id = res.getInt(res.getColumnIndex(column_1));
				
				updateMediumHighscore(id, totalScore,f1score, f2score,  f3score, f4score, totalErrors);
			} else {
				newDexterityHs[0] = 0;
				newDexterityHs[1] = 0;
				newDexterityHs[2] = 0;
				newDexterityHs[3] = 0;
				newDexterityHs[4] = 0;
				newDexterityHs[5] = 0;
				
			} 
			
			Log.e("We are returning the result of setHighscore()!", "We are returning the result of setHighscore()!");

		return res;
		
		
	}
	
	public Cursor setHardHighscore(int totalScore,int f1score, int f2score, int f3score, int f4score, int f1errors, int f2errors, int f3errors, int f4errors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + tbl_hardDextero, null);
		
		res.moveToFirst();
		int numberOfRecords = res.getInt(0);
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}
		
		res = db.rawQuery("SELECT "+ column_1 + "," + column_2 + "," + column_7 + ", MIN(" + column_2 + ") FROM (SELECT * FROM " + tbl_hardDextero + " ORDER BY " + column_7 +" DESC) T", null);
		

		int totalErrors = f1errors+f2errors+f3errors+f4errors;
		
		
		if(numberOfRecords != 10){
			insertHardHscore(totalScore, f1score, f2score, f3score, f4score, totalErrors);

			return res;
		} 
		

			res.moveToFirst();
		
			int smallestHs = res.getInt(res.getColumnIndex(column_2));
			int whighestError = res.getInt(res.getColumnIndex(column_7));
			if((smallestHs == totalScore && totalErrors <= whighestError) || (smallestHs < totalScore)){
		
				int id = res.getInt(res.getColumnIndex(column_1));
				updateHardHighscore(id, totalScore,f1score, f2score,  f3score, f4score, totalErrors);
			} else {
			
				newDexterityHs[0] = 0;
				newDexterityHs[1] = 0;
				newDexterityHs[2] = 0;
				newDexterityHs[3] = 0;
				newDexterityHs[4] = 0;
				newDexterityHs[5] = 0;
				
			} 
			
			Log.e("We are returning the result of setHighscore()!", "We are returning the result of setHighscore()!");

		return res;
		
		
	}
	
	
	public boolean updateEasyHighscore(int id, int totalScore,int f1score, int f2score, int f3score, int f4score, int totalerrors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(column_1, id);
		cv.put(column_2, totalScore);
		cv.put(column_3, f1score);
		cv.put(column_4, f2score);
		cv.put(column_5, f3score);
		cv.put(column_6, f4score);
		cv.put(column_7, totalerrors);
		
		db.update(tbl_easyDextero, cv, "ID = " + id , null);

		
		newDexterityHs[0] = totalScore;
		newDexterityHs[1] = f1score;
		newDexterityHs[2] = f2score;
		newDexterityHs[3] = f3score;
		newDexterityHs[4] = f4score;
		newDexterityHs[5] = totalerrors;

		Log.e("Highscore update is working!", "Highscore update is working!");
		return true;
	}
	
	public boolean updateMediumHighscore(int id, int totalScore,int f1score, int f2score, int f3score, int f4score, int totalerrors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(column_1, id);
		cv.put(column_2, totalScore);
		cv.put(column_3, f1score);
		cv.put(column_4, f2score);
		cv.put(column_5, f3score);
		cv.put(column_6, f4score);
		cv.put(column_7, totalerrors);
		
		db.update(tbl_mediumDextero, cv, "ID = " + id , null);
		

		
		newDexterityHs[0] = totalScore;
		newDexterityHs[1] = f1score;
		newDexterityHs[2] = f2score;
		newDexterityHs[3] = f3score;
		newDexterityHs[4] = f4score;
		newDexterityHs[5] = totalerrors;

		Log.e("Highscore update is working!", "Highscore update is working!");
		return true;
	}
	
	public boolean updateHardHighscore(int id, int totalScore,int f1score, int f2score, int f3score, int f4score, int totalerrors){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(column_1, id);
		cv.put(column_2, totalScore);
		cv.put(column_3, f1score);
		cv.put(column_4, f2score);
		cv.put(column_5, f3score);
		cv.put(column_6, f4score);
		cv.put(column_7, totalerrors);
		
		db.update(tbl_hardDextero, cv, "ID = " + id , null);
		
		
		newDexterityHs[0] = totalScore;
		newDexterityHs[1] = f1score;
		newDexterityHs[2] = f2score;
		newDexterityHs[3] = f3score;
		newDexterityHs[4] = f4score;
		newDexterityHs[5] = totalerrors;

		Log.e("Highscore update is working!", "Highscore update is working!");
		return true;
	}
	
	
	public boolean insertSpeechScore(int score){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		
		ContentValues cv = new ContentValues();
		cv.put(column_2, score);

		long result = db.insert(tbl_speech, null, cv);
		
		if(result == -1){
			Log.e("Insert method not working!", "insert not working!");
			return false;
		}
		
		newSpeechHs = score;
		Log.e("Insert method works!", "insert statement works!");


		return true;
	}
	
	
	public Cursor setSpeechHS(int totalScore){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		Cursor res = db.rawQuery("SELECT COUNT(*) FROM " + tbl_speech, null);
		
		res.moveToFirst();
		int numberOfRecords = res.getInt(0);
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}

		
		res = db.rawQuery("SELECT "+ column_1 + "," + column_2 + " FROM " + tbl_speech + " ORDER BY " + column_2 +" ASC", null);
		
		
		
		if(numberOfRecords != 10){
			insertSpeechScore(totalScore);
			
			return res;
		} 
		

			res.moveToFirst();
		
			int smallestHs = res.getInt(res.getColumnIndex(column_2));
			
			if(smallestHs <= totalScore){

				int id = res.getInt(res.getColumnIndex(column_1));
	
				updateSpeechScore(id, totalScore);
			} else {
				newSpeechHs = 0;
			} 
			
			Log.e("We are returning the result of setHighscore()!", "We are returning the result of setHighscore()!");	

		return res;
		
		
	}
	
	
	public boolean updateSpeechScore(int id, int totalScore){
		SQLiteDatabase  db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(column_1, id);
		cv.put(column_2, totalScore);
		
		db.update(tbl_speech, cv, "ID = " + id , null);
		
		newSpeechHs = totalScore;

		Log.e("Highscore update is working!", "Highscore update is working!");
		return true;
	}
	
	public int[] getSpeechScores(){
		SQLiteDatabase  db = this.getWritableDatabase();

		speechHsLst = null;
		
		speechHsLst = new int[10];
		
		
		Cursor res = db.rawQuery("SELECT "+ column_1 + "," + column_2 + " FROM " + tbl_speech + " ORDER BY " + column_2 +" DESC", null);
		
		int numberOfRecords = res.getCount();
		
		if(numberOfRecords  == 0){
			Log.i("DBstatus: ", "Nothing is in here");
		}
		
		res.moveToFirst();

		for(int i=0; i< res.getCount(); i++){
			
			speechHsLst[i] = res.getInt(res.getColumnIndex(column_2));
			
			res.moveToNext();
		}
		
		return speechHsLst;
	}
	
	public int[] getNewHighscore(){
		return newDexterityHs;
	}
	
	public int[][] getDexterityHsLst(){
		return dexterityHsLst;
	}
	
	public int getNewSpeechHs(){
		return newSpeechHs;
	}
	
	public int[] getSpeechHsLst(){
		return speechHsLst;
	}

}
