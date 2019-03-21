package PreRenderTasks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PreRenderTaskDecorationFactory {

	private static PreRenderTaskDecorationFactory preRenderTaskFactory;

	private PreRenderTaskDecorationFactory() {
	}
	
	public synchronized static PreRenderTaskDecorationFactory getInstance(){
		if(preRenderTaskFactory == null){
			preRenderTaskFactory = new PreRenderTaskDecorationFactory();
		}
		return preRenderTaskFactory;
	}

	public PreRenderTask getPreRenderTask(Class<? extends PreRenderTask> preRenderTaskType,
			PreRenderTask preRenderTask) {
		PreRenderTask toReturn = preRenderTask;
		try {
			Constructor<? extends PreRenderTask> constructor = preRenderTaskType.getConstructor(PreRenderTask.class);
			toReturn = (PreRenderTask) constructor.newInstance(preRenderTask);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

}
