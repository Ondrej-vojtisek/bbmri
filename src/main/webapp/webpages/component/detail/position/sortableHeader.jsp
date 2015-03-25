<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>

        <%--Both row and column lead to matrix ordering so the column field is the same --%>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Position.row"
                             column="row"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Position.column"
                             column="row"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.infrastructure.Position.sequentialPosition"
                             column="sequentialPosition"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entity.sample.Sample.sampleId"
                             column="sample.sampleIdentification.sampleId"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>