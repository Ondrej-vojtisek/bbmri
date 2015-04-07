<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-definition>

    <fieldset>
        <legend><format:message key="cz.bbmri.entities.infrastructure.Container.container"/></legend>
        <table class="table table-striped">
            <stripes:layout-render name="/webpages/component/detail/container/header.jsp"/>
            <tbody>
            <tr>
                <stripes:layout-render name="/webpages/component/detail/container/row.jsp" record="${record}"/>
            </tr>
            </tbody>
        </table>
    </fieldset>

</stripes:layout-definition>


