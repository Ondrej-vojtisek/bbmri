<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Rack.name"
                             column="name"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Rack.capacity"
                             column="capacity"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>