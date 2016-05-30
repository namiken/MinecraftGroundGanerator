package generate.hight_map.imple;


public class SteepHeightMap extends NormalHeightMap{

//	protected long count = 0;
	@Override
	protected short getError(int length) {
		short rtn = (short) Math.floor(super.getError(length) * Math.ceil(length * 0.04));
//		if (count == 0) {
//			rtn = (short) Math.abs(rtn);
//		}
//		count++;
//		System.out.println(length + "@" + rtn + "@" + length * 0.04);
		return rtn;
	}
}
