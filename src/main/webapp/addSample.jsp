<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">


        <fieldset>

        </fieldset>

    </s:layout-component>
</s:layout-render>