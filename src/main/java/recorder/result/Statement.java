/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
/**
 * Result of a gesture.
 */
public interface Statement {
    boolean isEquivalentTo(Statement stmt);


    String toXml();
}
