package generate.command.generate.option;

import generate.blockSetter.BlockSetterInterface;
import generate.command.CommandOptionInterface;
import generate.hight_map.HeightMapInterface;

public class SmoothOption implements CommandOptionInterface {
	@Override
	public String getName() {
		return "AROUND_SMOOTH";
	}


	boolean smoothFlg = false;

	@Override
	public void setValue(String value) {
		if (Boolean.parseBoolean(value)) {
			smoothFlg = true;
		}
	}

	@Override
	public void applyOption(BlockSetterInterface setter, HeightMapInterface heightMap) {
		heightMap.setAroundSmoothFlg(smoothFlg);
	}

	@Override
	public String check(String value) {
		if ("TRUE".equals(value.toUpperCase())) {
			return null;
		}
		return "true以外許可されていません。(" + value +")";
	}

}
