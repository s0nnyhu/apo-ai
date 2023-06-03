package org.shu.core;

import org.apache.log4j.PropertyConfigurator;

public class Log4j {
    public static void init() {
        PropertyConfigurator.configure("log4j.properties");
    }
}
