/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
/**
 * R�sultat d'une gesture.
 */
public interface Statement {
    boolean isEquivalentTo(Statement stmt);


    String toXml();
}
