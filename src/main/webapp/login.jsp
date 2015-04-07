<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<format:message key="signin" var="title"/>

<stripes:layout-render name="/layouts/layout-login.jsp" title="${title}">
    <stripes:layout-component name="body">
        <stripes:form beanclass="cz.bbmri.action.LoginActionBean">
            <fieldset>
                <legend><format:message key="signin"/></legend>
                <table>
                    <tr>
                        <th><label for="z1"><format:message key="identifier"/></label></th>
                        <td><stripes:text id="z1" name="id"/></td>
                    </tr>
                    <tr>
                        <th><label for="z2"><format:message key="password"/></label></th>
                        <td><stripes:password id="z2" name="password"/></td>
                    </tr>
                </table>
                <div style="float: right;">
                    <stripes:submit name="login" class="btn btn-primary btnMargin"><format:message key="signin"/></stripes:submit>
                    <stripes:submit name="cancel" class="btn"><format:message key="cancel"/></stripes:submit>
                </div>

            </fieldset>
        </stripes:form>

    </stripes:layout-component>
</stripes:layout-render>