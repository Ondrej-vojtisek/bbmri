<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.Project.name" column="name"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.Project.fundingOrganization" column="fundingOrganization"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.Project.projectState" column="projectState"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>