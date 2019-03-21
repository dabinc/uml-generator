package Readers;

import java.io.InputStream;
import java.util.List;

import Wrappers.ClassNodeWrapper;
import Wrappers.ProgramWrapper;
import Wrappers.SequenceWrapper;

public class WhitelistBlacklistReader extends ReaderDecorator {

	private List<String> whitelist;
	private List<String> blacklist;

	public WhitelistBlacklistReader(Reader reader, List<String> whitelist, List<String> blacklist) {
		super(reader);
		this.whitelist = whitelist;
		this.blacklist = blacklist;
	}

	@Override
	public ProgramWrapper getProgramWrapper(List<String> classNames, List<InputStream> inputStreams) {
		ProgramWrapper previous = super.getProgramWrapper(classNames, inputStreams);
		ProgramWrapper toReturn = new ProgramWrapper();
		for (ClassNodeWrapper previousClassNodeWrapper : previous.classNodeWrappers) {
			if (isWhiteListed(previousClassNodeWrapper.name) || !isBlackListed(previousClassNodeWrapper.name)) {
				toReturn.classNodeWrappers.add(previousClassNodeWrapper);
			}
		}
		for (SequenceWrapper previousSequenceWrapper : previous.sequenceWrappers) {
			if (isWhiteListed(previousSequenceWrapper.methodType)
					|| !isBlackListed(previousSequenceWrapper.methodType)) {
				toReturn.sequenceWrappers.add(previousSequenceWrapper);
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
