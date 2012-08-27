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

        assertTrue("Vide est equivalent à vide", listA.isEquivalentTo(listB));
        assertTrue("listA==listA", listA.isEquivalentTo(listA));

        listA.add(newStatement("tag1"));
        assertFalse("Pas le même nombre d'élément", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag1"));
        assertTrue("Liste équivalente", listA.isEquivalentTo(listB));

        listB.add(newStatement("tag2"));
        listA.add(newStatement("tag2other"));
        assertFalse("Pas les même éléments", listA.isEquivalentTo(listB));
    }


    public void test_isEquivalentTo_badcase() throws Exception {
        StatementList listA = new StatementList();
        assertFalse("Pas le même nombre d'élément",
            listA.isEquivalentTo(newStatement("tag1")));
    }


    private DefaultStatement newStatement(String tagName) {
        return new DefaultStatement(tagName, AttributeList.EMPTY_LIST);
    }
}
