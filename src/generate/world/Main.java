package generate.world;

import generate.command.generate.GroundGeneratorCommand;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	public static Plugin plguin;

	@Override
	public void onEnable() {
		plguin = this;
		getServer().getPluginManager().registerEvents(this, this);
		GroundGeneratorCommand command = new GroundGeneratorCommand();
		getCommand("GROUND_GENERATE").setExecutor(command);
		getCommand("GROUND_GENERATE").setTabCompleter(command);
	}

	static public HashMap<Player, Location> rightClick = new HashMap<Player, Location>();
	static public HashMap<Player, Location> leftClick = new HashMap<Player, Location>();

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getItemInHand();

		if (!player.isOp() || player.getGameMode() != GameMode.CREATIVE) {
			return;
		}
		if (itemInHand.getType() == Material.DIAMOND_HOE) {
			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				leftClick.put(player, e.getClickedBlock().getLocation());
				player.sendMessage(ChatColor.GREEN + "REGIST LEFT CLICK");
				e.setCancelled(true);
			} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				player.sendMessage(ChatColor.GREEN + "REGIST RIGHT CLICK");
				rightClick.put(player, e.getClickedBlock().getLocation());
				e.setCancelled(true);
			}
		}
//		Location location = player.getLocation();
//		location.setX(0);
//		location.setZ(0);
//		GenerateExcuterInteface generator = new RunnableExcuter(location, 100, 100);
//		if (e.getMaterial() == Material.BEACON) {
//			generator.excute(new GrassAndStoneSetter(), new NormalHeightMap().setMaxMin((short)58, (short)63));
//		} else if (e.getMaterial() == Material.ANVIL) {
//			generator.excute(new GrassAndStoneSetter(), new FlatHeightMap().setMaxMin((short)58, (short)63));
//		} else if (e.getMaterial() == Material.APPLE) {
//			generator.excute(new GrassAndStoneSetter(), new FlatLevel2HeightMap().setMaxMin((short)58, (short)63));
//		} else if (e.getMaterial() == Material.WATER_BUCKET) {
//			generator.excute(new OceanBlockSetter(), new NormalHeightMap().setMaxMin((short)30, (short)70));
//		} else if (e.getMaterial() == Material.NETHERRACK) {
//			generator.excute(new NetherLikeBlockSetter(), new NormalHeightMap().setMaxMin((short)50, (short)90));
//		} else if (e.getMaterial() == Material.STONE) {
//			generator.excute(new StoneMountainAndLaveBlockSetter(), new SteepHeightMap().setMaxMin((short)50, (short)170));
//		} else if (e.getMaterial() == Material.ARROW) {
//			generator.excute(new NormalMountainBlockSetter(150), new MountainHeightMap().setMaxMin((short)50, (short)150));
//		} else if (e.getMaterial() == Material.SAND) {
//			generator.excute(new DesertBlockSetter(), new NormalHeightMap().setMaxMin((short)50, (short)120));
//		} else if (e.getMaterial() == Material.SNOW_BLOCK) {
//			generator.excute(new SnowBlockSetter(), new SteepHeightMap().setMaxMin((short)50, (short)120));
//		}
	}
}
