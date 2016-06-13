package generate.command.generate;

import generate.command.CommandOptionInterface;
import generate.command.generate.option.MaxOption;
import generate.command.generate.option.MinOption;
import generate.command.generate.option.SmoothOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GenerateCommandOptionParser {
	static HashMap<String, CommandOptionInterface> options = new HashMap<String, CommandOptionInterface>();
	static Set<String> tabComlate = new HashSet<String>();

	{
		regist(new MaxOption());
		regist(new MinOption());
		regist(new SmoothOption());
	}

	public static void regist(CommandOptionInterface option) {
		options.put(option.getName().toLowerCase(), option);
		tabComlate.add("-" + option.getName().toUpperCase());
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

	/**
	 * 登録されているオプションの一覧を取得
	 * @return
	 */
	public Set<String> getTabCompleteList() {
		return tabComlate;
	}
}
