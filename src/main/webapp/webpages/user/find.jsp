<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="userFindBean" beanclass="cz.bbmri.action.user.FindUserActionBean"/>

<stripes:layout-render name="/layouts/layout_content.jsp"
                 primarymenu="user"
                 secondarymenu="user_find">

    <stripes:layout-component name="body">

        <fieldset>
            <stripes:form beanclass="${actionBean.name}" class="form-horizontal">

                <stripes:layout-render name="/webpages/component/form/userFindInput.jsp"/>

                <div class="form-actions">
                    <stripes:submit name="find" class="btn btn-primary btnMargin"/>
                </div>
            </stripes:form>
        </fieldset>

        </br>

        <fieldset>
            <core:if test="${empty actionBean.results}">
                <p><format:message key="cz.bbmri.action.FindActionBean.noResults"/></p>
            </core:if>
            <core:if test="${not empty actionBean.results}">
                <legend><format:message key="cz.bbmri.action.FindActionBean.results"/></legend>
                <table class="table table-hover table-striped">
                    <stripes:layout-render name="/webpages/component/detail/user/header.jsp"/>
                    <tbody>

                    <core:forEach var="user" items="${actionBean.results}">
                        <tr>
                            <stripes:layout-render name="/webpages/component/detail/user/row.jsp" record="${user}"/>
                            <td class="action">
                                <span class="pull-right">
                                <stripes:link beanclass="${actionBean.name}" event="detail" class="btn btn-primary">
                                    <stripes:param name="userId" value="${user.id}"/>
                                    <format:message key="detail"/>
                                </stripes:link>
                                    </span>
                            </td>
                        </tr>
                    </core:forEach>
                    </tbody>
                </table>
            </core:if>
        </fieldset>

    </stripes:layout-component>
</stripes:layout-render>