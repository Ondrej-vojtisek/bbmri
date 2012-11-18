<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="researcher.create" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ResearcherActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.ResearcherActionBean">
            <fieldset>
                <legend><f:message key="researcher.create"/></legend>
                <%@include file="/form/createResearcherAndPasswordForm.jsp" %>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>