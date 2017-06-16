package me.messageofdeath.lib.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	
	String[] aliases();
	
	String usage() default "";
	
	String desc();
	
	String flags() default "";
	
	int max() default -1;
	
	int min() default 0;
	
	boolean ingame() default false;
	
	String[] modifiers() default { "" };
	
	String permission() default "noPerm";
}
