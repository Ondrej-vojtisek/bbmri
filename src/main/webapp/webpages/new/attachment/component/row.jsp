<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${item.fileName}</td>
    <td>${item.size}</td>
    <td><f:message key="cz.bbmri.entity.AttachmentType.${item.attachmentType.name}"/></td>

</s:layout-definition>