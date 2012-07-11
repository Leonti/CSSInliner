package com.leonty.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.leonty.Selector;

public class SelectorTest {

	@Test
	public void testId() {	
		testCounts(new Selector("#someId", ""), 1, 0, 0);
	}
	
	@Test
	public void testClass() {
		testCounts(new Selector(".someClass", ""), 0, 1, 0);	
	}
	
	@Test
	public void testElement() {
		testCounts(new Selector("div", ""), 0, 0, 1);		
	}
	
	@Test
	public void testCombined() {		
		testCounts(new Selector("ul#nav li.active a", ""), 1, 1, 3);
		
		testCounts(new Selector("body.ie7 .col_3 h2 ~ h2", ""), 0, 2, 3);

		testCounts(new Selector("#footer *:not(nav) li", ""), 1, 0, 2);
			
		testCounts(new Selector("ul > li ul li ol li:first-letter", ""), 0, 1, 6);		
	}
	
	private void testCounts(Selector selector, Integer idCount, Integer classCount, Integer elementCount) {
		assertEquals("Id count", idCount, selector.getIdCount());
		assertEquals("Class count", classCount, selector.getClassCount());
		assertEquals("Element count", elementCount, selector.getElementCount());		
	}
	
}
