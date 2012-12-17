<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:errors/>
<table>
    <tr>
        <th><label for="z1"><s:label name="sample.ID"/></label></th>
        <td><s:text id="z1" name="sample.sampleID"/></td>
    </tr>
    <tr>
        <th><label for="z2"><s:label name="sample.numOfSamples"/></label></th>
        <td><s:text id="z2" name="sample.numOfSamples"/></td>
    </tr>
    <tr>
        <th><label for="z3"><s:label name="sample.numOfAvailable"/></label></th>
        <td><s:text id="z3" name="sample.numOfAvailable"/></td>
    </tr>
    <tr>
        <th><label for="z4"><s:label name="sample.tissueType"/></label></th>
        <td><s:text id="z4" name="sample.tissueType"/></td>
    </tr>
    <tr>
        <th><label for="z5"><s:label name="sample.TNM"/></label></th>
        <td><s:text id="z5" name="sample.TNM"/></td>
    </tr>
    <tr>
        <th><label for="z6"><s:label name="sample.pTNM"/></label></th>
        <td><s:text id="z6" name="sample.pTNM"/></td>
    </tr>
    <tr>
        <th><label for="z7"><s:label name="sample.grading"/></label></th>
        <td><s:text id="z7" name="sample.grading"/></td>
    </tr>
    <tr>
        <th><label for="z8"><s:label name="sample.diagnosis"/></label></th>
        <td><s:text id="z8" name="sample.diagnosis"/></td>
    </tr>
</table>