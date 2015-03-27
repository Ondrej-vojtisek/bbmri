<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.User.id"
                             column="id"
                             pagination="${pagination}"/>
        </th>
        <th>
            <%--whole name is not a table column--%>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.User.wholeName"
                             column="surname"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>