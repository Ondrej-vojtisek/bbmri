<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table>

        <tr>
            <th><label for="z1"><stripes:label name="sample.sampleID"/></label></th>
            <td><stripes:text id="z1" name="sample.sampleID"/></td>
        </tr>
        <tr>
            <th><label for="z2"><stripes:label name="sample.numOfSamples"/></label></th>
            <td><stripes:text id="z2" name="sample.numOfSamples"/></td>
        </tr>
        <tr>
            <th><label for="z3"><stripes:label name="sample.numOfAvailable"/></label></th>
            <td><stripes:text id="z3" name="sample.numOfAvailable"/></td>
        </tr>
        <tr>
            <th><label for="z4"><stripes:label name="sample.tissueType"/></label></th>
            <td><stripes:text id="z4" name="sample.tissueType"/></td>
        </tr>
        <tr>
            <th><label for="z5"><stripes:label name="sample.TNM"/></label></th>
            <td><stripes:text id="z5" name="sample.TNM"/></td>
        </tr>
        <tr>
            <th><label for="z6"><stripes:label name="sample.pTNM"/></label></th>
            <td><stripes:text id="z6" name="sample.pTNM"/></td>
        </tr>
        <tr>
            <th><label for="z7"><stripes:label name="sample.grading"/></label></th>
            <td><stripes:text id="z7" name="sample.grading"/></td>
        </tr>
        <tr>
            <th><label for="z8"><stripes:label name="sample.diagnosis"/></label></th>
            <td><stripes:text id="z8" name="sample.diagnosis"/></td>
        </tr>
</table>
