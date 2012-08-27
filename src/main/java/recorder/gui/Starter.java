/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Classe permettant de démarrer à la fois l'EventRecorder et l'application à écouter.
 * 
 * <p>
 * Usage :
 * </p>
 * <pre>
 *  java recorder.Starter &lt;Class> &lt;Methode> [&lt;arg>]");
 *         &lt;Class>   - Nom de la classe à tester (e.g. 'com.my.App')");
 *         &lt;Methode> - Méthode main de l'application (e.g. 'main')");
 *         [&lt;arg>]   - Les arguments de la méthode main de l'application (e.g. 'main')");
 * </pre>
 */
public final class Starter {
    private Starter() {}

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            displayUsage();
        }
        String appClassMain = args[0];
        String mainMethodName = args[1];

        log("Execute : new " + appClassMain + "()." + mainMethodName + "("
            + Arrays.asList(args).subList(2, args.length) + ");");

        Class clazz = Class.forName(appClassMain);
        Method mainMethod = clazz.getMethod(mainMethodName, new Class[] {String[].class});

        mainMethod.invoke(null, new Object[] {removeClassAndMain(args)});

        new RecorderLogic().display();
    }


    private static String[] removeClassAndMain(String[] args) {
        List result = new ArrayList(Arrays.asList(args));
        result.remove(0);
        result.remove(0);
        return (String[])result.toArray(new String[result.size()]);
    }


    private static void displayUsage() {
        log("java recorder.Starter <Class> <Methode> [<arg>]");
        log("    <Class>   - Nom de la classe à tester (e.g. 'com.my.App')");
        log("    <Methode> - Méthode main de l'application (e.g. 'main')");
        log(
            "    [<arg>]   - Les arguments de la méthode main de l'application (e.g. 'main')");
    }


    private static void log(String msg) {
        System.out.println(msg);
    }
}
