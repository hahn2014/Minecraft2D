package com.minecraft.client.misc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.minecraft.client.resources.NewComputer;

public class IniFile {

	private Pattern  _section  = Pattern.compile( "\\s*\\[([^]]*)\\]\\s*" );
   	private Pattern  _keyValue = Pattern.compile( "\\s*([^=]*)=(.*)" );
   	private Map<String,Map<String,String >>  _entries  = new HashMap<>();

   	public IniFile(String path) throws IOException {
	   	load(path);
   	}

   	public void load(String path) throws IOException {
	   	try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		   	String line;
		   	String section = null;
		   	while ((line = br.readLine()) != null) {
			   	Matcher m = _section.matcher(line);
			   	if (m.matches()) {
				   	section = m.group(1).trim();
			   	} else if (section != null) {
				   	m = _keyValue.matcher(line);
				   	if (m.matches()) {
					   	String key   = m.group(1).trim();
					   	String value = m.group(2).trim();
					   	Map<String, String> kv = _entries.get(section);
					   	if(kv == null) {
						   	_entries.put(section, kv = new HashMap<>());   
					   	}
					   	kv.put(key, value);
				   	}
			   	}
		   	}
	   	}
   	}

   	public String getString(String section, String key) {
	   	Map<String, String> kv = _entries.get(section);
	   	if (kv.get(key) == null) {
		   	return "null";
	   	}
	   	return kv.get(key);
   	}

   	public int getInt(String section, String key) {
	   	Map<String, String> kv = _entries.get(section);
	   	if (kv.get(key) == null) {
		   	return -1;
	   	}
	   	return Integer.parseInt(kv.get(key));
   	}
   
   	public float getFloat(String section, String key) {
	   	Map<String, String> kv = _entries.get(section);
	   	if (kv.get(key) == null) {
		   	return 0.0f;
	   	}
	   	return Float.parseFloat(kv.get(key));
   	}
   
   	public double getDouble(String section, String key) {
	   	Map<String, String> kv = _entries.get(section);
	   	if (kv.get(key) == null) {
		   	return 0.0;
	   	}
	   	return Double.parseDouble(kv.get(key));
   	}
   
   	public boolean getBoolean(String section, String key) {
	   	Map<String, String> kv = _entries.get(section);
	   	if (kv.get(key) == null) {
		   	return true;
	   	}
	   	return Boolean.parseBoolean(kv.get(key));
   	}
   
   	public void setValue(String key, String value) {
	   	Properties p = new Properties();
	   	try {
		   	p.store(new FileOutputStream(NewComputer.settingsFile), null);
		   	p.setProperty(key, value);
	   	} catch (FileNotFoundException e) {
		   	e.printStackTrace();
	   	} catch (IOException e) {
		   	e.printStackTrace();
	   	}
   	}
   	
   	public void setValue(String key, int value) {
	   	Properties p = new Properties();
	   	try {
		   	p.store(new FileOutputStream(NewComputer.settingsFile), null);
		   	p.setProperty(key, Integer.toString(value));
	   	} catch (FileNotFoundException e) {
		   	e.printStackTrace();
	   	} catch (IOException e) {
		   	e.printStackTrace();
	   	}
   	}
   	
   	public void setValue(String key, Boolean value) {
	   	Properties p = new Properties();
	   	try {
		   	p.store(new FileOutputStream(NewComputer.settingsFile), null);
		   	p.setProperty(key, Boolean.toString(value));
	   	} catch (FileNotFoundException e) {
		   	e.printStackTrace();
	   	} catch (IOException e) {
		   	e.printStackTrace();
	   	}
   	}
   	
   	public void setValue(String key, float value) {
	   	Properties p = new Properties();
	   	try {
		   	p.store(new FileOutputStream(NewComputer.settingsFile), null);
		   	p.setProperty(key, Float.toString(value));
	   	} catch (FileNotFoundException e) {
		   	e.printStackTrace();
	   	} catch (IOException e) {
		   	e.printStackTrace();
	   	}
   	}
   	
   	public void setValue(String key, double value) {
	   	Properties p = new Properties();
	   	try {
		   	p.store(new FileOutputStream(NewComputer.settingsFile), null);
		   	p.setProperty(key, Double.toString(value));
	   	} catch (FileNotFoundException e) {
		   	e.printStackTrace();
	   	} catch (IOException e) {
		   	e.printStackTrace();
	   	}
   	}
}