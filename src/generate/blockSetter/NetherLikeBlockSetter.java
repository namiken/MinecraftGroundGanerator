package generate.blockSetter;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

public class NetherLikeBlockSetter extends LiquidBlockSetter{
	Random r = new Random();

	@Override
	protected Material getLiquid(Location add, int height) {
		return Material.LAVA;
	}

	@Override
	protected Material getLiquidButtom(Location add, int height) {
		return Material.NETHERRACK;
	}

	@Override
	protected Material getCoastline(Location add, int height) {
		return Material.NETHERRACK;
	}

	@Override
	protected Material getSurface(Location add, int height) {
		return r.nextInt(50) == 0 ? Material.LAVA : Material.NETHERRACK;
	}

	@Override
	protected Material getSurfaceUnder(Location add, int height) {
		return Material.NETHERRACK;
	}

}
