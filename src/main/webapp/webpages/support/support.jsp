<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="/layouts/layout_content.jsp"
                primarymenu="support"
                secondarymenu="contacts">

    <stripes:layout-component name="body">
        <fieldset>
            <legend><format:message key="cz.bbmri.action.support.SupportActionBean.administrators"/></legend>
            <%--<format:message key="cz.bbmri.action.support.SupportActionBean.administratorsText"/>--%>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><format:message key="cz.bbmri.entities.User.wholeName"/></th>
                    <th><format:message key="cz.bbmri.entities.User.email"/></th>
                </tr>
                </thead>
                <tbody>

                <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${actionBean.allAdministrators}"/>

                <core:forEach items="${actionBean.allAdministrators}" var="user">
                    <tr>
                        <td>${user.wholeName}</td>
                        <td>${user.email}</td>
                    </tr>
                </core:forEach>
                </tbody>
            </table>

        </fieldset>

        <fieldset>
            <legend><format:message key="cz.bbmri.action.support.SupportActionBean.developers"/></legend>
                <%--<format:message key="cz.bbmri.action.support.SupportActionBean.developersText">--%>

            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><format:message key="cz.bbmri.entities.User.wholeName"/></th>
                    <th><format:message key="cz.bbmri.entities.User.email"/></th>
                </tr>
                </thead>
                <tbody>

                <stripes:layout-render name="/webpages/component/detail/empty/emptyTable.jsp"
                                           collection="${actionBean.allDevelopers}"/>

                <core:forEach items="${actionBean.allDevelopers}" var="user">
                    <tr>
                        <td>${user.wholeName}</td>
                        <td>${user.email}</td>
                    </tr>
                </core:forEach>
                </tbody>
            </table>

        </fieldset>

    </stripes:layout-component>

</stripes:layout-render>

