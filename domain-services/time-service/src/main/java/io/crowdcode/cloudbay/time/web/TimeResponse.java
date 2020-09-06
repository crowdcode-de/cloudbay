package io.crowdcode.cloudbay.time.web;

import java.time.LocalDateTime;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public class TimeResponse {

	private LocalDateTime now;

	public LocalDateTime getNow() {
		return now;
	}

	public TimeResponse setNow(LocalDateTime now) {
		this.now = now;
		return this;
	}

}
