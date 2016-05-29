package hight_map;

public class HeightMapUtil {
	public static void print(HeightMapInterface heightMap2) {
		print(heightMap2, 40);
	}
	
	public static void print(HeightMapInterface heightMap2, int printSize) {
		for (int i = 0; i < Math.min(printSize, heightMap2.getHeightMap().length); i++) {
			for (int j = 0; j < Math.min(printSize, heightMap2.getHeightMap().length); j++) {
				if (heightMap2.getHeight(i, j) > 99) {
					print3(heightMap2, printSize);
					System.out.println();
					return;
				}
			}
		}
		print2(heightMap2, printSize);
		System.out.println();
	}

	protected static void print3(HeightMapInterface heightMap2, int printSize) {
		for (int i = 0; i < Math.min(printSize, heightMap2.getHeightMap().length); i++) {
			for (int j = 0; j < Math.min(printSize, heightMap2.getHeightMap().length); j++) {
				if (heightMap2.getHeight(i, j) < 10) {
					System.out.print("  " + heightMap2.getHeight(i, j) + " ");
				} else if (heightMap2.getHeight(i, j) < 100) {
					System.out.print(" " + heightMap2.getHeight(i, j) + " ");
				} else {
					System.out.print(heightMap2.getHeight(i, j) + " ");
				}
			}
			System.out.println();
		}
	}

	protected static void print2(HeightMapInterface heightMap2, int printSize) {
		for (int i = 0; i < Math.min(printSize, heightMap2.getHeightMap().length); i++) {
			for (int j = 0; j < Math.min(printSize, heightMap2.getHeightMap().length); j++) {
				if (heightMap2.getHeight(i, j) < 10) {
					System.out.print(" " + heightMap2.getHeight(i, j) + " ");
				} else {
					System.out.print(heightMap2.getHeight(i, j) + " ");
				}
			}
			System.out.println();
		}
	}
}
