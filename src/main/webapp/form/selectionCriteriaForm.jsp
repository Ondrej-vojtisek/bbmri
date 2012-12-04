<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:errors/>
<table>
    <tr>
        <th><label for="z3"><f:message key="samples.number_of_available_samples"/></label></th>
        <td><s:text id="z3" name="sample.numOfAvailable"/></td>
    </tr>
    <tr>
        <th><label for="z4"><f:message key="samples.tissue_type"/></label></th>
        <td><s:text id="z4" name="sample.tissueType"/></td>
    </tr>
    <tr>
        <th><label for="z5"><f:message key="samples.TNM"/></label></th>
        <td><s:text id="z5" name="sample.TNM"/></td>
    </tr>
    <tr>
        <th><label for="z6"><f:message key="samples.pTNM"/></label></th>
        <td><s:text id="z6" name="sample.pTNM"/></td>
    </tr>
    <tr>
        <th><label for="z7"><f:message key="samples.grading"/></label></th>
        <td><s:text id="z7" name="sample.grading"/></td>
    </tr>
    <tr>
        <th><label for="z8"><f:message key="samples.diagnosis"/></label></th>
        <td><s:text id="z8" name="sample.diagnosis"/></td>
    </tr>
</table>