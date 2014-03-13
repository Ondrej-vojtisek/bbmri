<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.SampleQuestion.created"
                             column="created"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.SampleQuestion.biobank"
                             column="biobank.name"
                             pagination="${pagination}"/>
        </th>
        <th>
            <%--Age is not a table column--%>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.SampleQuestion.requestState"
                             column="requestState"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="/webpages/component/detail/sortableTable/header.jsp"
                             msgKey="cz.bbmri.entities.SampleQuestion.specification"
                             column="specification"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>