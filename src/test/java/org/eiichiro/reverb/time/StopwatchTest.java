package org.eiichiro.reverb.time;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.eiichiro.reverb.time.Stopwatch.Precision.*;

import org.junit.Test;

public class StopwatchTest {

	@Test
	public void testStopwatch() {
		new Stopwatch();
	}

	@Test
	public void testStopwatchPrecision() {
		new Stopwatch(SECOND);
		new Stopwatch(MILLISECOND);
		new Stopwatch(MICROSECOND);
		new Stopwatch(NANOSECOND);
		
		try {
			new Stopwatch(null);
			fail();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			assertThat(e.getMessage(), is("'precision' must not be [null]"));
		}
	}

	@Test
	public void testStart() {
		Stopwatch stopwatch = new Stopwatch();
		assertThat(stopwatch.isRunning(), is(false));
		stopwatch.start();
		assertThat(stopwatch.isRunning(), is(true));
	}

	@Test
	public void testStop() throws InterruptedException {
		Stopwatch stopwatch = new Stopwatch();
		assertThat(stopwatch.stop(), is(0L));
		stopwatch.start();
		Thread.sleep(1000);
		long stop = stopwatch.stop();
		assertThat(stop >= 1000, is(true));
		assertThat(stopwatch.isRunning(), is(false));
	}

	@Test
	public void testReset() throws InterruptedException {
		Stopwatch stopwatch = new Stopwatch();
		stopwatch.start();
		Thread.sleep(1000);
		stopwatch.reset();
		assertThat(stopwatch.getTime(), is(0L));
		assertThat(stopwatch.isRunning(), is(false));
	}

	@Test
	public void testGetTime() throws InterruptedException {
		Stopwatch stopwatch = new Stopwatch();
		assertThat(stopwatch.getTime(), is(0L));
		stopwatch.start();
		Thread.sleep(1000);
		assertThat(stopwatch.getTime() >= 1000, is(true));
	}

	@Test
	public void testIsRunning() {
		Stopwatch stopwatch = new Stopwatch();
		assertThat(stopwatch.isRunning(), is(false));
		stopwatch.start();
		assertThat(stopwatch.isRunning(), is(true));
		stopwatch.stop();
		assertThat(stopwatch.isRunning(), is(false));
	}

	@Test
	public void testGetPrecision() {
		assertThat(new Stopwatch(SECOND).getPrecision(), is(SECOND));
		assertThat(new Stopwatch(MILLISECOND).getPrecision(), is(MILLISECOND));
		assertThat(new Stopwatch(MICROSECOND).getPrecision(), is(MICROSECOND));
		assertThat(new Stopwatch(NANOSECOND).getPrecision(), is(NANOSECOND));
	}

	@Test
	public void testToString() throws InterruptedException {
		Stopwatch stopwatch = new Stopwatch(SECOND);
		assertThat(stopwatch.toString(), is("0:00:00"));
		stopwatch.start();
		Thread.sleep(1000);
		stopwatch.stop();
		assertThat(stopwatch.toString().compareTo("0:00:01") >= 0, is(true));
		
		stopwatch = new Stopwatch(MILLISECOND);
		assertThat(stopwatch.toString(), is("0:00:00.000"));
		stopwatch.start();
		Thread.sleep(1000);
		stopwatch.stop();
		assertThat(stopwatch.toString().compareTo("0:00:01.000") >= 0, is(true));
		
		stopwatch = new Stopwatch(MICROSECOND);
		assertThat(stopwatch.toString(), is("0:00:00.000000"));
		stopwatch.start();
		Thread.sleep(1000);
		stopwatch.stop();
		assertThat(stopwatch.toString().compareTo("0:00:01.000000") >= 0, is(true));
		
		stopwatch = new Stopwatch(NANOSECOND);
		assertThat(stopwatch.toString(), is("0:00:00.000000000"));
		stopwatch.start();
		Thread.sleep(1000);
		stopwatch.stop();
		assertThat(stopwatch.toString().compareTo("0:00:01.000000000") >= 0, is(true));
	}

	@Test
	public void testSynchronizedStopwatch() {}

}
