package imple;

public class SteepHeightMap extends NormalHeightMap{
	
	protected long count = 0;
	@Override
	protected short getError(int length) {
		short rtn = (short) Math.floor(super.getError(length) * length);
		if (count == 0) {
			rtn = (short) Math.abs(rtn);
		}
		count++;
		return rtn;
	}
}
