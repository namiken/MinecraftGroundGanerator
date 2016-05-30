
package generate.hight_map.imple;

import generate.hight_map.HeightMapInterface;
import generate.hight_map.HeightMapUtil;

public class FlatLevel2HeightMap extends NormalHeightMap{
	int originalSize;
	
	public FlatLevel2HeightMap() {
	}

	@Override
	public HeightMapInterface generate(int size) {
		if (4 > size) {
			size = 4;
		}
		setSize(size);
		originalSize = getCreateSize();
		//大きさを小さくする
		super.generate((originalSize - 1) / 4);
		HeightMapUtil.print(this);
		
		short[][] heightMap = getHeightMap();
		//サイズを元に戻す
		super.setSize(originalSize);
		
		
		//今取得したデータをreduction飛ばしで入れていく
		for (int i = 0; i < heightMap.length; i++) {
			for (int j = 0; j < heightMap.length; j++) {
				setHeight(i * 4, j * 4, heightMap[i][j]);
			}
		}
		
		//横の枠を埋める
		for (int i = 0; i < getCreateSize(); i+=4) {
			for (int j = 1; j < getCreateSize(); j+=4) {
				short[] block3 = getYoko3Block(i, j);
				setHeight(i, j, block3[0]);
				setHeight(i, j+1, block3[1]);
				setHeight(i, j+2, block3[2]);
			}
		}

		//縦の枠を埋める
		for (int i = 1; i < getCreateSize(); i+=4) {
			for (int j = 0; j < getCreateSize(); j+=4) {
				short[] block3 = getTate3Block(i, j);
				setHeight(i, j, block3[0]);
				setHeight(i+1, j, block3[1]);
				setHeight(i+2, j, block3[2]);
			}
		}
		
		//残りの部分を埋めていく
		for (int i = 1; i < getCreateSize(); i+=4) {
			for (int j = 1; j < getCreateSize(); j+=4) {
				short[] blocks = get9Block(i, j);
				setHeight(i, j, blocks[0]);
				setHeight(i, j+1, blocks[1]);
				setHeight(i, j+2, blocks[2]);
				setHeight(i+1, j, blocks[3]);
				setHeight(i+1, j+1, blocks[4]);
				setHeight(i+1, j+2, blocks[5]);
				setHeight(i+2, j, blocks[6]);
				setHeight(i+2, j+1, blocks[7]);
				setHeight(i+2, j+2, blocks[8]);
			}
		}
		
		return this;
	}

	private short[] get9Block(int i, int j) {
		short[] blocks = new short[9];
		blocks[0] = (short) ((getHeight(i - 1, j) + getHeight(i, j - 1)) / 2.0);
		blocks[2] = (short) ((getHeight(i - 1, j) + getHeight(i, j + 3)) / 2.0);
		blocks[6] = (short) ((getHeight(i, j - 1) + getHeight(i + 3, j)) / 2.0);
		blocks[8] = (short) ((getHeight(i + 3, j + 2) + getHeight(i + 2, j + 3)) / 2.0);

		blocks[1] = (short) ((getHeight(i - 1, j) + blocks[0] + blocks[2]) / 3.0);
		blocks[3] = (short) ((getHeight(i, j - 1) + blocks[0] + blocks[6]) / 3.0);
		blocks[5] = (short) ((getHeight(i + 1, j + 3) + blocks[2] + blocks[8]) / 3.0);
		blocks[7] = (short) ((getHeight(i + 3, j + 1) + blocks[6] + blocks[8]) / 3.0);

		blocks[4] = (short) ((blocks[1] + blocks[3] + blocks[5] + blocks[7]) / 4.0);
		return blocks;
	}

	private short[] getYoko3Block(int i, int j) {
		short[] rtn = new short[3];
		rtn[1] = (short) ((getHeight(i, j-1) + getHeight(i, j+3)) / 2.0);
		rtn[0] = (short) ((getHeight(i, j-1) + rtn[1]) / 2.0);
		rtn[2] = (short) ((rtn[1] + getHeight(i, j+3)) / 2.0);
		return rtn;
	}

	private short[] getTate3Block(int i, int j) {
		short[] rtn = new short[3];
		rtn[1] = (short) ((getHeight(i-1, j) + getHeight(i+3, j)) / 2.0);
		rtn[0] = (short) ((getHeight(i-1, j) + rtn[1]) / 2.0);
		rtn[2] = (short) ((rtn[1] + getHeight(i+3, j)) / 2.0);
		return rtn;
	}

}
