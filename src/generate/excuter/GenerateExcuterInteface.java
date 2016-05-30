package generate.excuter;

import generate.blockSetter.BlockSetterInterface;
import generate.hight_map.HeightMapInterface;

public interface GenerateExcuterInteface {
	public void excute(BlockSetterInterface setter, HeightMapInterface generate);
}
