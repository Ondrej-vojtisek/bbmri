<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankBean" beanclass="cz.bbmri.action.biobank.BiobankActionBean"/>

<%-- -------------------------------------------------------------------- --%>


<security:allowed bean="biobankBean" event="allBiobanks">
    <li
    <c:if test="${secondarymenu == 'biobank_all'}"> class="active" </c:if> ><s:link
        beanclass="${biobankBean.name}" event="allBiobanks"><f:message
        key="biobanks_all"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

<%--There can't be used createActionBean because it is set as a wizard. Don't know why.--%>
<security:allowed bean="biobankBean" event="createBiobank">
    <li
    <c:if test="${secondarymenu == 'biobank_create'}"> class="active" </c:if> ><s:link
                beanclass="cz.bbmri.action.biobank.CreateActionBean"
                event="display"><f:message key="biobank_create"/></s:link>
    </li>
</security:allowed>

<%-- -------------------------------------------------------------------- --%>

