package me.messageofdeath.lib.pagelists;

import java.util.ArrayList;
import org.bukkit.command.CommandSender;

public class PageList {

	private ArrayList<Option> options = new ArrayList<>();
	private int amountPerPage;

	public PageList(int amountPerPage) {
		this.options = new ArrayList<>();
		this.amountPerPage = amountPerPage;
	}

	public PageList(ArrayList<Option> options, int amountPerPage) {
		this.options = options;
		if (this.options == null) {
			this.options = new ArrayList<>();
		}
		this.amountPerPage = amountPerPage;
	}

	public void addOption(Option option) {
		this.options.add(option);
	}

	public void setAmountPerPage(int amountPerPage) {
		this.amountPerPage = amountPerPage;
	}

	public int getAmountOfOptions() {
		return this.options.size();
	}

	public ArrayList<String> getOptions(CommandSender name, int page) {
		if (!this.options.isEmpty()) {
			ArrayList<String> texts = new ArrayList<>();
			int id = page * this.amountPerPage;
			for (int i = id; i < id + this.amountPerPage; i++) {
				if (i >= this.options.size()) {
					break;
				}
				if (this.options.get(i).hasPermission(name)) {
					texts.add(this.options.get(i).getText());
				}
			}
			//return this.options.subList(id, id + this.amountPerPage);
			return texts;
		}
		return new ArrayList<>();
	}

	public int checkPage(CommandSender name, int page) {
		if (!this.options.isEmpty()) {
			/*if (page >= Integer.MAX_VALUE) {
				page = 2147483646;
			}
			if (page <= Integer.MIN_VALUE) {
				page = -2147483647;
			}*/
			page--;
			if (page < 0) {
				page = 0;
			}
			if (page > getTotalPages(name) - 1) {
				page = getTotalPages(name) - 1;
			}
			return page;
		}
		return 0;
	}

	public int getTotalPages(CommandSender name) {
		if (!this.options.isEmpty()) {
			int text = 0;
			for (Option option : this.options) {
				if (option.hasPermission(name)) {
					text++;
				}
			}
			if (text % this.amountPerPage == 0) {
				return text / this.amountPerPage;
			}
			if (text % this.amountPerPage > 0) {
				return text / this.amountPerPage + 1;
			}
		}
		return 0;
	}
}
