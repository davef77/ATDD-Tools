package com.cd.acceptance.dsl;

import java.util.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Copyright (c) Continuous Delivery Ltd. 2024
 */
public class Params
{
    private final DslContext context;
    private final List<String> args;

    public Params(DslContext context, String[] args)
    {
        this.context = context;
        this.args = new ArrayList<>(Arrays.asList(args));
    }

    public Params(DslContext context) {
        this(context, new String[0]);
    }

    public String optional(String name, String defaultValue)
    {
        String arg = getParamValue(name);
        if (arg != null) return arg;
        return defaultValue;
    }

    public String alias(String name)
    {
        String value = getParamValue(name);
        if (value == null) {
            fail("No '" + name + "' supplied for alias");
        }
        return context.alias(value);
    }

    public String alias(String name, String defaultValue)
    {
        String value = getParamValue(name);
        if (value == null) {
            args.add(name + ": " + defaultValue);
        }
        return alias(name);
    }

    public String decodeAlias(String alias) {
        return context.decodeAlias(alias);
    }

    public String optionalSequence(String name, int start) {
        return optional(name, context.sequenceNumberForName(name, start));
    }

    private String getParamValue(String name) {
        for (String arg : args)
        {
            int index = arg.indexOf(name + ": ");
            if (index != -1)
            {
                return arg.substring(index + name.length() + 2);
            }
        }
        return null;
    }

    public List<String> optionalList(String name, String[] items) {
        return null;
    }
    public static class DslContext {

        private static final Map<String, Integer> globalSequenceNumbers = new HashMap<>();
        private final Map<String, Integer> sequenceNumbers = new HashMap<>();
        private final Map<String, String> aliases = new HashMap<>();

        public String sequenceNumberForName(String name, int start) {
            return seqForName(name, start, sequenceNumbers);
        }

        public String alias(String name) {
            if (!aliases.containsKey(name)) {
                String sequenceNo = seqForName(name, 1, globalSequenceNumbers);
                aliases.put(name, name + sequenceNo);
            }
            return aliases.get(name);
        }

        private String seqForName(String name, int start, Map<String, Integer> sequenceNumbers) {
            int retVal = start;
            if (sequenceNumbers.containsKey(name)) {
                retVal = sequenceNumbers.get(name);
            }
            sequenceNumbers.put(name, retVal + 1);

            return String.valueOf(retVal);
        }

        public String decodeAlias(String name) {
            for (String key : aliases.keySet()) {
                if (aliases.get(key).equals(name)) {
                    return key;
                }
            }
            return "";
        }
    }
}
