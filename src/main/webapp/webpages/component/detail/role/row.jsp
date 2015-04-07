<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <td>${record.user.wholeName}</td>
    <td>
        <stripes:select name="permission" value="${record.permission}">
            <stripes:options-enumeration enum="cz.bbmri.entity.enumeration.Permission"/>
        </stripes:select>
    </td>

</stripes:layout-definition>