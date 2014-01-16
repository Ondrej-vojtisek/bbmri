<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<f:message key="cz.bbmri.action.LoginActionBean.notAuthorized" var="title"/>
<s:layout-render name="/layouts/layout_error_outside.jsp"
                 title="${title}">

    <s:useActionBean var="ab" beanclass="cz.bbmri.action.ErrorActionBean"/>

    <s:layout-component name="body">
        <p><f:message key="cz.bbmri.action.LoginActionBean.unauthorized"/></p>
        <p><f:message key="cz.bbmri.action.LoginActionBean.rightToAccess"/></p>
        <ul>
        <c:forEach items="${ab.developers}" var="user">

                <li>${user.wholeName}, email: ${user.email}</li>

        </c:forEach>
        </ul>

    </s:layout-component>
</s:layout-render>