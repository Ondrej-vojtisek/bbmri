<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="change_administrator" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ChangeAdministrator"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="changeadministrator"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}">

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.ChangeAdministrator">
            <fieldset>
                <legend><f:message key="change_administrator"/></legend>
                <s:select name="user.id">
                    <s:option value=""><f:message key="select_one"/></s:option>
                    <s:options-collection collection="${ab.users}" label="wholeName" value="id"/>
                </s:select>
                <s:submit name="changeAdministrator"
                          onclick="return confirm('Change administrator?');"><f:message
                        key="change_administrator"/></s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>