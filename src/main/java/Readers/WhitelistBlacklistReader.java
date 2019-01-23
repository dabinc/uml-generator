package Readers;

import java.util.LinkedList;
import java.util.List;

import Wrappers.ClassNodeWrapper;

public class WhitelistBlacklistReader extends ReaderDecorator {

	private List<String> whitelist;
	private List<String> blacklist;

	public WhitelistBlacklistReader(Reader reader, List<String> whitelist, List<String> blacklist) {
		super(reader);
		this.whitelist = whitelist;
		this.blacklist = blacklist;
	}

	@Override
	public List<ClassNodeWrapper> getClassNodeWrappers(List<String> classNames) {
		List<ClassNodeWrapper> previousClassNodeWrappers = super.getClassNodeWrappers(classNames);
		List<ClassNodeWrapper> toReturn = new LinkedList<ClassNodeWrapper>();
		for (ClassNodeWrapper previousClassNodeWrapper : previousClassNodeWrappers) {
			if (isWhiteListed(previousClassNodeWrapper.name) || !isBlackListed(previousClassNodeWrapper.name)) {
				toReturn.add(previousClassNodeWrapper);
			}
		}
		return toReturn;
	}

	private boolean isWhiteListed(String className) {
		for (String whiteListedName : this.whitelist) {
			if (className.startsWith(whiteListedName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlackListed(String className) {
		for (String blackListedName : this.blacklist) {
			if (className.startsWith(blackListedName)) {
				return true;
			}
		}
		return false;
	}

}
