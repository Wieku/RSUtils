package pl.redstonefun.rsutils.user;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayerByRankSorter implements Comparator<Player>{

	public List<Player> sort(Player[] players){
		
		Arrays.sort(players, this);
		
		return Arrays.asList(players);
	}

	@Override
	public int compare(Player arg0, Player arg1) {
		Integer rank = new User(arg0).getRank();
		Integer rank1 = new User(arg1).getRank();
		return rank.compareTo(rank1);
	}
}
