<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="security" uri="http://www.stripes-stuff.org/security.tld"%>
<%--<%@ taglib prefix="function" uri="http://java.sun.com/jsp/jstl/functions"%> formerly <n: --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<format:setBundle var="texts" basename="texts" scope="application"/>
<format:setBundle var="errors" basename="errors" scope="application"/>

<core:if test="${empty actionBean}">

	<%--
	Assign alternative action bean when default
	one unavailable due to application exception.
	Required for showing correct username.
	--%>
	<stripes:useActionBean id="actionBean" beanclass="cz.bbmri.action.base.ErrorActionBean"/>
</core:if>

<core:set var="component" value="${actionBean.component}"/>

