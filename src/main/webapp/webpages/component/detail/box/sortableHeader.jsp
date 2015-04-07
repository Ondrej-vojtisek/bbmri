<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <thead>
    <tr>
        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Box.name"
                             column="name"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Box.capacity"
                             column="capacity"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/headerUnsortable.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Box.numberOfPositions"
                             column="numberOfPositions"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Box.tempMin"
                             column="tempMin"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Box.tempMax"
                             column="tempMax"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</stripes:layout-definition>