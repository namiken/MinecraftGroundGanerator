package generate.command.generate.option;

import generate.blockSetter.BlockSetterInterface;
import generate.command.CommandOptionInterface;
import generate.hight_map.HeightMapInterface;

public class MinOption implements CommandOptionInterface{
	@Override
	public String getName() {
		return "min";
	}

	@Override
	public void applyOption(BlockSetterInterface setter, HeightMapInterface heightMap) {
		short parseShort = Short.parseShort(value);
		heightMap.setMin(parseShort);
	}

	@Override
	public String check(String value) {
		try {
			short parseShort = Short.parseShort(value);
			if (parseShort < 0) {
				return "min値は0以上を指定してください。(" + value + ")";
			}
		} catch (NumberFormatException e) {
			return "min値は整数以外許可されません。(" + value + ")";
		}
		return null;
	}

	String value;
	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
