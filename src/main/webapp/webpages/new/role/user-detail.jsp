<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>


<s:layout-render name="${component.layout.content}"
                 primarymenu="user">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.user}" active="roles"/>

        <table class="table table-hover table-striped">

            <thead>
            <tr>
                <th><f:message key="cz.bbmri.entity.Role.name"/></th>
                <th><f:message key="cz.bbmri.entity.Role.description"/></th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="item" items="${actionBean.user.role}">
                <tr>
                    <td><b><f:message key="cz.bbmri.entity.Role.${item.name}"/></b></td>
                    <td><f:message key="cz.bbmri.entity.Role.${item.name}.description"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>