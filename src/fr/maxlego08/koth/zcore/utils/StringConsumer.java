package fr.maxlego08.koth.zcore.utils;

@FunctionalInterface
public interface StringConsumer<T> {

	String accept(T t);
	
}
