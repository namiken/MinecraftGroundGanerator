package generate.hight_map;

import generate.hight_map.imple.SteepHeightMap;

import java.util.Random;

public abstract class BaseHeightMap implements HeightMapInterface {

	public static void main(String[] args) {
		HeightMapInterface generate = new SteepHeightMap().setMax((short)170).setMin((short)50).setSeed(100).generate(100);
		HeightMapUtil.print(generate, 100);
//		HeightMapInterface generate2 = new NormalHeightMap().setMaxMin((short)50, (short)170).generate(100);
//		HeightMapUtil.print(generate2, 100);
	}

	@Override
	public HeightMapInterface setSeed(long seed) {
		r = new Random(seed);
		return this;
	}

	protected short[][] heightMap;

	protected Random r = new Random();

	public BaseHeightMap() {
	}

	protected short leftUp = 60;
	protected short leftDown = 60;
	protected short rightUp = 60;
	protected short rightDown = 60;

	public HeightMapInterface setStartConer(short leftUp, short leftDown, short rightUp, short rightDown) {
		this.leftUp = leftUp;
		this.leftDown = leftDown;
		this.rightDown = rightDown;
		this.rightUp = rightUp;
		return this;
	}

	short max = 70;
	short min = 50;
	@Override
	public HeightMapInterface setMax(short max) {
		if (max < 0) {
			throw new RuntimeException("max dont allow negative value");
		}
		this.max = max;
		if (max < min) {
			this.min = (short) (max - 1);
		}
		return this;
	}
	
	@Override
	public HeightMapInterface setMin(short min) {
		if (min < 0) {
			throw new RuntimeException("max dont allow negative value");
		}
		
		this.min = min;
		if (max < min) {
			this.max = (short) (min + 1);
		}
		return this;
	}

//	@Override
//	public HeightMapInterface generate(int size) {
//		setSize(size);
//		heightMap[0][0] = leftUp;
//		heightMap[heightMap.length - 1][0] = leftDown;
//		heightMap[0][heightMap.length - 1] = rightUp;
//		heightMap[heightMap.length - 1][heightMap.length - 1] = rightDown;
//
//		generate(0, 0, heightMap.length - 1);
//		return this;
//	}

	protected void setSize(int size) {
		if (size < 2) {
			size = 2;
			heightMap = new short[size][size];
		} else {
			// 2^xの形にする
			int count = 1;
			while (size != 1) {
				if (size % 2 == 1) {
					size++;
				}
				size /= 2;
				count *= 2;
			}
			heightMap = new short[count + 1][count + 1];
		}
	}

//	/**
//	 *
//	 * @param x 左上の点x
//	 * @param y 左上の点y
//	 * @param length　正方形の辺の長さ
//	 */
//	protected void generate(int x, int y, int length) {
//		if (length <= 1) {
//			return;
//		}
//
//		int x1 = x;
//		int x2 = x + length;
//		int y1 = y;
//		int y2 = y + length;
//
//		//(x1,y1) (x1,y2)
//		//(x2,y1) (x2,y2)
//
//		//中点
//		short midValue = (short)(((heightMap[x1][y1] + heightMap[x1][y2] + heightMap[x2][y1] + heightMap[x2][y2]) / 4.0) + getError(length));
//		setHeight((x1 + x2) / 2, (y1 + y2) / 2, midValue);
//
//		//左中点
//		setSideMidPoint(x1, y1, x2, y1, length);
//
//		//上中点
//		setSideMidPoint(x1, y1, x1, y2, length);
//
//		//下中点
//		setSideMidPoint(x2, y1, x2, y2, length);
//
//		//右中点
//		setSideMidPoint(x1, y2, x2, y2, length);
//
////		HeightMapUtil.print(this);
////		System.out.println();
//
//		//新たに作られた4つの正方形に対しても同じことを
//		generate(x1, y1, length / 2);
//		generate(x1, (y1 + y2)/2, length / 2);
//		generate((x1 + x2)/2, y1, length / 2);
//		generate((x1 + x2)/2, (y1 + y2)/2, length / 2);
//	}

	/**
	 * (x1,y1) (x2,y2)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param lenght
	 */
	protected void setSideMidPoint(int x1, int y1, int x2, int y2, int lenght) {
		short leftMid = (short) ((heightMap[x1][y1] + heightMap[x2][y2]) / 2.0);
		short error = getError(lenght);
		setHeight((x1 + x2) / 2, (y1 + y2) / 2, (short) (leftMid + error));
	}

	protected void setHeight(int x, int y, short val) {
		if (val < min) {
			val = min;
		}
		if (val > max) {
			val = max;
		}
		heightMap[x][y] = val;
	}

	@Override
	public int getHeight(int x, int z) {
		return heightMap[x][z];
	}

	@Override
	public short[][] getHeightMap() {
		return heightMap;
	}

	@Override
	public int getCreateSize() {
		return heightMap.length;
	}

	protected short getError(int length) {
		short rtn = (short) ((r.nextInt(getCreateSize() / 2) - getCreateSize() / 4) * length / getCreateSize());
		return rtn;
	}
}
