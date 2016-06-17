package generate.command.generate.option;

import generate.blockSetter.BlockSetterInterface;
import generate.command.CommandOptionInterface;
import generate.hight_map.HeightMapInterface;

public class SeedOption implements CommandOptionInterface{
	@Override
	public String getName() {
		return "seed";
	}

	@Override
	public void applyOption(BlockSetterInterface setter, HeightMapInterface heightMap) {
		setter.setSeed(Long.parseLong(value));
		heightMap.setSeed(Long.parseLong(value));
	}

	@Override
	public String check(String value) {
		try {
			Long.parseLong(value);
		} catch (NumberFormatException e) {
			return "seed値は整数以外許可されません。(" + value + ")";
		}
		return null;
	}

	String value;
	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
