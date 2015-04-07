<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <thead>
    <tr>

        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Sample.institutionalId"
                             column="institutionalId"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Sample.takingDate"
                             column="takingDate"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.MaterialType.materialType"
                             column="materialType"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Quantity.quantity"
                             column="quantity"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Diagnosis.diagnosis"
                             column="diagnosis"
                             pagination="${pagination}"/>
        </th>
        <th>
            <stripes:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Biopticalreport.biopticalReport"
                             column="biopticalReport"
                             pagination="${pagination}"/>
        </th>
    </tr>
    </thead>

</stripes:layout-definition>