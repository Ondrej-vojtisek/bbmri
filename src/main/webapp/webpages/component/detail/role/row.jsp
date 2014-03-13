<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <td>${record.user.wholeName}</td>
    <td>
        <s:select name="permission" value="${record.permission}">
            <s:options-enumeration enum="cz.bbmri.entities.enumeration.Permission"/>
        </s:select>
    </td>

</s:layout-definition>