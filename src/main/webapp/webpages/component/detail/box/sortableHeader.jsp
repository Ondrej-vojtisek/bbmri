<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.infrastructure.Box.name"
                             column="name"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.infrastructure.Box.capacity"
                             column="capacity"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.infrastructure.Box.numberOfPositions"
                             column="numberOfPositions"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.infrastructure.Box.tempMin"
                             column="tempMin"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.infrastructure.Box.tempMax"
                             column="tempMax"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>