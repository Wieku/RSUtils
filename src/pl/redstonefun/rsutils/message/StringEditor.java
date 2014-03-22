package pl.redstonefun.rsutils.message;

import java.util.HashMap;

public class StringEditor{
	
	HashMap<Integer,Integer> chars = new HashMap<Integer,Integer>();
	HashMap<Integer,Boolean> edited = new HashMap<Integer,Boolean>();
	StringBuilder buil;
	
	String text;
	
	public StringEditor(String string){
		buil = new StringBuilder(string);
		int j=0;
		for(int i=0; i<string.length();i++){
			if(string.charAt(i) == '%'){
				chars.put(j, i);
				edited.put(j, false);
				++j;
			}
		}
	}
	
	public StringEditor write(int num, String replace){
		try {	
			if(chars.containsKey(num)){
				
				if(!edited.get(num)){
					int g = chars.get(num);
					buil.replace(g, g+1, replace);
					
					edited.put(num, true);
					
					for(int k = num + 1;chars.containsKey(k);k++){
						chars.put(k, chars.get(k) + replace.length()-1);
					}
					
				} else {
					throw new Exception("% on that index has arleady edited");
				}
				
			} else {
			
				throw new Exception("String doesn't contain % on that index");
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public String get(){
		return buil.toString();
	}
	
	
}
