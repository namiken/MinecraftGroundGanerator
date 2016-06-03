package generate.command.generate;

import generate.command.CommandOptionInterface;
import generate.command.generate.option.MaxOption;
import generate.command.generate.option.MinOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GenerateCommandOptionParser {
	static HashMap<String, CommandOptionInterface> options = new HashMap<String, CommandOptionInterface>();
	{
		regist(new MaxOption());
		regist(new MinOption());
	}
	
	public static void regist(CommandOptionInterface option) {
		options.put(option.getName().toLowerCase(), option);
		
	}
	
	/**
	 * 一つでもエラーがある場合はnullを返す
	 * @param commandParam
	 * @param sender
	 * @return
	 */
	public List<CommandOptionInterface> getOptions(String[] commandParam, CommandSender sender) {
		ArrayList<CommandOptionInterface> optionList = new ArrayList<CommandOptionInterface>();
		
		boolean errorFlg = false;
		
		CommandOptionInterface option = null;
		for (String param : commandParam) {
			//option名の取得
			param = param.trim();
			if (param.startsWith("-") && param.length() > 1) {
				option = options.get(param.substring(1).toLowerCase());
				if (option == null) {
					errorFlg = true;
					sender.sendMessage(ChatColor.RED + "パラメータ：" + param + "は存在しません。");
				}
				continue;
			}
			
			//option値の取得
			if (option != null) {
				String value = param.trim();
				//正しい値か確認
				String errorMsg = option.check(value);
				//エラーが無ければ値をセットする
				if (errorMsg == null) {
					option.setValue(value);
					optionList.add(option);
				} else {
					errorFlg = true;
					sender.sendMessage(ChatColor.RED + errorMsg);
				}
				option = null;
			}
		}
		if (errorFlg) {
			return null;
		} else {
			return optionList;
		}
	}
}
