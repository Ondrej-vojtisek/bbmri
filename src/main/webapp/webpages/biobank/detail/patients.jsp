<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.detail" var="title"/>
<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="patients">

    <s:layout-component name="body">

        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th><f:message key="cz.bbmri.entities.Patient.institutionId"/></th>
                <th><f:message key="cz.bbmri.entities.Patient.sex"/></th>
                <th><f:message key="cz.bbmri.entities.Patient.age"/></th>
                <th><f:message key="cz.bbmri.entities.Patient.consent"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <c:if test="${empty biobankBean.patients}">
                <tr>
                    <td colspan="5"><f:message key="empty"/></td>
                </tr>
            </c:if>
            <c:forEach items="${biobankBean.patients}" var="patient">
                <tr>
                    <td>${patient.institutionId}</td>
                    <td>${patient.sex}</td>
                    <td>${patient.age}</td>
                    <td>${patient.consent}</td>
                    <td> AKCE ? </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>
