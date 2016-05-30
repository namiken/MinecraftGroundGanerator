package generate.hight_map.imple;

import generate.hight_map.HeightMapInterface;

public class FlatHeightMap extends NormalHeightMap{
	int originalSize;
	
	
	public FlatHeightMap() {
	}

	@Override
	public HeightMapInterface generate(int size) {
		if (2 > size) {
			size = 2;
		}
		setSize(size);
		originalSize = getCreateSize();
		//大きさを小さくする
		super.setSize((size - 1) / 2);
		
		//生成する
		super.generate(size);
		
		short[][] heightMap = getHeightMap();
		//サイズを元に戻す
		super.setSize(originalSize);
		
		//今取得したデータをreduction飛ばしで入れていく
		for (int i = 0; i < heightMap.length; i++) {
			for (int j = 0; j < heightMap.length; j++) {
				setHeight(i * 2, j * 2, heightMap[i][j]);
			}
		}
		
		//間を埋める
		for (int i = 0; i < getCreateSize(); i++) {
			for (int j = 0; j < getCreateSize(); j++) {
				short val = getVal(i, j);
				if (val != 0) {
					setHeight(i, j, val);
				}
			}
		}
		
		//残りの部分を埋めていく
		for (int i = 1; i < getCreateSize(); i+=2) {
			for (int j = 1; j < getCreateSize(); j+=2) {
				setHeight(i, j, (short) ((getHeight(i - 1, j) + getHeight(i + 1, j) + getHeight(i, j - 1) + getHeight(i, j + 1)) / 4.0));
			}
		}
		
		return this;
	}

	private short getVal(int i, int j) {
		short height = 0;
		if (i % 2 == 0 && j % 2 == 1) {
			height = (short) ((getHeight(i, j + 1) + getHeight(i, j - 1)) / 2);
		} else if (i % 2 == 1 && j % 2 == 0) {
			height = (short) ((getHeight(i + 1, j) + getHeight(i - 1, j)) / 2 );
		}
		return height;
	}
}
