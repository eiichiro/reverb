package org.eiichiro.reverb.lang;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eiichiro.reverb.lang.ClassResolver.Matcher;
import org.eiichiro.reverb.system.Environment;
import org.junit.Test;

public class JLCClassResolverTest {

	@Test
	public void testClassResolverIterable() throws IOException {
		List<URL> paths = new ArrayList<URL>();
		paths.add(new URL("http://reverb.eiichiro.org/"));
		JLCClassResolver resolver = new JLCClassResolver(paths);
		assertThat(resolver.paths(), is((Iterable<URL>) paths));
	}

	@Test
	public void testClassResolverClassLoaderIterable() throws MalformedURLException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<URL> paths = new ArrayList<URL>();
		paths.add(new URL("http://reverb.eiichiro.org/"));
		JLCClassResolver resolver = new JLCClassResolver(classLoader, paths);
		assertThat(resolver.classLoader(), is(classLoader));
		assertThat(resolver.paths(), is((Iterable<URL>) paths));
	}

	@Test
	public void testResolveByName() throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<URL> paths = new ArrayList<URL>();
		
		if (classLoader instanceof URLClassLoader) {
			// JDK <=8
			paths = Arrays.asList(((URLClassLoader) classLoader).getURLs());
		} else {
			// JDK >9
			for (String path : Environment.getProperty("java.class.path").split(File.pathSeparator)) {
				paths.add(new File(path).toURI().toURL());
			}
		}
		
		JLCClassResolver resolver = new JLCClassResolver(paths);
		System.out.println(resolver.paths());
		Set<Class<?>> set = resolver.resolveByName("bject1");
		assertThat(set.contains(Object1.class), is(true));
		assertThat(set.contains(Object2.class), is(false));
		assertThat(set.contains(Object3.class), is(false));
		assertThat(set.contains(Object4.class), is(false));
		assertThat(set.contains(Object5.class), is(false));
	}

	@Test
	public void testResolveBySuperclass() throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<URL> paths = new ArrayList<URL>();
		
		if (classLoader instanceof URLClassLoader) {
			// JDK <=8
			paths = Arrays.asList(((URLClassLoader) classLoader).getURLs());
		} else {
			// JDK >9
			for (String path : Environment.getProperty("java.class.path").split(File.pathSeparator)) {
				paths.add(new File(path).toURI().toURL());
			}
		}
		
		JLCClassResolver resolver = new JLCClassResolver(paths);
		System.out.println(resolver.paths());
		Set<Class<?>> set = resolver.resolveBySuperclass(Superclass1.class);
		assertThat(set.contains(Object1.class), is(false));
		assertThat(set.contains(Object2.class), is(true));
		assertThat(set.contains(Object3.class), is(false));
		assertThat(set.contains(Object4.class), is(false));
		assertThat(set.contains(Object5.class), is(true));
	}

	@Test
	public void testResolveByInterface() throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<URL> paths = new ArrayList<URL>();
		
		if (classLoader instanceof URLClassLoader) {
			// JDK <=8
			paths = Arrays.asList(((URLClassLoader) classLoader).getURLs());
		} else {
			// JDK >9
			for (String path : Environment.getProperty("java.class.path").split(File.pathSeparator)) {
				paths.add(new File(path).toURI().toURL());
			}
		}
		
		JLCClassResolver resolver = new JLCClassResolver(paths);
		System.out.println(resolver.paths());
		Set<Class<?>> set = resolver.resolveByInterface(Interface1.class);
		assertThat(set.contains(Object1.class), is(false));
		assertThat(set.contains(Object2.class), is(false));
		assertThat(set.contains(Object3.class), is(true));
		assertThat(set.contains(Object4.class), is(false));
		assertThat(set.contains(Object5.class), is(true));
	}

	@Test
	public void testResolveByAnnotation() throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<URL> paths = new ArrayList<URL>();
		
		if (classLoader instanceof URLClassLoader) {
			// JDK <=8
			paths = Arrays.asList(((URLClassLoader) classLoader).getURLs());
		} else {
			// JDK >9
			for (String path : Environment.getProperty("java.class.path").split(File.pathSeparator)) {
				paths.add(new File(path).toURI().toURL());
			}
		}
		
		JLCClassResolver resolver = new JLCClassResolver(paths);
		System.out.println(resolver.paths());
		Set<Class<?>> set = resolver.resolveByAnnotation(Annotation1.class);
		assertThat(set.contains(Object1.class), is(false));
		assertThat(set.contains(Object2.class), is(false));
		assertThat(set.contains(Object3.class), is(false));
		assertThat(set.contains(Object4.class), is(true));
		assertThat(set.contains(Object5.class), is(true));
	}

	@Test
	public void testResolve() throws IOException, ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<URL> paths = new ArrayList<URL>();
		
		if (classLoader instanceof URLClassLoader) {
			// JDK <=8
			paths = Arrays.asList(((URLClassLoader) classLoader).getURLs());
		} else {
			// JDK >9
			for (String path : Environment.getProperty("java.class.path").split(File.pathSeparator)) {
				paths.add(new File(path).toURI().toURL());
			}
		}
		
		JLCClassResolver resolver = new JLCClassResolver(paths);
		System.out.println(resolver.paths());
		Set<Class<?>> set = resolver.resolve(new Matcher<Class<?>>() {
			
			public boolean matches(Class<?> clazz) {
				if (clazz.getAnnotation(Annotation1.class) != null
						&& clazz.getSuperclass().equals(Superclass1.class)
						&& Arrays.asList(clazz.getInterfaces()).contains(Interface1.class)) {
					return true;
				}
				
				return false;
			}
			
		});
		assertThat(set.contains(Object1.class), is(false));
		assertThat(set.contains(Object2.class), is(false));
		assertThat(set.contains(Object3.class), is(false));
		assertThat(set.contains(Object4.class), is(false));
		assertThat(set.contains(Object5.class), is(true));
	}

}
