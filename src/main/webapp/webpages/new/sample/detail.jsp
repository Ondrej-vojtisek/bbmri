<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-render name="${component.layout.content}">

    <s:layout-component name="body">

        <s:layout-render name="${component.menu.sample}" active="detail"/>

        <table class="table">
            <s:layout-render name="${component.header.sample}"/>

            <tbody>
                <tr>
                    <s:layout-render name="${component.row.sample}" item="${actionBean.sample}"/>
                </tr>
            </tbody>
        </table>

    </s:layout-component>
</s:layout-render>