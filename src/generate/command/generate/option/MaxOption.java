package generate.command.generate.option;

import generate.blockSetter.BlockSetterInterface;
import generate.command.CommandOptionInterface;
import generate.hight_map.HeightMapInterface;

public class MaxOption implements CommandOptionInterface{
	@Override
	public String getName() {
		return "max";
	}

	@Override
	public void applyOption(BlockSetterInterface setter, HeightMapInterface heightMap) {
		setter.setMax(Short.parseShort(value));
		heightMap.setMax(Short.parseShort(value));
	}

	@Override
	public String check(String value) {
		try {
			short parseShort = Short.parseShort(value);
			if (parseShort < 0) {
				return "max値は0以上を指定してください。(" + value + ")";
			}
		} catch (NumberFormatException e) {
			return "max値は整数以外許可されません。(" + value + ")";
		}
		return null;
	}

	@Override
	public CommandOptionInterface copy() {
		return new MaxOption();
	}


	String value;
	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
