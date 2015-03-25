<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Container.name"
                             column="name"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Container.location"
                             column="location"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Container.capacity"
                             column="capacity"
                             pagination="${pagination}"/>
        </th>
        <th>
            <%--TODO - solve sort later--%>
            <s:layout-render name="/webpages/component/detail/sortableTable/headerUnsortable.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Container.numberOfRacks"
                             column="numberOfRacks"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Container.tempMin"
                             column="tempMin"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Container.tempMax"
                             column="tempMax"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>