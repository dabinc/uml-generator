package Containers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import Wrappers.ActivityNodeWrapper;

public class ActivityContainer {
	
	public ActivityNodeWrapper activityNodeWrapper;
	public List<ActivityContainer> subActivities;
	public Optional<ActivityContainer> onSuccess;
	public Optional<ActivityContainer> onFailure;
	
	public DisplayContainer displayContainer;
	public List<StereotypeContainer> stereotypeContainers;
	
	public ActivityContainer(ActivityNodeWrapper activityNodeWrapper) {
		this.activityNodeWrapper = activityNodeWrapper;
		this.subActivities = new LinkedList<ActivityContainer>();
		this.onSuccess = Optional.empty();
		this.onFailure = Optional.empty();
		this.displayContainer = new DisplayContainer();
		this.stereotypeContainers = new LinkedList<StereotypeContainer>();
	}

}
