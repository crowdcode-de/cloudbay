package io.crowdcode.cloudbay.catalog.service;

import java.time.LocalDateTime;

/**
 * @author Ingo Düppe (CROWDcODE)
 */
public class TimeResponse implements Time {

	private LocalDateTime now;

	@Override
	public LocalDateTime getNow() {
		return now;
	}

	public void setNow(LocalDateTime now) {
		this.now = now;
	}
}
