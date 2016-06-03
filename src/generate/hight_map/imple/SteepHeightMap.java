package generate.hight_map.imple;


public class SteepHeightMap extends NormalHeightMap{

	@Override
	protected short getError(int length) {
		short rtn = (short) Math.floor(super.getError(length) * Math.ceil(length * 0.04));
		return rtn;
	}
}
