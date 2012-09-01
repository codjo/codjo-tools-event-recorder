/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
public class Attribute {
    private String name;
    private Object value;


    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }


    public Object getValue() {
        return value;
    }


    public String getName() {
        return name;
    }
}
