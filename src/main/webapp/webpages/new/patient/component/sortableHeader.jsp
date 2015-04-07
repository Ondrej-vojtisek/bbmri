<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <thead>
    <tr>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.institutionalId"
                             column="institutionalId"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.sex"
                             column="sex"
                             pagination="${pagination}"/>
        </th>
        <th>
            <%--Age is not a table column--%>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.age"
                             column="birthYear"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Patient.consent"
                             column="consent"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</stripes:layout-definition>