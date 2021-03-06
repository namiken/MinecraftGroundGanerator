package generate.hight_map;


import generate.hight_map.imple.MountainHeightMap;
import generate.hight_map.mapInitializer.AroundSmoothMapInitializer;
import generate.hight_map.mapInitializer.MatrixInitializerInterface;
import generate.hight_map.mapInitializer.NormalMatrixInitializer;

import java.util.Random;

import org.bukkit.Location;

public abstract class BaseHeightMap implements HeightMapInterface {

	public static void main(String[] args) {
		HeightMapInterface generate =
				new MountainHeightMap()
		.setMax((short)99)
		.setSeed(101)
		.setMinLocMaxLoc(new Location(null, 99, 81, 99), new Location(null, -1, 61, -1));
		generate.setAroundSmoothFlg(true);
		generate.generate(100);
//		HeightMapUtil.printRange(generate, 20 + 1 ,30 + 1);
		HeightMapUtil.printRange(generate, 100, 100);
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

	/**
	 * sizeをセットし、行列を生成する
	 * @param size
	 */
	final protected void setSize(int size) {
		if (size < 2) {
			size = 2;
			heightMap = initializerInterface.getMatrix(size);
		} else {
			// 2^x + 1の形にする
			int count = HeightMapUtil.geTruncation2Multiplier(size);
			count++;
			heightMap = initializerInterface.getMatrix(count);
		}
//		HeightMapUtil.printRange(this, 20 + 1 ,30 + 1);
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

	protected MatrixInitializerInterface initializerInterface = new NormalMatrixInitializer();

	protected int xLength = -1;
	protected int zLength = -1;
	protected Location minLoc;
	protected Location maxLoc;

	@Override
	public HeightMapInterface setMinLocMaxLoc(Location minLoc, Location maxLoc) {
		this.minLoc = minLoc;
		this.maxLoc = maxLoc;

		this.xLength = Math.abs(maxLoc.getBlockX() - minLoc.getBlockX());
		this.zLength = Math.abs(maxLoc.getBlockZ() - minLoc.getBlockZ());

		if (isAroundSmooth) {
			initializerInterface = new AroundSmoothMapInitializer(minLoc, maxLoc, xLength, zLength);
		}
		return this;
	}

	protected boolean isAroundSmooth = false;
	@Override
	public HeightMapInterface setAroundSmoothFlg(boolean isAroundSmooth) {
		this.isAroundSmooth = isAroundSmooth;

		if (minLoc != null && maxLoc != null) {
			initializerInterface = new AroundSmoothMapInitializer(minLoc, maxLoc, xLength, zLength);
		}
		return this;
	}
}
