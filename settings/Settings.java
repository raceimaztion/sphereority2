package settings;

import java.util.HashMap;

/*
 * TODO: Create a user interface to add, remove, and modify settings dynamically
 */

/**
 * This class handles storing and retrieving settings such as default weights, maximum speeds, etc.
 * This facilitates dynamically changing values during runtime to perfect the gameplay.
 * Settings are stored as named values and can be requested by giving a String and getting an Object back.  
 * @author dvanhumb
 */
public class Settings
{
	private static Settings singleton = null;
	
	private HashMap<String, Object> storedSettings;
	
	private Settings()
	{
		storedSettings = new HashMap<String, Object>();
		
		// Load the settings
	}
	
	/**
	 * Get the single instance of the Settings class
	 * @return
	 */
	public static Settings getSettings()
	{
		if (singleton == null)
			singleton = new Settings();
		return singleton;
	}
	
	/**
	 * Save all the changes made to a file
	 */
	public void storeSettings()
	{
		
	}
	
	/**
	 * Get the specified setting
	 * @param key  The String name of the desired setting
	 * @return  The Object stored at the specified key
	 */
	public Object getValue(String key)
	{
		return storedSettings.get(key);
	}
}
