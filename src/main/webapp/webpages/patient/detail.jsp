<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<stripes:layout-render name="${component.layout.content}">

    <stripes:layout-component name="body">

        <stripes:layout-render name="${component.menu.patient}" active="detail"/>

        <table class="table">
            <stripes:layout-render name="${component.header.patient}"/>

            <tbody>
                <tr>
                    <stripes:layout-render name="${component.row.patient}" item="${actionBean.patient}"/>
                </tr>
            </tbody>
        </table>

    </stripes:layout-component>
</stripes:layout-render>