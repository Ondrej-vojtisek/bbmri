<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${record.fileName}</td>
    <td>${record.size}</td>
    <td><format:message key="cz.bbmri.entity.enumeration.BiobankAttachmentType.${record.biobankAttachmentType}"/></td>

</stripes:layout-definition>