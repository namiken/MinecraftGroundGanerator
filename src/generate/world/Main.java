package generate.world;

import hight_map.MountainHeightMap;
import imple.FlatHeightMap;
import imple.FlatLevel2HeightMap;
import imple.NormalHeightMap;
import imple.SteepHeightMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import blockSetter.GrassAndStoneSetter;
import blockSetter.NetherLikeBlockSetter;
import blockSetter.NormalMountainBlockSetter;
import blockSetter.OceanBlockSetter;
import blockSetter.StoneMountainAndLaveBlockSetter;
import excuter.GenerateExcuterInteface;
import excuter.SimpleExcuter;

public class Main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Location location = player.getLocation();
		location.setX(0);
		location.setZ(0);
		GenerateExcuterInteface generator = new SimpleExcuter(location, 100, 100);
		if (e.getMaterial() == Material.BEACON) {
			generator.excute(new GrassAndStoneSetter(), new NormalHeightMap().setMaxMin((short)63, (short)58));
		} else if (e.getMaterial() == Material.ANVIL) {
			generator.excute(new GrassAndStoneSetter(), new FlatHeightMap().setMaxMin((short)63, (short)58));
		} else if (e.getMaterial() == Material.APPLE) {
			generator.excute(new GrassAndStoneSetter(), new FlatLevel2HeightMap().setMaxMin((short)63, (short)58));
		} else if (e.getMaterial() == Material.WATER_BUCKET) {
			generator.excute(new OceanBlockSetter(), new NormalHeightMap().setMaxMin((short)30, (short)70));
		} else if (e.getMaterial() == Material.NETHERRACK) {
			generator.excute(new NetherLikeBlockSetter(), new NormalHeightMap().setMaxMin((short)50, (short)90));
		} else if (e.getMaterial() == Material.STONE) {
			generator.excute(new StoneMountainAndLaveBlockSetter(), new SteepHeightMap().setMaxMin((short)50, (short)170));
		} else if (e.getMaterial() == Material.ARROW) {
			player.sendMessage("mountain!!");
			generator.excute(new NormalMountainBlockSetter(170), new MountainHeightMap().setMaxMin((short)50, (short)170));
		}
		player.sendMessage("ganarate complete!!");
	}
}
