/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.result;
import java.util.Iterator;
/**
 * Représente un tag avec des attributs.
 */
public class DefaultStatement implements Statement {
    private String tagName;
    private AttributeList attributes;

    public DefaultStatement(String tagName, AttributeList attributes) {
        if (tagName == null || attributes == null) {
            throw new IllegalArgumentException();
        }
        this.tagName = tagName;
        this.attributes = attributes;
    }

    public String toXml() {
        return "<" + tagName + buildAttributes() + "/>";
    }


    public boolean isEquivalentTo(Statement stmt) {
        if (this == stmt) {
            return true;
        }
        if (!(stmt instanceof DefaultStatement)) {
            return false;
        }

        final DefaultStatement defaultStatement = (DefaultStatement)stmt;

        if (!attributes.equals(defaultStatement.attributes)) {
            return false;
        }
        if (!tagName.equals(defaultStatement.tagName)) {
            return false;
        }

        return true;
    }


    private String buildAttributes() {
        StringBuffer buffer = new StringBuffer();

        for (Iterator iter = attributes.iterator(); iter.hasNext();) {
            Attribute entry = (Attribute)iter.next();
            buffer.append(" ").append(entry.getName()).append("=\"")
                  .append(convertToXml(entry.getValue().toString())).append("\"");
        }

        return buffer.toString();
    }


    String convertToXml(String query) {
        StringBuffer sb = new StringBuffer(query);
        replace(sb, "&", "&amp;");
        replace(sb, "\"", "&qote;");
        replace(sb, "<", "&lt;");
        replace(sb, ">", "&gt;");
        replace(sb, "'", "&apos;");
        return sb.toString();
    }


    StringBuffer replace(StringBuffer sb, String oldStr, String newStr) {
        int startIndex = sb.toString().indexOf(oldStr);
        while (startIndex != -1) {
            sb.replace(startIndex, startIndex + oldStr.length(), newStr);
            startIndex = sb.toString().indexOf(oldStr, startIndex + 1);
        }
        return sb;
    }
}
