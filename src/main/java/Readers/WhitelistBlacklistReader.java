package Readers;

import java.io.InputStream;
import java.util.List;

import Wrappers.ClassNodeWrapper;
import Wrappers.ProgramWrapper;

public class WhitelistBlacklistReader extends ReaderDecorator {

	private List<String> whitelist;
	private List<String> blacklist;

	public WhitelistBlacklistReader(Reader reader, List<String> whitelist, List<String> blacklist) {
		super(reader);
		this.whitelist = whitelist;
		this.blacklist = blacklist;
	}

	@Override
	public ProgramWrapper getClassNodeWrappers(List<String> classNames, List<InputStream> inputStreams) {
		List<ClassNodeWrapper> previousClassNodeWrappers = super.getClassNodeWrappers(classNames, inputStreams).classNodeWrappers;
		ProgramWrapper toReturn = new ProgramWrapper();
		for (ClassNodeWrapper previousClassNodeWrapper : previousClassNodeWrappers) {
			if (isWhiteListed(previousClassNodeWrapper.name) || !isBlackListed(previousClassNodeWrapper.name)) {
				toReturn.classNodeWrappers.add(previousClassNodeWrapper);
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
