<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>
    <s:form beanclass="bbmri.action.biobank.BiobankActionBean">

        <c:if test="${editable}">
            <s:hidden name="biobank.id"/>
            <fieldset>
                <legend><f:message key="project.edit"/></legend>
                <%@include file="/form/createBiobankForm.jsp" %>
                <s:submit name="update"><f:message key="save"/></s:submit>
            </fieldset>
        </c:if>

        <c:if test="${not editable}">
            <fieldset>
                <legend><f:message key="biobank.detail"/></legend>
                <table>
                    <tr>
                        <th><s:label name="biobank.name"/></th>
                        <td><s:text name="biobank.name" readonly="true"/></td>
                    </tr>
                    <tr>
                        <th><s:label name="biobank.address"/></th>
                        <td><s:text name="biobank.address" readonly="true"/></td>
                    </tr>
                </table>
            </fieldset>
        </c:if>

    </s:form>

</s:layout-definition>