<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <td>
        <s:layout-render name="/webpages/component/detail/date/date.jsp" date="${record.created}"/>
    </td>
    <td>${record.biobank.name}</td>
    <td><f:message key="cz.bbmri.entities.enumeration.RequestState.${record.requestState}"/></td>
    <td>${record.specification}</td>

</s:layout-definition>

