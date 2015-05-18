<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<format:message key="cz.bbmri.action.LoginActionBean.notAuthorized" var="title"/>
<stripes:layout-render name="${component.layout.errorOutside}"
                       title="${title}">

    <stripes:useActionBean var="ab" beanclass="cz.bbmri.action.base.ErrorActionBean"/>

    <stripes:layout-component name="body">
        <p><format:message key="cz.bbmri.action.LoginActionBean.unauthorized"/></p>

        <p><format:message key="cz.bbmri.action.LoginActionBean.rightToAccess"/></p>

        <ul>
            <core:forEach items="${ab.developers}" var="user">

                <li>${user.wholeName}, <b>email:</b>
                    <core:if test="${not empty user.email}">
                        ${user.email}
                    </core:if>
                </li>

            </core:forEach>
        </ul>

        <p><format:message key="cz.bbmri.action.LoginActionBean.rightToAccessAdmin"/></p>
        <ul>
            <core:forEach items="${ab.administrators}" var="user">

                <li>${user.wholeName}, <b>email:</b>
                    <core:if test="${not empty user.email}">
                        ${user.email}
                    </core:if>
                </li>

            </core:forEach>
        </ul>

    </stripes:layout-component>
</stripes:layout-render>