<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.biobank.BiobankActionBean.allBiobanks" var="title"/>
<s:useActionBean var="infrastructureBean" beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp" title="${title}"
                 primarymenu="biobank"
                 ternarymenu="infrastructure">

    <s:layout-component name="body">


        <c:if test="${empty infrastructureBean.infrastructure }">
            Infrastructure null

            <s:form beanclass="cz.bbmri.action.infrastructure.InfrastructureActionBean">
                <s:submit name="initiateInfrastructure">
                    <s:param name="biobankId" value="${infrastructureBean.biobankId}"/>
                </s:submit>
            </s:form>

        </c:if>

        <c:if test="${empty infrastructureBean.infrastructure }">
            Infrastructure null or empty containers or empty boxes
        </c:if>




    </s:layout-component>
</s:layout-render>