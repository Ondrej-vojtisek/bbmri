<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<stripes:layout-render name="${component.layout.content}"
                 primarymenu="user">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.user}" active="roles"/>

        <table class="table table-hover table-striped">

            <thead>
            <tr>
                <th><format:message key="cz.bbmri.entity.Role.name"/></th>
                <th><format:message key="cz.bbmri.entity.Role.description"/></th>
            </tr>
            </thead>

            <tbody>

            <core:forEach var="item" items="${actionBean.user.role}">
                <tr>
                    <td><b><format:message key="cz.bbmri.entity.Role.${item.name}"/></b></td>
                    <td><format:message key="cz.bbmri.entity.Role.${item.name}.description"/></td>
                </tr>
            </core:forEach>
            </tbody>
        </table>

    </stripes:layout-component>
</stripes:layout-render>