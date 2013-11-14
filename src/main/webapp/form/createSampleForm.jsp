<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table>
    <tr>
        <th><label for="z1"><s:label name="sample.sampleID"/></label></th>
        <td><s:text id="z1" name="sample.sampleID"/></td>
        <td>Identifikátor - 13 znaků, 3 zn. biobanka, 4 znaky rok, 6zn poradove cislo - </td>
    </tr>
    <tr>
        <th><label for="z2"><s:label name="sample.numOfSamples"/></label></th>
        <td><s:text id="z2" name="sample.numOfSamples"/></td>
        <td>Celkový počet dostupných vzorků</td>
    </tr>
    <tr>
        <th><label for="z3"><s:label name="sample.numOfAvailable"/></label></th>
        <td><s:text id="z3" name="sample.numOfAvailable"/></td>
        <td>Počet dostupných vzorků</td>
    </tr>
    <tr>
        <th><label for="z4"><s:label name="sample.tissueType"/></label></th>
        <td><s:text id="z4" name="sample.tissueType"/></td>
        <td>Atribut vychází z číselníku MOU - 1(nádor), 2 (metastáze), 3(benigní tkáň), 4(Zdravá tkáň), SD(sérový modul)</td>
    </tr>
    <tr>
        <th><label for="z5"><s:label name="sample.TNM"/></label></th>
        <td><s:text id="z5" name="sample.TNM"/></td>
        <td>Klasifikace používající 7 znaků</td>
    </tr>
    <tr>
        <th><label for="z6"><s:label name="sample.pTNM"/></label></th>
        <td><s:text id="z6" name="sample.pTNM"/></td>
        <td>Klasifikace používající 7 znaků</td>
    </tr>
    <tr>
        <th><label for="z7"><s:label name="sample.grading"/></label></th>
        <td><s:text id="z7" name="sample.grading"/></td>
        <td>Číslo 1-9</td>
    </tr>
    <tr>
        <th><label for="z8"><s:label name="sample.diagnosis"/></label></th>
        <td><s:text id="z8" name="sample.diagnosis"/></td>
        <td>Podle klasifiakce MKN-10 - např. "N26"</td>
    </tr>
</table>