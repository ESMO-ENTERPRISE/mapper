package com.esmo.mapper.example.ex15;

import com.esmo.mapper.common.utils.MapperUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

public class FieldIgnoresMapperTest {

	FieldIgnoresMapper mapper = MapperUtil.getMapper(FieldIgnoresMapper.class);



	@Test
	public void testIgnoreA() {
		// 'a' is not ignored in every methods
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getA, false);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getA, false);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getA, false, false);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getA, false, false);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getA, false, false, false);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getA, false, false, false);
	}

	@Test
	public void testIgnoreB() {
		// 'b' is ignored in every methods
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getB, true);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getB, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getB, true, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getB, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getB, true, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getB, true, true, true);
	}

	@Test
	public void testIgnoreC() {
		// @FieldIgnore(types=IData.class, value = "c") =>
		// 'c' is ignored on class implemented IData (Data1, Data2)
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getC, true);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getC, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getC, true, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getC, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getC, false, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getC, false, true, true);
	}

	@Test
	public void testIgnoreD() {
		// @FieldIgnore(types={Data2.class,Data3.class}, value = "d")
		// 'd' is ignored on class Data2 and Data3
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getD, false);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getD, false);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getD, true, false);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getD, true, false);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getD, true, false, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getD, true, false, true);
	}
	@Test
	public void testIgnoreE() {
		// @FieldIgnore("e") only on method clone?b =>
		// 'e' is ignored only for clone*b
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getE, false);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getE, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getE, false, false);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getE, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getE, false, false, false);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getE, true, true, true);
	}
	@Test
	public void testIgnoreF() {
		// @FieldIgnore("f") on Mapper +
		// @FieldIgnore(types = Data1.class, value="f",ignored = false) on method clone?b =>
		// 'f' is ignored on every place, but not for clone1b, because first rule is rewritten rule on this method with Data1.class
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getF, true);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getF, false);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getF, true, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getF, true, false);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getF, true, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getF, true, false, true);
	}
	@Test
	public void testIgnoreG() {
		// @FieldIgnore(types=IData.class, value = "g") on Mapper +
		// @FieldIgnore(types = Data3.class, value="g",ignored = false) on method clone?b
		// => 'g' is ignored on IData (Data1,Data2) on every place
		//        included int method clone1b, because second rule on method is not used (because types is not used in this methods )
		assertTestIgnore(AbstractData.createData1(), mapper::clone1, AbstractData::getG, true);
		assertTestIgnore(AbstractData.createData1(), mapper::clone1b, AbstractData::getG, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2, AbstractData::getG, true, true);
		assertTestIgnore(AbstractData.createData2(), mapper::clone2b, AbstractData::getG, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3, AbstractData::getG, false, true, true);
		assertTestIgnore(AbstractData.createData3(), mapper::clone3b, AbstractData::getG, false, true, true);
	}

	<T extends AbstractData> void assertTestIgnore(T i, Function<T, T> clone, Function<AbstractData, String> getter, boolean expectIgnoredAll) {
		T o = clone.apply(i);
		_assertTestIgnore(i, o, getter, expectIgnoredAll);
	}
	<T extends AbstractData> void _assertTestIgnore(T i, T o, Function<AbstractData, String> getter, boolean expectIgnoredAll) {
		Assertions.assertNotNull(i);
		String valIn = getter.apply(i);
		Assertions.assertNotNull(valIn);

		Assertions.assertNotNull(o);
		String valOut = getter.apply(o);

		if (expectIgnoredAll) {
			Assertions.assertNull(valOut);
		} else {
			Assertions.assertNotNull(o);
			Assertions.assertEquals(valIn, valOut);
		}
	}
	<T extends AbstractData> void assertTestIgnore(T i, Function<T, T> clone, Function<AbstractData, String> getter, boolean expectIgnoredAll, Boolean expectedIgnoredInFieldD1) {
		assertTestIgnore(i, clone, getter, expectIgnoredAll, expectedIgnoredInFieldD1, null);
	}
	<T extends AbstractData> void assertTestIgnore(T i, Function<T, T> clone, Function<AbstractData, String> getter, boolean expectIgnoredAll, Boolean expectedIgnoredInFieldD1, Boolean expectedIgnoredInFieldD2) {
		T o = clone.apply(i);
		_assertTestIgnore(i, o, getter, expectIgnoredAll);

		if (expectedIgnoredInFieldD1!=null) {
			if (i instanceof Data2) {
				_assertTestIgnore(((Data2) i).getD1(), ((Data2) o).getD1(), getter, expectedIgnoredInFieldD1);
			}
			if (i instanceof Data3) {
				_assertTestIgnore(((Data3) i).getD2().getD1(), ((Data3) o).getD2().getD1(), getter, expectedIgnoredInFieldD1);
			}
		}
		if (expectedIgnoredInFieldD2!=null) {
			if (i instanceof Data3) {
				_assertTestIgnore(((Data3) i).getD2(), ((Data3) o).getD2(), getter, expectedIgnoredInFieldD2);
			}
		}
	}
}
