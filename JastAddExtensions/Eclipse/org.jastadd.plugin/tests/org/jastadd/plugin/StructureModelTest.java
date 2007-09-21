package org.jastadd.plugin;

import java.io.File;

import org.jastadd.plugin.model.repair.JastAddStructureModel;

import junit.framework.TestCase;

public class StructureModelTest extends TestCase {
	public void testCase1() {
		assertRecover("public class X {", "public class X {;}");
	}
	
	private String recover(String str) {
		StringBuffer buf = new StringBuffer();
		buf.append(str);
		JastAddStructureModel m = new JastAddStructureModel(buf);
		m.doRecovery(0);
		return buf.toString();
	}
	
	public void assertRecover(String input, String expected) {
		assertEquals(expected, recover(input));
	}
	/*
	public void assertRecover(File input, File expected) {
	}
	*/

}
