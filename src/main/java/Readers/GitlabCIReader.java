package Readers;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.yaml.snakeyaml.Yaml;

import Wrappers.ActivityNodeWrapper;
import Wrappers.ActivityNodeInformationWrapper;
import Wrappers.ProgramWrapper;

public class GitlabCIReader implements Reader {

	private static final List<String> invalidJobNames = new LinkedList<String>() {
		{
			add("image");
			add("services");
			add("stages");
			add("types");
			add("before_script");
			add("after_script");
			add("variables");
			add("cache");
		}
	};

	@Override
	public ProgramWrapper getProgramWrapper(List<String> classNames, List<InputStream> inputStreams) {
		ProgramWrapper toReturn = new ProgramWrapper();

		if (inputStreams.size() > 0) {
			InputStream gitlabciInputStream = inputStreams.get(0);
			Yaml gitlabciYaml = new Yaml();
			Map<String, Object> obj = (Map<String, Object>) gitlabciYaml.load(gitlabciInputStream);

			List<ActivityNodeWrapper> stages = new LinkedList<ActivityNodeWrapper>();
			List<ActivityNodeWrapper> jobs = new LinkedList<ActivityNodeWrapper>();
			for (String key : obj.keySet()) {
				if (key.equals("stages")) {
					for (String stage : (List<String>) obj.get(key)) {
						stages.add(new ActivityNodeWrapper(stage));
					}
				} else if (!invalidJobNames.contains(key)) {
					jobs.add(new ActivityNodeWrapper(getInformation(key, obj.get(key))));
				}
			}

			if (stages.isEmpty()) {
				stages.add(new ActivityNodeWrapper("build"));
				stages.add(new ActivityNodeWrapper("test"));
				stages.add(new ActivityNodeWrapper("deploy"));
			}
			toReturn.startActivity = Optional.of(stages.get(0));

			for (int i = 0; i < stages.size() - 1; i++) {
				stages.get(i).onSuccess = Optional.of(stages.get(i + 1));
			}
			
			jobs = populateJobsWithStage(jobs);
			
			linkStagesToJobs(stages, jobs);
		}

		return toReturn;
	}
	
	private List<ActivityNodeWrapper> populateJobsWithStage(List<ActivityNodeWrapper> jobs) {
		for(ActivityNodeWrapper job : jobs) {
			boolean hasStage = false;
			for(ActivityNodeInformationWrapper jobInfo : job.info.subData) {
				if(jobInfo.name.equals("stage")) {
					hasStage = true;
					break;
				}
			}
			if(!hasStage) {
				ActivityNodeInformationWrapper stageToAdd = new ActivityNodeInformationWrapper("stage");
				stageToAdd.subData.add(new ActivityNodeInformationWrapper("test"));
				job.info.subData.add(stageToAdd);
			}
		}
		return jobs;
	}
	
	private void linkStagesToJobs(List<ActivityNodeWrapper> stages, List<ActivityNodeWrapper> jobs) {
		for(ActivityNodeWrapper stage : stages) {
			for(ActivityNodeWrapper job : jobs) {
				for(ActivityNodeInformationWrapper jobInfo : job.info.subData) {
					if(jobInfo.name.equals("stage") && !jobInfo.subData.isEmpty() && jobInfo.subData.get(0).name.equals(stage.info.name)) {
						stage.subActivities.add(job);
					}
				}
			}
		}
	}

	private ActivityNodeInformationWrapper getInformation(String name, Object data) {
		ActivityNodeInformationWrapper toReturn = new ActivityNodeInformationWrapper(name);

		if (data instanceof Map) {
			Map<String, Object> actualData = (Map<String, Object>) data;
			for (String key : actualData.keySet()) {
				toReturn.subData.add(getInformation(key, actualData.get(key)));
			}
		} else if (data instanceof List) {
			List<String> actualData = (List<String>) data;
			for (String listItem : actualData) {
				toReturn.subData.add(new ActivityNodeInformationWrapper(listItem));
			}
		} else if (data instanceof String) {
			toReturn.subData.add(new ActivityNodeInformationWrapper((String) data));
		}

		return toReturn;
	}

}
