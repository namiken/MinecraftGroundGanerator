package generate.hight_map;

import generate.hight_map.imple.NormalHeightMap;

public class CopyOfMountainHeightMap extends NormalHeightMap{
	
	int originalSize;
	
	@Override
	public HeightMapInterface generate(int size) {
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

	protected boolean isFirst = true;
	
	@Override
	protected short getError(int length) {
		short rtn = super.getError(length);
		if (isFirst) {
			rtn = (short) Math.floor(rtn * length);
			isFirst = false;
			return (short) Math.abs(rtn);
		}
		return rtn;
	}
}
