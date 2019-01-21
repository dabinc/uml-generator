package Readers;

import java.util.LinkedList;
import java.util.List;

public class WhitelistBlacklistReaderFactory implements ReaderFactory {

	@Override
	public Reader getReader(Reader reader) {
		return new WhitelistBlacklistReader(reader, new LinkedList<String>(), new LinkedList<String>());
	}

	@Override
	public Reader getReader(Reader reader, List<String> options) {
		List<String> whitelist = new LinkedList<String>();
		List<String> blacklist = new LinkedList<String>();
		for(String option : options){
			if(option.startsWith("black-")){
				blacklist.add(option.substring(6));
			}
			else if(option.startsWith("white-")){
				whitelist.add(option.substring(6));
			}
		}
		return new WhitelistBlacklistReader(reader, whitelist, blacklist);
	}

}
