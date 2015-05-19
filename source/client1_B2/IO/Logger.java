package com.minecraft.client.IO;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static boolean debug = false;

	private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	public static void info(String message) {
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		sb.append(format.format(date)).append(" [");
		sb.append("INFO").append("]: ");
		sb.append(message);
		System.out.println(sb.toString());
	}

	public static void warn(String message) {
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		sb.append(format.format(date)).append(" [");
		sb.append("WARN").append("]: ");
		sb.append(message);
		System.out.println(sb.toString());
	}

	public static void error(String message) {
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		sb.append(format.format(date)).append(" [");
		sb.append("ERROR").append("]: ");
		sb.append(message);
		System.out.println(sb.toString());
	}

	public static void debug(String message) {
		if (debug) {
			StringBuilder sb = new StringBuilder();
			Date date = new Date();
			sb.append(format.format(date)).append(" [");
			sb.append("DEBUG").append("]: ");
			sb.append(message);
			System.out.println(sb.toString());
		}
	}
}