<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:layout-definition>

    <c:if test="${editable}">

        <s:form beanclass="${bean.name}">
            <fieldset>
                <legend>Edit</legend>
                <s:hidden name="biobank.id"/>
                <table>

                    <tr>
                        <th><s:label name="biobank.name"/></th>
                        <td><s:text id="name" name="biobank.name"/></td>
                    </tr>

                    <tr>
                        <th><s:label name="biobank.address"/></th>
                        <td><s:text id="address" name="biobank.address"/></td>
                    </tr>

                </table>
                <s:submit name="update">
                    <s:param name="id" value="${bean.id}"/>
                    <f:message key="save"/></s:submit>
            </fieldset>
        </s:form>
    </c:if>

    <c:if test="${not editable}">
        <fieldset>
            <legend><f:message key="biobank.detail"/></legend>
            <table>
                <tr>
                    <th><s:label name="biobank.name"/></th>
                    <td>${biobank.name}</td>
                </tr>
                <tr>
                    <th><s:label name="biobank.address"/></th>
                    <td>${biobank.address}</td>
                </tr>
            </table>
        </fieldset>
    </c:if>


</s:layout-definition>