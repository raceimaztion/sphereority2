package tools.mapeditor;


/**
 * A listener for registering with an EditableMap
 * @author dvanhumb
 */
public interface MapAlterationListener
{
	public void mapChanged(EditableMap map, int x, int y);
}
