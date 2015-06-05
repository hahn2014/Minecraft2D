package com.minecraft.client.misc;

public class Tribool {
	
	public enum VALUE {
	    t, f, n 
	}
	

	VALUE value = VALUE.n;
	
	public Tribool(VALUE val) {
		value = val;
	}
	
	@Override
	public String toString() {
		if (value == VALUE.t)
			return "true";
		else if (value == VALUE.f)
			return "false";
		else
			return "null";
	}
	
	public VALUE toValue() {
		return value;
	}
	
	public boolean toBoolean() {
		if (value == VALUE.t)
			return true;
		else if (value == VALUE.f)
			return false;
		else
			return true;
	}
	
	public void setTribool(VALUE val) {
		value = val;
	}
	
	public VALUE valFromString(String str) {
		if (str == "true")
			return VALUE.t;
		else if (str == "false")
			return VALUE.f;
		else
			return VALUE.n;
	}
}