<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:errors/>
<table>
    <tr>
        <th><label for="z1"><s:label name="samples.tissue_type"/></label></th>
        <td><s:text id="z1" name="sample.tissueType"/></td>
    </tr>
    <tr>
        <th><label for="z2"><s:label name="samples.grading"/></label></th>
        <td><s:text id="z2" name="sample.grading"/></td>
    </tr>
    <tr>
        <th><label for="z3"><s:label name="samples.diagnosis"/></label></th>
        <td><s:text id="z3" name="sample.diagnosis"/></td>
    </tr>
</table>