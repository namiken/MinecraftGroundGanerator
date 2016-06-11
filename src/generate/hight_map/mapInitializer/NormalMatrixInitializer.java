package generate.hight_map.mapInitializer;

public class NormalMatrixInitializer implements MatrixInitializerInterface{
	@Override
	public short[][] getMatrix(int size) {
		return new short[size][size];
	}

}
