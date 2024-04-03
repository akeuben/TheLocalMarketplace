package com.thelocalmarketplace.software.UI.components;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;

public class WrappedJComponent<T extends Component> extends JPanel {
	private static final long serialVersionUID = 7945193115284784435L;
	private T component;

	public WrappedJComponent(Class<T> clazz, Object ...args) {
		Class<?>[] argTypes = new Class<?>[args.length];
		for(int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		try {
			component = clazz.getConstructor(argTypes).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		add(component);
	}
	
	public T getComponent() {
		return component;
	}
	
}
