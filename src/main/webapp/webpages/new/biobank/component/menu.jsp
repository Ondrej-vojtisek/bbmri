<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:useActionBean var="biobankActionBean" beanclass="cz.bbmri.action.BiobankActionBean"/>

<s:layout-definition>
    <ul class="nav nav-tabs">
    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="detail">
        <li <c:if test="${active == 'detail'}"> class="active" </c:if> >
            <s:link beanclass="cz.bbmri.action.BiobankActionBean" event="detail">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entity.Biobank.basicInformation"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="biobankuser">
        <li <c:if test="${active == 'biobankuser'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.BiobankActionBean"
                    event="biobankuser">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entity.BiobankUser.biobankUsers"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="attachments">
        <li <c:if test="${active == 'attachments'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.BiobankActionBean"
                    event="attachments">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entity.Biobank.attachment"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="withdraws">
        <li <c:if test="${active == 'withdraws'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.BiobankActionBean"
                    event="withdraws">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entity.Withdraw.withdraws"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>

    <security:allowed bean="biobankActionBean" event="patients">
        <li <c:if test="${active == 'patients'}"> class="active" </c:if>>
            <s:link beanclass="cz.bbmri.action.BiobankActionBean"
                    event="patients">
                <s:param name="id" value="${actionBean.id}"/>
                <f:message key="cz.bbmri.entity.Patient.patients"/>
            </s:link>
        </li>
    </security:allowed>

    <%-- -------------------------------------------------------------------- --%>
    </ul>
</s:layout-definition>