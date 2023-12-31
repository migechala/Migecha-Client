package migecha;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class ManageFiles {
	private static Gson gson = new Gson();
	
	private static File RD = new File("MigechaClientData");
	private static File MD = new File(RD, "mods");
	
	public static void init() {
		if(!RD.exists()) {
			RD.mkdirs();
		}
		if(!MD.exists()) {
			MD.mkdirs();
		}
	}
	
	public static Gson getGson() {
		return gson;
	}
	public static File getModDir() {
		return MD;
	}
	
	public static boolean writeJsonFile(File file, Object obj) {
			try {
				if(!file.exists()) {
					file.createNewFile();
				}
				
				FileOutputStream output = new FileOutputStream(file);
				output.write(gson.toJson(obj).getBytes());
				output.flush();
				output.close();
				
				return true;
				
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	}
	public static <T extends Object> T readJson(File file, Class<T> c) {
		try {
			FileInputStream input = new FileInputStream(file);
			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			StringBuilder builder = new StringBuilder();
			String line;
			
			while((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
			bufferedReader.close();
			inputReader.close();
			input.close();
			
			return gson.fromJson(builder.toString(), c);
			
			
			
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
