package me.armar.plugins.autorank.playerchecker.requirement;

import java.util.ArrayList;
import java.util.List;

import me.armar.plugins.autorank.AutorankTools;
import me.armar.plugins.autorank.language.Lang;
import me.armar.plugins.autorank.playerchecker.result.Result;

import org.bukkit.entity.Player;

public class TimeRequirement extends Requirement {

	int time = -1;
	private boolean optional = false;
	private boolean autoComplete = false;
	private int reqId;
	List<Result> results = new ArrayList<Result>();

	@Override
	public boolean setOptions(String[] options, boolean optional,
			List<Result> results, boolean autoComplete, int reqId) {
		this.optional = optional;
		this.results = results;
		this.autoComplete = autoComplete;
		this.reqId = reqId;

		if (options.length > 0)
			this.time = AutorankTools.stringToMinutes(options[0]);
		return (time != -1);
	}

	@Override
	public boolean meetsRequirement(Player player) {
		if (isCompleted(getReqId(), player.getName())) {
			return true;
		}

		double playtime = this.getAutorank().getPlaytimes()
				.getLocalTime(player.getName());
		return time != -1 && time <= playtime;
	}

	@Override
	public String getDescription() {
		return Lang.TIME_REQUIREMENT.getConfigValue(new String[] {AutorankTools.minutesToString(time)});
	}

	@Override
	public boolean isOptional() {
		return optional;
	}

	@Override
	public List<Result> getResults() {
		return results;
	}

	@Override
	public String getProgress(Player player) {
		String progress = "";
		progress = progress.concat(getAutorank().getPlaytimes().getLocalTime(
				player.getName()) + " min" 
				+ "/" + time + " min");
		return progress;
	}

	@Override
	public boolean useAutoCompletion() {
		return autoComplete;
	}
	
	@Override
	public int getReqId() {
		return reqId;
	}

}
