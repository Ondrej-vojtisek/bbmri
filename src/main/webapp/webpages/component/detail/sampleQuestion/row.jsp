<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>
    <td>
        <stripes:layout-render name="/webpages/component/detail/date/date.jsp" date="${record.created}"/>
    </td>
    <td>${record.biobank.name}</td>
    <td><format:message key="cz.bbmri.entity.enumeration.RequestState.${record.requestState}"/></td>
    <td>${record.specification}</td>

</stripes:layout-definition>

