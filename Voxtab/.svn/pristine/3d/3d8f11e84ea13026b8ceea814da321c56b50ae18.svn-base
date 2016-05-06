package com.ariatech.lib_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;


public class FileHandler {


	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}

	public static void writeToFile(String dirctory,String fileName, String body) {
		FileOutputStream fos = null;
		try {
			File dir =  new File(Environment
					.getExternalStorageDirectory().getAbsolutePath() +"/"+dirctory);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			final File myFile = new File(dir, fileName);

			if (!myFile.exists()) {
				myFile.createNewFile();
			}

			fos = new FileOutputStream(myFile);

			fos.write(body.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			GlobalData.printError(e);
		}
	}

	public static String readFromFile(String dir, String fileName) {
		try {
			final File dirR = new File(Environment
					.getExternalStorageDirectory().getAbsolutePath() +"/"+ dir);
			File myFile = new File(dirR, fileName);
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}

			myReader.close();

			return aBuffer;

		} catch (Exception e) {
//			GlobalData.printError(e);
		}
		return null;
	}

	// List<String> files = getList(new File("YOUR ROOT"));
	// ...
	public static List<String> getList(String dir) {
		final File dirRecieve = new File(Environment
				.getExternalStorageDirectory().getAbsolutePath() +"/"+ dir);
		ArrayList<String> inFiles = new ArrayList<String>();
		String[] fileNames = dirRecieve.list();

		if (fileNames != null) {
			for (String fileName : fileNames) {
				if (fileName.toLowerCase().endsWith(".txt")) {
					inFiles.add(fileName);
				}
			}
		}

		return inFiles;
	}


	public static void copyFileUsingStream(String dirSource, String dirDistination, String fileName) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		
		final File dirRecieve = new File(Environment
				.getExternalStorageDirectory().getAbsolutePath() +"/"+ dirSource);
		
		final File dirArchive = new File(Environment
				.getExternalStorageDirectory().getAbsolutePath() +"/"+ dirDistination);

		File source = new File(dirRecieve, fileName);
		File dest = new File(dirArchive, fileName);

		if (!dirArchive.exists()) {
			dirArchive.mkdirs();
		}

		if (!dest.exists()) {
			dest.createNewFile();
		}

		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			if (source.delete()) {
				Log.e("FileHandler", "File Deleted");
			} else {
				Log.e("FileHandler", "File Not Deleted");
			}
			is.close();
			os.close();
		}
	}
	
	public static void copyFileUsingStream(File dirRecieve, File dirArchive, String fileName) throws IOException {
		InputStream is = null;
		OutputStream os = null;

		File source = dirRecieve;
		File dest = new File(dirArchive, fileName);

		if (!dirArchive.exists()) {
			dirArchive.mkdirs();
		}

		if (!dest.exists()) {
			dest.createNewFile();
		}

		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
//			if (source.delete()) {
//				Log.e("FileHandler", "File Deleted");
//			} else {
//				Log.e("FileHandler", "File Not Deleted");
//			}
			is.close();
			os.close();
		}
	}

	public static String getFileFromAssets(Context c, String file) {
		BufferedReader reader = null;
		StringBuffer fileBuffer = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(c.getAssets()
					.open(file)));

			// do reading, usually loop until end of file reading

			String mLine = reader.readLine();
			while (mLine != null) {
				// process line
				fileBuffer.append(mLine);
				mLine = reader.readLine();
			}
		} catch (IOException e) {
			// log the exception
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}

		return fileBuffer.toString();
	}

}
