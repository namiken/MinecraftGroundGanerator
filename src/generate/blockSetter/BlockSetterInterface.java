package generate.blockSetter;

import org.bukkit.Location;

public interface BlockSetterInterface {
	public void setBlock(Location add, int height);
	
	public void setSeed(long seed);
}
