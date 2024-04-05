package com.thelocalmarketplace.software.UI.components;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;

public class WrappedJComponent<T extends Component> extends JPanel {
	private static final long serialVersionUID = 7945193115284784435L;
	private T component;
	
	public static <U extends Component> WrappedJComponent<U> create(Class<U> clazz, Object...args) {
		Object[] constructorArgs = new Object[args.length / 2];
		Class<?>[] constructorTypes = new Class<?>[args.length / 2];
		
		for(int i = 0; i < args.length/2; i ++) {
			constructorTypes[i] = (Class<?>) args[2 * i];
			constructorArgs[i] = args[2 * i + 1];
		}
		
		return new WrappedJComponent<U>(clazz, constructorArgs, constructorTypes);
	}

	public WrappedJComponent(Class<T> clazz, Object[] args, Class<?>[] types) {
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		try {
			component = clazz.getConstructor(types).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		add(component, gbc);
	}
	
	public T getComponent() {
		return component;
	}
	
}
