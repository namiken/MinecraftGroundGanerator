package generate.hight_map.imple;

import generate.hight_map.HeightMapInterface;

public class MountainHeightMap extends NormalHeightMap{
	int originalSize;

	@Override
	public HeightMapInterface generate(int size) {
		isFirst = true;

		originalSize = size;
		super.generate(size);

		int marge = (int) Math.floor((getCreateSize() - originalSize)/2);

		short[][] heightMap = getHeightMap();

		setSize(originalSize);
		for (int i = marge; i < originalSize + marge; i++) {
			for (int j = marge; j < originalSize + marge; j++) {
				setHeight(i - marge, j - marge, heightMap[i][j]);
			}
		}
		return this;
	}

	@Override
	protected void setHeight(int x, int y, short val) {
		if (x == 0|| y == 0 || x == heightMap.length - 1 || y == heightMap.length - 1) {
			val = min;
		}
		super.setHeight(x, y, val);
	}

	protected boolean isFirst = true;

	@Override
	protected short getError(int length) {
		short rtn = super.getError(length);
		if (isFirst) {
			rtn = max;
			isFirst = false;
			return (short) Math.abs(rtn);
		}
		return rtn;
	}
}
