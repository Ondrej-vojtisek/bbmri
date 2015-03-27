<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <thead>
    <tr>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Attachment.name"
                             column="fileName"
                             pagination="${actionBean.attachmentPagination}"/>
        </th>
        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Attachment.unit"
                             column="size"
                             pagination="${actionBean.attachmentPagination}"/>
        </th>

        <th>
            <s:layout-render name="${component.table.headerLayout}"
                             msgKey="cz.bbmri.entity.Attachment.importance"
                             column="attachmentType"
                             pagination="${actionBean.attachmentPagination}"/>
        </th>
    </tr>
    </thead>

</s:layout-definition>