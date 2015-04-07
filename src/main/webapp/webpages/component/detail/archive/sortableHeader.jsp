<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <thead>
    <tr>
        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Archive.created"
                             column="created"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Archive.actor"
                             column="actor"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Archive.message"
                             column="message"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</stripes:layout-definition>