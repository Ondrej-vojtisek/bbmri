<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <thead>
    <tr>
        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Biobank.acronym"
                             column="acronym"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Biobank.name"
                             column="name"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Biobank.nameEnglish"
                             column="nameEnglish"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Biobank.institutionalId"
                             column="institutionalId"
                             pagination="${pagination}"/>
        </th>

        <th>
            <stripes:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.Biobank.description"
                             column="description"
                             pagination="${pagination}"/>
        </th>

    </tr>
    </thead>

</stripes:layout-definition>
