package org.openmrs.module.pcslabinterface.rules;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.module.pcslabinterface.PcsLabInterfaceConstants;
import org.openmrs.test.Verifies;

public class RemoveValueModifiersFromHIVViralLoadsTest {
	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#RemoveValueModifiersFromHIVViralLoads()}
	 */
	@Test
	@Verifies(value = "should match only numeric OBX segments for HIV Viral Load with a modifier before the value", method = "RemoveValueModifiersFromHIVViralLoads()")
	public void RemoveValueModifiersFromHIVViralLoads_shouldMatchOnlyNumericOBXSegmentsForHIVViralLoadWithAModifierBeforeTheValue()
			throws Exception {
		// with modifier
		String hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||>123456789|||||||||20080206";
		Assert.assertEquals(true,
				new RemoveValueModifiersFromHIVViralLoads().matches(hl7string));

		// without modifier
		hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||123456789|||||||||20080206";
		Assert.assertEquals(false,
				new RemoveValueModifiersFromHIVViralLoads().matches(hl7string));
	}

	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#RemoveValueModifiersFromHIVViralLoads()}
	 */
	@Test
	@Verifies(value = "should match values with modifiers and values separated by spaces", method = "RemoveValueModifiersFromHIVViralLoads()")
	public void RemoveValueModifiersFromHIVViralLoads_shouldMatchValuesWithModifiersAndValuesSeparatedBySpaces()
			throws Exception {
		// with space
		String hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||< 40|||||||||20080206";
		Assert.assertEquals(true,
				new RemoveValueModifiersFromHIVViralLoads().matches(hl7string));

		// space but not a number
		hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||< abc|||||||||20080206";
		Assert.assertEquals(false,
				new RemoveValueModifiersFromHIVViralLoads().matches(hl7string));
	}

	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#RemoveValueModifiersFromHIVViralLoads()}
	 */
	@Test
	@Verifies(value = "should match strings with breaks in them", method = "RemoveValueModifiersFromHIVViralLoads()")
	public void RemoveValueModifiersFromHIVViralLoads_shouldMatchStringsWithBreaksInThem()
			throws Exception {
		String hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||>123456789|||||||||20080206"
				+ PcsLabInterfaceConstants.MESSAGE_EOL_SEQUENCE
				+ "NTE|||Hey now";
		Assert.assertEquals(true,
				new RemoveValueModifiersFromHIVViralLoads().matches(hl7string));
	}

	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#transform(String)}
	 */
	@Test
	@Verifies(value = "should decrease value by one if using less than modifier", method = "transform(String)")
	public void transform_shouldDecreaseValueByOneIfUsingLessThanModifier()
			throws Exception {
		String hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||<400|||||||||20080206";

		String expected = "OBX|1|NM|856^HIV Viral Load^99DCT||399|||||||||20080206"
				+ PcsLabInterfaceConstants.MESSAGE_EOL_SEQUENCE
				+ "NTE|||"
				+ PcsLabInterfaceConstants.LAB_VALUE_MODIFIED
				+ "<400";

		Assert.assertEquals(expected,
				new RemoveValueModifiersFromHIVViralLoads()
						.transform(hl7string));
	}

	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#transform(String)}
	 */
	@Test
	@Verifies(value = "should modify value if spaces exist between modifier and value", method = "transform(String)")
	public void transform_shouldModifyValueIfSpacesExistBetweenModifierAndValue()
			throws Exception {
		String hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||< 40|||||||||20080206";

		String expected = "OBX|1|NM|856^HIV Viral Load^99DCT||39|||||||||20080206"
				+ PcsLabInterfaceConstants.MESSAGE_EOL_SEQUENCE
				+ "NTE|||"
				+ PcsLabInterfaceConstants.LAB_VALUE_MODIFIED
				+ "< 40";

		Assert.assertEquals(expected,
				new RemoveValueModifiersFromHIVViralLoads()
						.transform(hl7string));
	}
	
	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#transform(String)}
	 */
	@Test
	@Verifies(value = "should increase value by one if using greater than modifier", method = "transform(String)")
	public void transform_shouldIncreaseValueByOneIfUsingGreaterThanModifier()
			throws Exception {
		String hl7string = "OBX|1|NM|856^HIV Viral Load^99DCT||>750000|||||||||20080206";

		String expected = "OBX|1|NM|856^HIV Viral Load^99DCT||750001|||||||||20080206"
				+ PcsLabInterfaceConstants.MESSAGE_EOL_SEQUENCE
				+ "NTE|||"
				+ PcsLabInterfaceConstants.LAB_VALUE_MODIFIED
				+ ">750000";

		Assert.assertEquals(expected,
				new RemoveValueModifiersFromHIVViralLoads()
						.transform(hl7string));
	}
	
	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#transform(String)}
	 */
	@Test
	@Verifies(value = "should leave text following modifier alone if not a digit", method = "transform(String)")
	public void transform_shouldLeaveTextFollowingModifierAloneIfNotADigit()
			throws Exception {
		String hl7string = "OBX|1|ST|856^HIV Viral Load^99DCT||INSUFFICIENT SAMPLE FOR ANALYSIS,PLEASE DRAW ADEQUATE SAMPLE.(>2MLS)|^Copies /mL|||||X|||201111221019|||||201201101130";

		Assert.assertEquals(hl7string,
				new RemoveValueModifiersFromHIVViralLoads()
						.transform(hl7string));
	}
	
	/**
	 * @see {@link RemoveValueModifiersFromHIVViralLoads#RemoveValueModifiersFromHIVViralLoads()}
	 */
	@Test
	@Verifies(value = "work for any OBX referencing 856 regardless of name", method = "RemoveValueModifiersFromHIVViralLoads()")
	public void RemoveValueModifiersFromHIVViralLoads_shouldWorkForAnyOBXReferencing856RegardlessOfName()
			throws Exception {
		String hl7string = "OBX|1|NM|856^Ack - foo Bar^99DCT||>750000|||||||||20080206";

		String expected = "OBX|1|NM|856^Ack - foo Bar^99DCT||750001|||||||||20080206"
				+ PcsLabInterfaceConstants.MESSAGE_EOL_SEQUENCE
				+ "NTE|||"
				+ PcsLabInterfaceConstants.LAB_VALUE_MODIFIED
				+ ">750000";

		Assert.assertEquals(expected,
				new RemoveValueModifiersFromHIVViralLoads().transform(hl7string));
	}
}