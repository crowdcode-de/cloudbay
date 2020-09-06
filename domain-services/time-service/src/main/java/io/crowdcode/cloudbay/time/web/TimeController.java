package io.crowdcode.cloudbay.time.web;

import io.crowdcode.cloudbay.common.AnsiColor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
@Slf4j
@RestController
public class TimeController {

	@GetMapping("/now")
	public TimeResponse now() {
		TimeResponse timeResponse = new TimeResponse().setNow(LocalDateTime.now());
		log.info(AnsiColor.blue("GOT REQUEST AND SAY " + timeResponse.getNow()));
		return timeResponse;
	}
}
