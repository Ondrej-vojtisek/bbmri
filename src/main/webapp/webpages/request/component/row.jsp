<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:useActionBean var="sampleActionBean" beanclass="cz.bbmri.action.SampleActionBean"/>

<stripes:layout-definition>

    <td>${item.id}</td>
    <td>${item.number}</td>
    <td>
            <stripes:link beanclass="cz.bbmri.action.SampleActionBean" event="detail">
                <stripes:param name="id" value="${item.sample.id}"/>
               ${item.sample.institutionalId}
            </stripes:link>
    </td>

</stripes:layout-definition>

