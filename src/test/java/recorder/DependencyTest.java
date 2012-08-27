/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;
import junit.framework.TestCase;
/**
 * Test les dépendances de cette API en utilisant JDepend.
 */
public class DependencyTest extends TestCase {
    private JDepend jdepend;

    /**
     * Test les dépendances inter-package.
     *
     * @throws IOException
     */
    public void test_dependencyConstraint() throws IOException {
        // Analyse
        jdepend.analyze();

        // Contraintes
        asserDependencytConstraint("recorder",
            new String[] {
                "recorder.event", "recorder.result", "recorder.component",
                "recorder.gesture"
            });

        asserDependencytConstraint("recorder.event", new String[] {"recorder.component"});

        asserDependencytConstraint("recorder.gesture",
            new String[] {"recorder.event", "recorder.component", "recorder.result"});

        asserDependencytConstraint("recorder.result", new String[] {});

        asserDependencytConstraint("recorder.component", new String[] {});

        // Pas de cycle
        assertFalse("Les cycles ne sont pas autorisés", jdepend.containsCycles());
    }


    private void asserDependencytConstraint(String javaPackage, String[] dependeciesArray) {
        List expectedDepencies = Arrays.asList(dependeciesArray);
        JavaPackage jPackage = jdepend.getPackage(javaPackage);
        if (jPackage == null) {
            throw new NullPointerException("Package '" + javaPackage + "' n'existe pas !");
        }
        List actualList = dependencyToList(jPackage);

        StringBuffer error = new StringBuffer();

        List notActual = new ArrayList(expectedDepencies);
        notActual.removeAll(actualList);
        if (!notActual.isEmpty()) {
            error.append("\n\t\tNon présente " + notActual);
        }

        List notExpected = new ArrayList(actualList);
        notExpected.removeAll(expectedDepencies);
        if (!notExpected.isEmpty()) {
            error.append("\n\t\tSupplément " + notExpected);
        }

        if (error.length() != 0) {
            fail("[" + javaPackage + "] Dépendance en erreur !" + error);
        }
    }


    private List dependencyToList(JavaPackage aPackage) {
        List list = new ArrayList();

        Collection efferents = aPackage.getEfferents();
        for (Iterator iter = efferents.iterator(); iter.hasNext();) {
            JavaPackage depPackage = (JavaPackage)iter.next();
            list.add(depPackage.getName());
        }

        return list;
    }


    protected void setUp() throws IOException {
        jdepend = new JDepend();
        jdepend.addDirectory("target/classes");

        // Filtre les package java et log4j
        final PackageFilter filter = jdepend.getFilter();
        filter.addPackage("java.*");
        filter.addPackage("javax.*");
        filter.addPackage("com_cenqua_clover");
        filter.addPackage("org.apache.log4j");
        filter.addPackage("recorder.gui.*");
        jdepend.setFilter(filter);
    }
}
