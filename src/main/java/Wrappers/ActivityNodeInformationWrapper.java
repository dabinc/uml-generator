package Wrappers;

import java.util.LinkedList;
import java.util.List;

public class ActivityNodeInformationWrapper {

	public String name;
	public List<ActivityNodeInformationWrapper> subData;
	
	public ActivityNodeInformationWrapper(String name) {
		this.name = name;
		this.subData = new LinkedList<ActivityNodeInformationWrapper>();
	}
	
}
