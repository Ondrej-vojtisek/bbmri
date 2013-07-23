<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="signin" var="title"/>
<s:layout-render name="/layout_login.jsp" title="${title}">
    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.LoginActionBean">
                    <fieldset>
                        <legend><f:message key="signin"/></legend>
                        <s:errors/>
                        <table>
                            <tr>
                                <th><label for="z1"><f:message key="identifier"/></label></th>
                                <td><s:text id="z1" name="id"/></td>
                            </tr>
                            <tr>
                                <th><label for="z2"><f:message key="password"/></label></th>
                                <td><s:password id="z2" name="password"/></td>
                            </tr>
                        </table>
                        <div style="float: right;">
                            <s:submit name="login"><f:message key="signin"/></s:submit>
                            <s:submit name="cancel"><f:message key="cancel"/></s:submit>
                        </div>
                    </fieldset>
                </s:form>
    </s:layout-component>
</s:layout-render>