<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td><s:layout-render name="${component.date}" date="${item.created}"/></td>
    <td><f:message key="cz.bbmri.entity.QuestionState.${item.questionState}"/></td>
    <td>${item.project.name}</td>
    <td>${item.specification}</td>

</s:layout-definition>

