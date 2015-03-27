<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.institutionalId"
                             column="institutionalId"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.sex"
                             column="sex"
                             pagination="${pagination}"/>
        </th>
        <th>
            <%--Age is not a table column--%>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.age"
                             column="birthYear"
                             pagination="${pagination}"/>
        </th>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.consent"
                             column="consent"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>