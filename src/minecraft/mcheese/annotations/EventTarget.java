package mcheese.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface EventTarget 
{
	public static enum EventPriority 
	{ 
		HIGHEST,HIGH,NORMAL,LOW,LOWEST;
	}
	
	EventPriority priority() default EventPriority.NORMAL;
}
