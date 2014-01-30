<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="detail">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
        <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="detail">
            <s:param name="biobankId" value="${biobankBean.biobankId}"/><f:message
                key="cz.bbmri.action.biobank.BiobankActionBean.basicData"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="administratorsResolution">
    <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="administratorsResolution">
            <s:param name="biobankId" value="${biobankBean.biobankId}"/><f:message
                key="cz.bbmri.action.biobank.BiobankActionBean.administrators"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<%--Event from biobankBean is here only because security check--%>

<security:allowed bean="biobankBean" event="editAdministrators">
    <li <c:if test="${ternarymenu == 'addAdministrator'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.FindBiobankAdminActionBean">
            <s:param name="biobankId" value="${biobankBean.biobankId}"/><f:message
                key="cz.bbmri.action.biobank.BiobankActionBean.addAdministrator"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="samples">
    <li <c:if test="${ternarymenu == 'samples'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="samples">
            <s:param name="biobankId" value="${biobankBean.biobankId}"/><f:message
                key="cz.bbmri.action.biobank.BiobankActionBean.samples"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="patients">
    <li <c:if test="${ternarymenu == 'patients'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="patients">
            <s:param name="biobankId" value="${biobankBean.biobankId}"/><f:message
                key="cz.bbmri.action.biobank.BiobankActionBean.patients"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<security:allowed bean="biobankBean" event="sampleQuestions">
    <li <c:if test="${ternarymenu == 'sampleQuestions'}"> class="active" </c:if>>
        <s:link beanclass="cz.bbmri.action.biobank.BiobankActionBean" event="sampleQuestions">
            <s:param name="biobankId" value="${projectBean.id}"/>
            <f:message key="cz.bbmri.action.biobank.BiobankActionBean.sampleQuestions"/>
        </s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>
