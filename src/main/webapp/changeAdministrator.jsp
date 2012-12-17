<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="change_administrator" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ChangeAdministrator"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li class="active"><s:link href="/changeAdministrator.jsp"><f:message
                    key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
    </s:layout-component>


    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.ChangeAdministrator">
            <fieldset>
                <legend><f:message key="change_administrator"/></legend>
                <s:select name="user.id">
                    <s:option value=""><f:message key="select_one"/></s:option>
                    <s:options-collection collection="${ab.users}" label="name" value="id"/>
                </s:select>
                <s:submit name="changeAdministrator"><f:message key="change_administrator"/></s:submit>
            </fieldset>

        </s:form>
    </s:layout-component>
</s:layout-render>