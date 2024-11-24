package com.cd.acceptance.dsl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

public class ParamsTest {
    private Params.DslContext context;

    @BeforeEach
    public void setUp(){
        context = new Params.DslContext();
    }

    @Test
    public void shouldReturnOptionalValueOfParam() {
        Params params = new Params(context);

        assertEquals("3", params.optional("Three", "3"));
    }

    @Test
    public void shouldReturnDefinedValueOverridingDefault() {
        Params params = new Params(context, new String[]{"One: 1"});

        assertEquals("1", params.optional("One", "3"));
    }

    @Test
    public void shouldReturnStartValueOfOptionalSequence() {
        Params params = new Params(context);

        assertEquals("3", params.optionalSequence("Param1", 3));
    }

    @Test
    public void shouldReturnNextValueOfOptionalSequence() {
        Params params = new Params(context);

        params.optionalSequence("SomeParam", 5);
        assertEquals("6", params.optionalSequence("SomeParam", 5));
    }

    @Test
    public void shouldReturnValueOverridingOptionalSequenceValue() {
        Params params = new Params(context, new String[]{"SomeParam: 1"});

        assertEquals("1", params.optionalSequence("SomeParam", 3));
    }

    @Test
    public void shouldAliasNameWithDifferentValue() {
        Params params = new Params(context, new String[]{"name: nameTest"});
        String aliasedName = params.alias("name");

        assertNotEquals(aliasedName, "name");
    }

    @Test
    public void shouldAliasNamesWithUniqueValue() {
        Params params = new Params(context, new String[]{"name1: nameTest", "name2: nameTest2"});
        String aliasedName1 = params.alias("name1");
        String aliasedName2 = params.alias("name2");

        assertNotEquals(aliasedName1, aliasedName2);
    }

    @Test
    public void shouldReturnOriginalValueGivenAlias() {
        Params params = new Params(context);

        String alias = params.alias("name", "someName");

        assertEquals("someName", params.decodeAlias(alias));
    }

    @Test
    public void shouldReturnEmptyStringWhenDecodingAndAliasNotFound() {
        Params params = new Params(context);

        assertEquals("", params.decodeAlias("UnknonwnAlias"));
    }
    @Test()
    public void shouldFailAliasIfValueNotPresent() {
        Params params = new Params(context, new String[]{"name: nameTest"});
        assertThrows(AssertionFailedError.class, () -> params.alias("name2"));
    }

    @Test
    public void shouldSupplyConsistentAliasWithinContext() {
        Params params = new Params(context, new String[]{"name: nameTest"});
        String aliasedName = params.alias("name");

        assertEquals(aliasedName, params.alias("name"));
    }

    @Test
    public void shouldSupplyIncrementedAliasAcrossContexts() {
        Params params = new Params(context, new String[]{"name: nameTest"});
        Params.DslContext otherContext = new Params.DslContext();
        Params otherParams = new Params(otherContext, new String[]{"name: nameTest"});

        assertNotEquals(params.alias("name"), otherParams.alias("name"));
    }

    @Test
    public void shouldUseSuppliedNameAsRootOfAlias() {
        Params params = new Params(context, new String[]{"name: nameTest"});
        assertTrue(params.alias("name").startsWith("nameTest"));
    }

    @Test
    public void shouldUseDefaultValueAsRootOfAlias() {
        Params params = new Params(context, new String[0]);
        assertTrue(params.alias("name", "defaultValue").startsWith("defaultValue"));
    }
}
