package pl.redstonefun.rsutils.api;

public @interface RSCommand {
	
	String command();
	String description() default "";
	String[] aliases();
	
}
