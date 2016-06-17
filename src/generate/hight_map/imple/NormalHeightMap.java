package generate.hight_map.imple;

import generate.hight_map.BaseHeightMap;
import generate.hight_map.HeightMapInterface;

public class NormalHeightMap extends BaseHeightMap{
	public NormalHeightMap() {
		super();
	}

	@Override
	public HeightMapInterface generate(int size) {
		setSize(size);


		if (isAroundSmooth) {
			heightMap[0][0] = (short) ((heightMap[0][1] + heightMap[1][0])/2.0);
			heightMap[heightMap.length - 1][0] = (short) ((heightMap[heightMap.length - 2][0] + heightMap[heightMap.length - 1][1])/2.0);
			heightMap[0][heightMap.length - 1] = (short) ((heightMap[1][heightMap.length - 1] + heightMap[0][heightMap.length - 2])/2.0);
			heightMap[heightMap.length - 1][heightMap.length - 1] = (short) ((heightMap[heightMap.length - 2][heightMap.length - 1] + heightMap[heightMap.length - 1][heightMap.length - 2])/2.0);
		} else {
			heightMap[0][0] = leftUp;
			heightMap[heightMap.length - 1][0] = leftDown;
			heightMap[0][heightMap.length - 1] = rightUp;
			heightMap[heightMap.length - 1][heightMap.length - 1] = rightDown;
		}

		int length = getCreateSize() - 1;
		while (length > 1) {
			for (int i = 0; i < getCreateSize() - 1; i += length) {
				for (int j = 0; j < getCreateSize() - 1; j += length) {
					generate(i, j, length);
				}
			}

			length /= 2;
		}

		return this;
	}

	protected void generate(int x, int y, int length) {
		int x1 = x;
		int x2 = x + length;
		int y1 = y;
		int y2 = y + length;

		//(x1,y1) (x1,y2)
		//(x2,y1) (x2,y2)

		//中点
		short midValue = (short)(((heightMap[x1][y1] + heightMap[x1][y2] + heightMap[x2][y1] + heightMap[x2][y2]) / 4.0) + getError(length));
		setHeight((x1 + x2) / 2, (y1 + y2) / 2, midValue);

		//左中点
		setSideMidPoint(x1, y1, x2, y1, length);

		//上中点
		setSideMidPoint(x1, y1, x1, y2, length);

		//下中点
		setSideMidPoint(x2, y1, x2, y2, length);

		//右中点
		setSideMidPoint(x1, y2, x2, y2, length);

	}
}
