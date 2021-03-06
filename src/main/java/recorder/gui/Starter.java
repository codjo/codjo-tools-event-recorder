/*
 * codjo (Prototype)
 * =================
 *
 *    Copyright (C) 2005, 2012 by codjo.net
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */
package recorder.gui;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Main class used to start the EventRecorder with the targeted application.
 *
 * <p> Usage : </p>
 * <pre>
 *  java recorder.Starter &lt;Class> &lt;main-method> [&lt;arg>]");
 *         &lt;Class>       - Application class name (e.g. 'com.my.App')");
 *         &lt;main-method> - Main method of the application (e.g. 'main')");
 *         [&lt;arg>]       - Application arguments (e.g. 'user server-host')");
 * </pre>
 */
public final class Starter {
    private Starter() {
    }


    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            displayUsage();
        }
        String appClassMain = args[0];
        String mainMethodName = args[1];

        log("Execute : new " + appClassMain + "()." + mainMethodName + "("
            + Arrays.asList(args).subList(2, args.length) + ");");

        Class clazz = Class.forName(appClassMain);
        Method mainMethod = clazz.getMethod(mainMethodName, new Class[]{String[].class});

        mainMethod.invoke(null, new Object[]{removeClassAndMain(args)});

        new RecorderLogic().display();
    }


    private static String[] removeClassAndMain(String[] args) {
        List<String> result = new ArrayList<String>(Arrays.asList(args));
        result.remove(0);
        result.remove(0);
        return result.toArray(new String[result.size()]);
    }


    private static void displayUsage() {
        log("java recorder.Starter <Class> <main-method> [<arg>]");
        log("    <Class>       - Application class name  (e.g. 'com.my.App')");
        log("    <main-method> - Main method of the application (e.g. 'main')");
        log("    [<arg>]   - Application arguments (e.g. 'user server-host')");
    }


    private static void log(String msg) {
        //noinspection UseOfSystemOutOrSystemErr
        System.out.println(msg);
    }
}
