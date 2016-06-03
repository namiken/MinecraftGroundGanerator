package generate.excuter;

import generate.blockSetter.BlockSetterInterface;
import generate.common.NestedLoopExcuter;
import generate.common.NestedLoopRunnable;
import generate.hight_map.HeightMapInterface;
import generate.world.Main;

import org.bukkit.Location;

public class RunnableExcuter  implements GenerateExcuterInteface{
	protected Location corner;
	protected int xLenght;
	protected int zLenght;

	public RunnableExcuter(Location corner, int xLenght, int zLenght) {
		this.corner = corner;
		this.xLenght = xLenght;
		this.zLenght = zLenght;
	}

	public RunnableExcuter(Location corner, Location outSide) {
		this.corner = corner;
		this.xLenght = (int) Math.abs(outSide.getX() - corner.getX());
		this.zLenght = (int) Math.abs(outSide.getZ() - corner.getZ());
	}

	protected static boolean isExcuteNow = false;
	
	public boolean isLocked() {
		return isExcuteNow;
	}
	
	protected void lock() {
		isExcuteNow = true;
	}
	
	protected void unlock() {
		isExcuteNow = false;
	}
	
	
	@Override
	public void excute(BlockSetterInterface setter, HeightMapInterface hightMap) {
		lock();
		NestedLoopRunnable runnable = new NestedLoopRunnable(xLenght, zLenght, new SetBlockExcuter(setter, hightMap));
		runnable.runTaskTimer(Main.plguin, 0, 2);
	}

	class SetBlockExcuter implements NestedLoopExcuter{
		BlockSetterInterface setter;
		HeightMapInterface hightMap;

		public SetBlockExcuter(BlockSetterInterface setter, HeightMapInterface hightMap) {
			this.setter = setter;
			this.hightMap = hightMap;
		}
		@Override
		public boolean excute(int i, int j, int count) {
			try {
				if (count == 0) {
					//heightmapを作成する
					hightMap.generate(Math.max(xLenght, zLenght));
				}
				//ブロックを配置する
				int height = hightMap.getHeight(i, j);
				setter.setBlock(corner.clone().add(i, 0, j), height);
				
				//200回に一度ループから抜ける
				if (count == 0 || count % 400 == 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		@Override
		public void after() {
			unlock();
		}

	}
}
