package generate.hight_map.imple;

public class SteepHeightMap extends NormalHeightMap{

	@Override
	protected short getError(int length) {
		short rtn;
		if (isAroundSmooth) {
			rtn = (short) Math.floor(super.getError(length) * length / 3.0 * 2.0);
		} else {
			rtn = (short) Math.floor(super.getError(length) * Math.ceil(length * 0.04));
		}
		return rtn;
	}
	
}
