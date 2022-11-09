package com.refinement.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StringValidatorTest {

    @Test
    public void codesAreNotNullAndEmptyShouldPass() {
        //given
        String code1 = "";
        String code2 = "";
        //when
        boolean result = StringValidator.codesAreNotNullAndEmpty(code1, code2);
        //then
        assertEquals(true, result);
    }

    @Test
    public void codesAreNotNullAndEmptyShouldValidOneCode() {
        //given
        String code1 = null;
        String code2 = "";
        //when
        boolean result = StringValidator.codesAreNotNullAndEmpty(code1, code2);
        //then
        assertEquals(false, result);
    }

    @Test
    public void codesAreNotNullStringAndEmptyShouldValid() {
        //given
        String code1 = "null";
        String code2 = "";
        //when
        boolean result = StringValidator.codesAreNotNullAndEmpty(code1, code2);
        //then
        assertEquals(false, result);
    }

    @Test
    public void codesAreNotNullAndEmptyShouldValidCodes() {
        //given
        String code1 = "code1";
        String code2 = "code2";
        //when
        boolean result = StringValidator.codesAreNotNullAndEmpty(code1, code2);
        //then
        assertEquals(false, result);
    }

    @Test
    public void codesAreNullStringShouldNotValidCodes() {
        //given
        String code1 = "null";
        String code2 = "null";
        //when
        boolean result = StringValidator.codesAreNotNullAndEmpty(code1, code2);
        //then
        assertEquals(true, result);
    }
}
