/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import junit.framework.TestCase;
/**
 * Classe de test de {@link StatementList}.
 */
public class StatementListTest extends TestCase {
    public void test_isEquivalentTo() throws Exception {
        StatementList listA = new StatementList();
        StatementList listB = new StatementList();

        assertTrue("Vide est equivalent � vide", listA.isEquivalentTo(listB));
        assertTrue("listA==listA", listA.isEquivalentTo(listA));

        listA.add(newStatement("tag1"));
        assertFalse("Pas le m�me nombre d'�l�ment", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag1"));
        assertTrue("Liste �quivalente", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag2"));
        listA.add(newStatement("tag2other"));
        assertFalse("Pas les m�me �l�ments", listA.isEquivalentTo(listB));
    }


    public void test_isEquivalentTo_badcase() throws Exception {
        StatementList listA = new StatementList();
        assertFalse("Pas le m�me nombre d'�l�ment",
            listA.isEquivalentTo(newStatement("tag1")));
    }


    private DefaultStatement newStatement(String tagName) {
        return new DefaultStatement(tagName, AttributeList.EMPTY_LIST);
    }
}
