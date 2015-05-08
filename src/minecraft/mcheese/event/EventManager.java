package mcheese.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import mcheese.MCheese;
import mcheese.annotations.EventTarget;
import mcheese.annotations.EventTarget.EventPriority;

public class EventManager 
{
	private HashMap<EventListener, HashMap<Class<? extends Event>, ArrayList<Method>>> HANDLERS = new HashMap<EventListener, HashMap<Class<? extends Event>, ArrayList<Method>>>();
	
	public void callEvent(Event event)
	{
		synchronized(this.HANDLERS)
		{
			HashMap<EventListener, HashMap<Class<? extends Event>, ArrayList<Method>>> handlers = new HashMap<EventListener, HashMap<Class<? extends Event>,ArrayList<Method>>>();
			handlers.putAll(HANDLERS);
			
			for(int i = 0; i < EventPriority.values().length; ++i)
			{
				EventPriority pending_priority = EventPriority.values()[i];
							
				Iterator<EventListener> iter = handlers.keySet().iterator();
				while(iter.hasNext())
				{				
					EventListener obj = iter.next();
					
					ArrayList<Method> handler_list = handlers.get(obj).containsKey(event.getClass()) ? handlers.get(obj).get(event.getClass()) : null;
					if(handler_list != null)
					{
						for(Method m : handler_list)
						{
							if(!isValidMethod(m))
							{
								continue;
							}
							EventTarget annot = m.getAnnotation(EventTarget.class);
							if(annot.priority() == pending_priority)
							{
								try 
								{
									m.invoke(obj, event);
									
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
	public void unregisterHandlers(EventListener obj)
	{
		synchronized(this.HANDLERS)
		{
			if(HANDLERS.containsKey(obj))
			{
				HANDLERS.remove(obj);
			}
		}
	}
	public void registerHandlers(EventListener obj)
	{
		synchronized(this.HANDLERS)
		{
			HashMap<Class<? extends Event>, ArrayList<Method>> method_map = new HashMap<Class<? extends Event>, ArrayList<Method>>();
			
			for(int i = 0; i < obj.getClass().getMethods().length; ++i)
			{
				Method m = obj.getClass().getMethods()[i];
				if(!isValidMethod(m))
				{
					continue;
				}
				Class<? extends Event> event_type = (Class<? extends Event>) m.getParameterTypes()[0];
				
				ArrayList<Method> methods;
				if(method_map.containsKey(event_type))
				{
					methods = method_map.get(event_type);
				}
				else
				{
					methods = new ArrayList<Method>();
				}
				methods.add(m);
				method_map.put(event_type, methods);
			}
			if(!method_map.isEmpty())
			{
				HANDLERS.put(obj, method_map);
			}
		}
	}
	private static boolean isValidMethod(Method m)
	{
		boolean bool = false;
		
		for(int i = 0; i < m.getAnnotations().length; ++i)
		{
			if(m.getAnnotations()[i] instanceof EventTarget)
			{
				bool = true;
			}
		}
		if(bool)
		{
			if(m.getParameterTypes().length == 1 && m.getParameterTypes()[0].getSuperclass().equals(Event.class))
			{
				return true;
			}
		}
		return false;
	}
}
