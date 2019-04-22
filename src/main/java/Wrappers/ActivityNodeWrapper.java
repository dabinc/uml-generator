package Wrappers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ActivityNodeWrapper {
	
	public Optional<ActivityNodeWrapper> onSuccess;
	public Optional<ActivityNodeWrapper> onFailure;
	
	public List<ActivityNodeWrapper> subActivities;
	
	public ActivityNodeInformationWrapper info;
	
	public ActivityNodeWrapper(String name) {
		this.onSuccess = Optional.empty();
		this.onFailure = Optional.empty();
		this.subActivities = new LinkedList<ActivityNodeWrapper>();
		this.info = new ActivityNodeInformationWrapper(name);
	}
	
	public ActivityNodeWrapper(ActivityNodeInformationWrapper info) {
		this.onSuccess = Optional.empty();
		this.onFailure = Optional.empty();
		this.subActivities = new LinkedList<ActivityNodeWrapper>();
		this.info = info;
	}

}
