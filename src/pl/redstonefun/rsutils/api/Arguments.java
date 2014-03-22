package pl.redstonefun.rsutils.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arguments{

	List<String> args = new ArrayList<String>();
	public int length;
	
	public Arguments(String[] args){
		length = args.length;
		this.args = Arrays.asList(args);
	}
	
	public String get(int index){
		return args.get(index);
	}
	
	public String getFT(int start, int end, String separator){
		String j = "";
		for(int i = start;i<=end && i < args.size(); i++){
			j += (i==start?"":separator) + args.get(i);
		}
		return j;
	}
	
}
