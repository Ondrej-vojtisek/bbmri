<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Biobank.acronym"
                             column="acronym"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Biobank.name"
                             column="name"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Biobank.institutionalId"
                             column="institutionalId"
                             pagination="${pagination}"/>
        </th>

        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Biobank.description"
                             column="description"
                             pagination="${pagination}"/>
        </th>

    </tr>
    </thead>

</s:layout-definition>
