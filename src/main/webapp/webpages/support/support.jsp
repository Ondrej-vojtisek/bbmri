<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<f:message key="cz.bbmri.action.support.SupportActionBean.title" var="title"/>

<s:useActionBean var="actionBean" beanclass="cz.bbmri.action.support.SupportActionBean"/>
<s:layout-render name="/layouts/layout_content.jsp"
                 title="${title}" primarymenu="support" secondarymenu="contacts">

    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="cz.bbmri.action.support.SupportActionBean.administrators"/></legend>
            <%--<f:message key="cz.bbmri.action.support.SupportActionBean.administratorsText"/>--%>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><f:message key="cz.bbmri.entities.User.wholeName"/></th>
                    <th><f:message key="cz.bbmri.entities.User.email"/></th>
                </tr>
                </thead>
                <tbody>

                <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${actionBean.allAdministrators}"/>

                <c:forEach items="${actionBean.allAdministrators}" var="user">
                    <tr>
                        <td>${user.wholeName}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>

        <fieldset>
            <legend><f:message key="cz.bbmri.action.support.SupportActionBean.developers"/></legend>
                <%--<f:message key="cz.bbmri.action.support.SupportActionBean.developersText">--%>

            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><f:message key="cz.bbmri.entities.User.wholeName"/></th>
                    <th><f:message key="cz.bbmri.entities.User.email"/></th>
                </tr>
                </thead>
                <tbody>

                <s:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${actionBean.allDevelopers}"/>

                <c:forEach items="${actionBean.allDevelopers}" var="user">
                    <tr>
                        <td>${user.wholeName}</td>
                        <td>${user.email}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </fieldset>

    </s:layout-component>

</s:layout-render>

