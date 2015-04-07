<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <thead>
    <tr>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.User.id"
                             column="id"
                             pagination="${pagination}"/>
        </th>
        <th>
            <%--whole name is not a table column--%>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.User.wholeName"
                             column="surname"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</stripes:layout-definition>