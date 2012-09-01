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
package recorder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import jdepend.framework.PackageFilter;
import junit.framework.TestCase;
/**
 * Test les d»pendances de cette API en utilisant JDepend.
 */
public class DependencyTest extends TestCase {
    private JDepend jdepend;


    /**
     * Test les d»pendances inter-package.
     */
    public void test_dependencyConstraint() throws IOException {
        // Analyse
        jdepend.analyze();

        // Contraintes
        asserDependencytConstraint("recorder",
                                   new String[]{
                                         "recorder.event", "recorder.result", "recorder.component",
                                         "recorder.gesture"
                                   });

        asserDependencytConstraint("recorder.event", new String[]{"recorder.component"});

        asserDependencytConstraint("recorder.gesture",
                                   new String[]{"recorder.event", "recorder.component", "recorder.result"});

        asserDependencytConstraint("recorder.result", new String[]{});

        asserDependencytConstraint("recorder.component", new String[]{});

        // Pas de cycle
        assertFalse("Les cycles ne sont pas autoris»s", jdepend.containsCycles());
    }


    private void asserDependencytConstraint(String javaPackage, String[] dependeciesArray) {
        List<String> expectedDepencies = Arrays.asList(dependeciesArray);
        JavaPackage jPackage = jdepend.getPackage(javaPackage);
        if (jPackage == null) {
            throw new NullPointerException("Package '" + javaPackage + "' n'existe pas !");
        }
        List<String> actualList = dependencyToList(jPackage);

        StringBuffer error = new StringBuffer();

        List<String> notActual = new ArrayList<String>(expectedDepencies);
        notActual.removeAll(actualList);
        if (!notActual.isEmpty()) {
            error.append("\n\t\tNon pr»sente ");
            error.append(notActual);
        }

        List<String> notExpected = new ArrayList<String>(actualList);
        notExpected.removeAll(expectedDepencies);
        if (!notExpected.isEmpty()) {
            error.append("\n\t\tSuppl»ment ");
            error.append(notExpected);
        }

        if (error.length() != 0) {
            fail("[" + javaPackage + "] D»pendance en erreur !" + error);
        }
    }


    private List<String> dependencyToList(JavaPackage aPackage) {
        List<String> list = new ArrayList<String>();

        Collection efferents = aPackage.getEfferents();
        for (Object efferent : efferents) {
            JavaPackage depPackage = (JavaPackage)efferent;
            list.add(depPackage.getName());
        }

        return list;
    }


    @Override
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
