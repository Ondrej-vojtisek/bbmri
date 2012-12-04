<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="change_administrator" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ChangeAdministrator"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
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