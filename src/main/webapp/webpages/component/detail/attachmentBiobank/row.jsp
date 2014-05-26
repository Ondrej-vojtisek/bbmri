<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.fileName}</td>
    <td>${record.size}</td>
    <td><f:message key="cz.bbmri.entities.enumeration.BiobankAttachmentType.${record.biobankAttachmentType}"/></td>

</s:layout-definition>