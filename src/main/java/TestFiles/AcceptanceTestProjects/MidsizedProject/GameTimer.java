package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.time.Duration;
import java.time.Instant;

public class GameTimer {

	private static GameTimer timer;
	private Instant startTime;
	private Instant endTime;

	private GameTimer() {
		startTimer();
		endTimer();
	}

	public void startTimer() {
		this.startTime = Instant.now();
	}

	private void endTimer() {
		this.endTime = Instant.now();
	}

	public Duration getTime() {
		endTimer();
		return Duration.between(startTime, endTime);
	}

	public static GameTimer getInstance() {
		if (timer == null) {
			timer = new GameTimer();
		}
		return timer;
	}

}
