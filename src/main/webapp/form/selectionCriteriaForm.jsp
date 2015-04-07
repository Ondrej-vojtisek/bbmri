<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table>
    <tr>
        <th><label for="z1"><stripes:label name="samples.tissue_type"/></label></th>
        <td><stripes:text id="z1" name="sample.tissueType"/></td>
    </tr>
    <tr>
        <th><label for="z2"><stripes:label name="samples.grading"/></label></th>
        <td><stripes:text id="z2" name="sample.grading"/></td>
    </tr>
    <tr>
        <th><label for="z3"><stripes:label name="samples.diagnosis"/></label></th>
        <td><stripes:text id="z3" name="sample.diagnosis"/></td>
    </tr>
</table>