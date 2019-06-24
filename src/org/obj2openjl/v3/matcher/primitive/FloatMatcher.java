package org.obj2openjl.v3.matcher.primitive;

import java.util.regex.Pattern;

import org.obj2openjl.v3.matcher.MatchHandler;

public class FloatMatcher extends MatchHandler<Float> {
	private String lastGroup;
	private Pattern floatNumberPattern = Pattern.compile("[+-]?[0-9]*\\.[0-9]*");

	@Override
	protected Pattern getPattern() {
		return this.floatNumberPattern;
	}
	
	boolean print = false;
	
	@Override
	protected void handleMatch(String group) {
		if (group == null || group.length() <= 0){
			System.out.println("FloatMatcher.handleMatch(): lastGroup = " + lastGroup);
			return;
		}
		lastGroup = group;
		Float f = Float.parseFloat(group);
		this.addMatch(f);
	}

}
