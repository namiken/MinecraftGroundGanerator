package generate.command;

import generate.blockSetter.BlockSetterInterface;
import generate.hight_map.HeightMapInterface;

public interface CommandOptionInterface {
	public String getName();
	public void setValue(String value);
	public void applyOption(BlockSetterInterface setter, HeightMapInterface heightMap);
	public String check(String value);
}
