package com.leonty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Selector implements Comparable<Selector> {

	private String selector;
	private String style;
	
	private Integer idCount = 0;
	private Integer classCount = 0;
	private Integer elementCount = 0;
	
	public Selector (String selector, String style) {
		
		this.selector = selector;
		this.style = style;
		calculateSpecificity();
	}
	
	private void calculateSpecificity() {
		
		uniformSelector();
		
		Pattern idPattern = Pattern.compile("#[\\w-]+");
		Pattern classPattern = Pattern.compile("\\.[\\w_-]+|\\[\\]");
		Pattern elementPattern = Pattern.compile("(^|\\s)[\\w_-]+");			
		idCount = getMatchCount(idPattern.matcher(selector));
		classCount = getMatchCount(classPattern.matcher(selector));
		elementCount = getMatchCount(elementPattern.matcher(selector));
	}
	
	private void uniformSelector() {
		
		// remove wildcards - they have no effect on specificity count
		selector = selector.replaceAll("\\*", "");
		
		// just leave [] for attribute selectors
		// remove double quotes
		selector = selector.replaceAll("\"[^\"]\\*\"", "");
		// remove single quotes
		selector = selector.replaceAll("'[^']\\*'", "");
		// remove content from attributes
		selector = selector.replaceAll("\\[[^\\]]*\\]", "[]");
		
		// remove child and descendant selectors
		selector = selector.replaceAll("[>+~]+", " ");
		
		
		// remove all function-like selectors
		// do it in a loop to remove nested ones as well
		Pattern functionPattern = Pattern.compile("\\([^)]*\\)");
		Matcher functionMatcher = functionPattern.matcher(selector);
		while (functionMatcher.find()) {
			selector = functionMatcher.replaceAll("");
		}
		
		// Pseudo elements and pseudo classes have different specificities, so we need to convert pseudo classes to 
		// temp class called .pseudo		
		selector = selector.replaceAll(":(first-child|first-letter|last-child|link|visited|hover|active|focus|lang)", ".pseudo");
		
		// Now only pseudo elements are left - so convert them into temp elements called pesudo 
		selector = selector.replaceAll(":[\\w-]+", " pseudo");		
	}
	
	private int getMatchCount(Matcher matcher) {
		int count = 0;
        while (matcher.find())
            count++;
        
        return count;
	}
	
	public Integer getIdCount() {
		return idCount;
	}

	public Integer getClassCount() {
		return classCount;
	}

	public Integer getElementCount() {
		return elementCount;
	}
	
	public String getStyle() {
		return style;
	}	

	@Override
	public String toString() {

		return idCount + " " + classCount + " " + elementCount;	
	}

	@Override
	public int compareTo(Selector that) {

		/* We can't just concatenate numbers in string and convert it back to number for comparison
		 * Consider this:
		 * 1 13 1 - it has 13 classes
		 * 1 2 10 - it has only 2 classes
		 * First one should win
		 * 
		 * 1210 > 1131, in this scenario second one wins. Concatenating numbers only works
		 * if every specificity count is below 10 
		 * 
		 * */		
		
		if (this.getIdCount() != that.getIdCount()) {
			return this.getIdCount().compareTo(that.getIdCount());
		}

		if (this.getClassCount() != that.getClassCount()) {
			return this.getClassCount().compareTo(that.getClassCount());
		}		

		if (this.getElementCount() != that.getElementCount()) {
			return this.getElementCount().compareTo(that.getElementCount());
		}		
		
		return 0;
	}
	
}
