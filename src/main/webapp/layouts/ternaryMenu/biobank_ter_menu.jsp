<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankBean" beanclass="bbmri.action.biobank.BiobankActionBean"/>

<%------------------------------------------------------------------------%>

<security:allowed bean="biobankBean" event="edit">
    <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
    <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="edit">
        <s:param name="id" value="${biobankBean.id}"/><f:message
            key="bbmri.action.biobank.BiobankActionBean.basicData"/></s:link>
</security:allowed>
</li>

<security:notAllowed bean="biobankBean" event="edit">
    <security:allowed bean="biobankBean" event="detail">
        <li <c:if test="${ternarymenu == 'detail'}"> class="active" </c:if> >
            <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="detail">
                <s:param name="id" value="${biobankBean.id}"/><f:message
                    key="bbmri.action.biobank.BiobankActionBean.basicData"/></s:link>
        </li>
    </security:allowed>
</security:notAllowed>

<%------------------------------------------------------------------------%>

<security:allowed bean="biobankBean" event="editAdministrators">
    <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>>
        <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="editAdministrators">
            <s:param name="id" value="${biobankBean.id}"/><f:message
                key="bbmri.action.biobank.BiobankActionBean.administrators"/></s:link>
    </li>
</security:allowed>

<security:notAllowed bean="biobankBean" event="editAdministrators">
    <security:allowed bean="biobankBean" event="administrators">
        <li <c:if test="${ternarymenu == 'administrators'}"> class="active" </c:if>>
            <s:link beanclass="bbmri.action.biobank.BiobankActionBean" event="administrators">
                <s:param name="id" value="${biobankBean.id}"/><f:message
                    key="bbmri.action.biobank.BiobankActionBean.administrators"/></s:link>
        </li>
    </security:allowed>
</security:notAllowed>

<%------------------------------------------------------------------------%>

<%--Event from biobankBean is here only because security check--%>

<security:allowed bean="biobankBean" event="editAdministrators">
    <li <c:if test="${ternarymenu == 'addAdministrator'}"> class="active" </c:if>>
        <s:link beanclass="bbmri.action.biobank.FindBiobankAdminActionBean">
            <s:param name="id" value="${biobankBean.id}"/><f:message
                key="bbmri.action.biobank.BiobankActionBean.addAdministrator"/></s:link>
    </li>
</security:allowed>

<%------------------------------------------------------------------------%>