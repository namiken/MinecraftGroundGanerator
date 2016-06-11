package generate.hight_map;


import generate.hight_map.imple.SteepHeightMap;
import generate.hight_map.mapInitializer.AroundSmoothMapInitializer;
import generate.hight_map.mapInitializer.MatrixInitializerInterface;
import generate.hight_map.mapInitializer.NormalMatrixInitializer;
import generate.hight_map.imple.MountainHeightMap;

import java.util.Random;

import org.bukkit.Location;

public abstract class BaseHeightMap implements HeightMapInterface {

	public static void main(String[] args) {
		HeightMapInterface generate = new SteepHeightMap().setMax((short)170).setMin((short)50).setSeed(100);
		generate.setAroundSmoothInitializer(null, null, 80, 60);
		generate.generate(100);
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

	protected short max = 70;
	protected short min = 50;
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

	final protected void setSize(int size) {
		if (size < 2) {
			size = 2;
			heightMap = initializerInterface.getMatrix(size);
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
			heightMap = initializerInterface.getMatrix(count + 1);
		}
	}

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
		if (heightMap[x][y] == 0) {
			heightMap[x][y] = val;
		}
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
	
	MatrixInitializerInterface initializerInterface = new NormalMatrixInitializer();
	
	public HeightMapInterface setAroundSmoothInitializer(Location minLoc, Location maxLoc, int xLength, int zLength) {
		initializerInterface = new AroundSmoothMapInitializer(minLoc, maxLoc, xLength, zLength);
		return this;
	}
}
