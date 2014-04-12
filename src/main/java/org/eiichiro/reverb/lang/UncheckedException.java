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
package org.eiichiro.reverb.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * {@code UncheckedException} is an uncheckable wrapper of a checked exception.
 * 
 * @author <a href="mailto:mail@eiichiro.org">Eiichiro Uchiumi</a>
 */
public class UncheckedException extends RuntimeException {

	private static final long serialVersionUID = 9189038064891756128L;
	
	/** The checked exception to be wrapped. */
	protected final Exception cause;
	
	/**
	 * Constructs a new {@code UncheckedException} with the specified checked 
	 * exception.
	 * 
	 * @param cause The checked exception to be wrapped.
	 */
	public UncheckedException(Exception cause) {
		this.cause = cause;
	}
	
	@Override
	public void printStackTrace(PrintWriter s) {
		cause.printStackTrace(s);
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		cause.printStackTrace(s);
	}
	
	@Override
	public void printStackTrace() {
		cause.printStackTrace();
	}
	
	@Override
	public String toString() {
		return cause.toString();
	}
	
	@Override
	public Throwable getCause() {
		return cause.getCause();
	}
	
	@Override
	public String getLocalizedMessage() {
		return cause.getLocalizedMessage();
	}
	
	@Override
	public String getMessage() {
		return cause.getMessage();
	}
	
	@Override
	public StackTraceElement[] getStackTrace() {
		return cause.getStackTrace();
	}
	
	@Override
	public synchronized Throwable initCause(Throwable cause) {
		return this.cause.initCause(cause);
	}
	
	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		cause.setStackTrace(stackTrace);
	}
	
	@Override
	public int hashCode() {
		return cause.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return cause.equals(obj);
	}
	
}
