package settings;

public interface SettingListener
{
	public void settingChanged(String keyname, Object oldValue, Object newValue);
}
