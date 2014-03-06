<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${attachment.fileName}</td>
    <td>${attachment.size}</td>
    <td><f:message key="AttachmentType.${attachment.attachmentType}"/></td>

</s:layout-definition>