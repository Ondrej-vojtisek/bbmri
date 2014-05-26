<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.systemAdministration.Archive.created"
                             column="created"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.systemAdministration.Archive.message"
                             column="message"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>