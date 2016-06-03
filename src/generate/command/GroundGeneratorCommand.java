package generate.command;

import generate.blockSetter.BlockSetterInterface;
import generate.blockSetter.DesertBlockSetter;
import generate.blockSetter.GrassAndStoneSetter;
import generate.blockSetter.NetherLikeBlockSetter;
import generate.blockSetter.NormalMountainBlockSetter;
import generate.blockSetter.OceanBlockSetter;
import generate.blockSetter.SnowBlockSetter;
import generate.blockSetter.StoneGrassWaterBlockSetter;
import generate.blockSetter.StoneMountainAndLaveBlockSetter;
import generate.excuter.RunnableExcuter;
import generate.hight_map.HeightMapInterface;
import generate.hight_map.imple.FlatHeightMap;
import generate.hight_map.imple.FlatLevel2HeightMap;
import generate.hight_map.imple.MountainHeightMap;
import generate.hight_map.imple.NormalHeightMap;
import generate.hight_map.imple.SteepHeightMap;
import generate.world.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public class GroundGeneratorCommand implements CommandExecutor, TabCompleter{
	public GroundGeneratorCommand() {
		blockSetterMap = new HashMap<String, BlockSetterInterface>();
		blockSetterMap.put("DESERT", new DesertBlockSetter());
		blockSetterMap.put("NORMAL", new GrassAndStoneSetter());
		blockSetterMap.put("NETHER", new NetherLikeBlockSetter());
		blockSetterMap.put("OCEAN", new OceanBlockSetter());
		blockSetterMap.put("SNOW", new SnowBlockSetter());
		blockSetterMap.put("STONE_MOUNTAIN", new StoneMountainAndLaveBlockSetter());
		blockSetterMap.put("MOUNTAIN", new NormalMountainBlockSetter(170));
		blockSetterMap.put("STONE_GRASS_WATER", new StoneGrassWaterBlockSetter());

		heightMapMap = new HashMap<String, HeightMapInterface>();
		heightMapMap.put("FLAT", new FlatHeightMap());
		heightMapMap.put("FLAT2", new FlatLevel2HeightMap());
		heightMapMap.put("MOUNTAIN", new MountainHeightMap());
		heightMapMap.put("NORMAL", new NormalHeightMap());
		heightMapMap.put("STEEP", new SteepHeightMap());
	}

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
		if (!(paramCommandSender instanceof Player)) {
			return false;
		}


		Player p = (Player) paramCommandSender;
		Location rightLoc = Main.rightClick.get(p);
		Location leftLoc = Main.leftClick.get(p);

		Location minLoc = new Location(rightLoc.getWorld(), Math.min(rightLoc.getX(), leftLoc.getX()), 0, Math.min(rightLoc.getZ(), leftLoc.getZ()));
		Location maxLoc = new Location(rightLoc.getWorld(), Math.max(rightLoc.getX(), leftLoc.getX()), 0, Math.max(rightLoc.getZ(), leftLoc.getZ()));

		if (paramArrayOfString.length < 2) {
			p.sendMessage(ChatColor.RED + "引数が足りません。" );
			return false;
		}

		String blockSetterName = paramArrayOfString[0].toUpperCase();
		String heightMapName = paramArrayOfString[1].toUpperCase();

		BlockSetterInterface blockSetter = getBlockSetter(blockSetterName);
		HeightMapInterface heightMap = getHeightMap(heightMapName);

		if (paramArrayOfString.length == 4) {
			short min = Short.parseShort(paramArrayOfString[2]);
			short max = Short.parseShort(paramArrayOfString[3]);
			heightMap.setMaxMin(min, max);
		}

		if (blockSetter == null) {
			p.sendMessage(ChatColor.RED + "block setterが存在しません。" + blockSetterName);
			return true;
		}
		if (heightMap == null) {
			p.sendMessage(ChatColor.RED + "height mapが存在しません。" + heightMapName);
			return true;
		}

		p.sendMessage(ChatColor.BLUE + "実行します");
		RunnableExcuter runnableExcuter = new RunnableExcuter(minLoc, maxLoc);
		runnableExcuter.excute(blockSetter, heightMap);
		return true;
	}


	static HashMap<String, BlockSetterInterface> blockSetterMap = null;

	static HashMap<String, HeightMapInterface> heightMapMap = null;

	private BlockSetterInterface getBlockSetter(String blockSetterName) {
		return blockSetterMap.get(blockSetterName);
	}

	private HeightMapInterface getHeightMap(String heightMapName) {
		return heightMapMap.get(heightMapName);
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg3.length == 1) {
			return (List<String>)StringUtil.copyPartialMatches(arg3[0], blockSetterMap.keySet(), new ArrayList<String>(blockSetterMap.size()));
		} else if (arg3.length == 2) {
			return (List<String>)StringUtil.copyPartialMatches(arg3[0], heightMapMap.keySet(), new ArrayList<String>(heightMapMap.size()));
		}
		return ImmutableList.of();
	}
}
