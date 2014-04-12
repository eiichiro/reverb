/*
 * Copyright (C) 2011 Eiichiro Uchiumi. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eiichiro.reverb.time;

import static org.eiichiro.reverb.time.Stopwatch.Precision.*;

import java.io.Serializable;

/**
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class Stopwatch implements Serializable, Cloneable {

	private static final long serialVersionUID = 4724234150088994221L;
	
	/**
	 * {@code Precision} represents the precision of elapsed time with 4 level.
	 *
	 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
	 */
	public static enum Precision {
		
		/**
		 * The second precision.
		 * This is accuracy of 1/1000000000 at the nanosecond.
		 */
		SECOND(0.000000001), 
		
		/**
		 * The millisecond precision.
		 * This is accuracy of 1/1000000 at the nanosecond.
		 */
		MILLISECOND(0.000001), 
		
		/**
		 * The microsecond precision.
		 * This is accuracy of 1/1000 at the nanosecond.
		 */
		MICROSECOND(0.001), 
		
		/**
		 * The nanosecond precision.
		 * This is the most accurate.
		 */
		NANOSECOND(1);
		
		/** The precision value based on the nanosecond. */
		private final double value;
		
		/**
		 * Constructs a {@code Precision} instance with specified precision 
		 * value.
		 * 
		 * @param value The precision value based on the nanosecond.
		 */
		Precision(double value) {
			this.value = value;
		}
		
	}
	
	/** Total time. */
	protected long total = 0;

	/** Start time. */
	protected long start = 0;
	
	/** Stop time. */
	protected long stop = 0;
	
	/** Is this {@code Stopwatch} started or not. */
	protected boolean running = false;
	
	/** The precision that this {@code Stopwatch} instance measures. */
	protected final Precision precision;
	
	/**
	 * Constructs a new {@code Stopwatch} instance with the millisecond 
	 * precision.
	 */
	public Stopwatch() {
		this(MILLISECOND);
	}
	
	/**
	 * Constructs a {@code Stopwatch} instance with specified {@link Precision}.
	 * 
	 * @param precision The precision that this {@code Stopwatch} instance 
	 * measures with.
	 */
	public Stopwatch(Precision precision) {
		if (precision == null) {
			throw new IllegalArgumentException("'precision' must not be ["
					+ precision + "]");
		} else {
			this.precision = precision;
		}
	}
	
	/** Starts to measure. */
	public void start() {
		if (!isRunning()) {
			start = System.nanoTime();
			running = true;
		}
	}
	
	/**
	 * Stops to measure.
	 * 
	 * @return Elapsed time.
	 */
	public long stop() {
		if (isRunning()) {
			stop = System.nanoTime();
			total += stop - start;
			running = false;
		}
		
		return Math.round(total * getPrecision().value);
	}
	
	/** Stops to measure and resets each values. */
	public void reset() {
		start = 0;
		stop = 0;
		total = 0;
		running = false;
	}
	
	/**
	 * Returns the elapsed time with specified precision since this 
	 * {@code Stopwatch} instance started to measure. The result is rounded to 
	 * long.
	 * 
	 * @see Math#round(double)
	 * @return The elapsed time with specified precision.
	 */
	public long getTime() {
		long time = (isRunning()) ? total + System.nanoTime() - start : total;
		return Math.round(time * getPrecision().value);
	}
	
	/**
	 * Returns this {@code Stopwatch} is running or not.
	 * 
	 * @return This {@code Stopwatch} is running or not.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Returns the precision that this {@code Stopwatch} instance measures with.
	 * 
	 * @return The precision that this {@code Stopwatch} instance measures with.
	 */
	public Precision getPrecision() {
		return precision;
	}
	
	/**
	 * Returns a string representation of this instance.
	 * 
	 * @return A string representation of this instance.
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		long time = (isRunning()) ? total + System.nanoTime() - start : total;
		long hour = (long) (time / (60 * 60 / SECOND.value));
		long remainder = (long) (time - (hour * (60 * 60 / SECOND.value)));
		long minute = (long) (remainder / (60 / SECOND.value));
		remainder = (long) (remainder - (minute * (60 / SECOND.value)));
		long second = 0;
		
		if (getPrecision().equals(SECOND)) {
			second = Math.round(remainder * SECOND.value);
		} else {
			second = (long) (remainder * SECOND.value);
		}
		
		stringBuilder.append(hour + ":");
		
		if (minute < 10) {
			stringBuilder.append("0");
		}
		
		stringBuilder.append(minute + ":");
		
		if (second < 10) {
			stringBuilder.append("0");
		}
		
		stringBuilder.append(second);
		
		if (!getPrecision().equals(SECOND)) {
			stringBuilder.append(".");
			remainder = (long) (remainder - (second / SECOND.value));
			int formatLength = 0;
			
			if (getPrecision().equals(MILLISECOND)) {
				remainder = Math.round(remainder * MILLISECOND.value);
				formatLength = 3;
			} else if (getPrecision().equals(MICROSECOND)) {
				remainder = Math.round(remainder * MICROSECOND.value);
				formatLength = 6;
			} else {
				formatLength = 9;
			}
			
			int remainderLength = String.valueOf(remainder).length();
			
			if (remainderLength < formatLength) {
				for (int i = 0; i < formatLength - remainderLength; i++) {
					stringBuilder.append("0");
				}
			}
			
			stringBuilder.append(remainder);
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * {@code SynchronizedStopwatch} wraps and synchronizes {@code Stopwatch}.
	 *
	 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
	 */
	private static class SynchronizedStopwatch extends Stopwatch {
		
		private static final long serialVersionUID = -4701060525503813545L;
		
		/** A {@code Stopwatch} instance to be synchronized. */
		private Stopwatch stopwatch;
		
		/**
		 * Constructs a {@code SynchronizedStopwatch} instance with specified 
		 * {@code Stopwatch} instance.
		 * 
		 * @param stopwatch A {@code Stopwatch} instance to be synchronized. 
		 */
		SynchronizedStopwatch(Stopwatch stopwatch) {
			this.stopwatch = stopwatch;
		}

		/** Starts to measure being synchronized. */
		@Override
		public synchronized void start() {
			stopwatch.start();
		}
		
		/**
		 * Stops to measure being synchronized.
		 * 
		 * @return Elapsed time.
		 */
		@Override
		public synchronized long stop() {
			return stopwatch.stop();
		}
		
		/** Stops to measure and resets each values being synchronized. */
		@Override
		public synchronized void reset() {
			stopwatch.stop();
		}
		
		/**
		 * Returns the elapsed time being synchronized with specified precision 
		 * since this {@code Stopwatch} started to measure. The result is 
		 * rounded to long.
		 * 
		 * @see Stopwatch#getTime()
		 * @return The elapsed time with specified precision.
		 */
		@Override
		public synchronized long getTime() {
			return stopwatch.getTime();
		}
		
		/**
		 * Returns this {@code Stopwatch} is running or not being synchronized.
		 * 
		 * @return This {@code Stopwatch} is running or not.
		 */
		@Override
		public synchronized boolean isRunning() {
			return stopwatch.isRunning();
		}
		
		/**
		 * Returns the precision that this {@code Stopwatch} instance measures 
		 * with being synchronized.
		 * 
		 * @return The precision that this {@code Stopwatch} instace measures 
		 * with.
		 */
		@Override
		public synchronized Precision getPrecision() {
			return stopwatch.getPrecision();
		}
		
		/**
		 * Returns a string representation of this instance being synchronized.
		 * 
		 * @return A string representation of this instance.
		 */
		@Override
		public synchronized String toString() {
			return stopwatch.toString();
		}
		
	}
	
	/**
	 * Returns a {@code Stopwatch} instance to be synchronized.
	 * 
	 * @param stopwatch {@code Stopwatch} instance to synchronize.
	 * @return {@code Stopwatch} instance to be synchronized.
	 */
	public static Stopwatch synchronizedStopwatch(Stopwatch stopwatch) {
		return new SynchronizedStopwatch(stopwatch);
	}
	
}
