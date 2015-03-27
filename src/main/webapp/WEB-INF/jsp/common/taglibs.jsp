<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="security" uri="http://www.stripes-stuff.org/security.tld"%>
<%@ taglib prefix="n" uri="http://java.sun.com/jsp/jstl/functions"%>
<f:setBundle var="texts" basename="texts" scope="application"/>
<f:setBundle var="errors" basename="errors" scope="application"/>

<c:if test="${empty actionBean}">

	<%--
	Assign alternative action bean when default
	one unavailable due to application exception.
	Required for showing correct username.
	--%>
	<s:useActionBean id="actionBean" beanclass="cz.bbmri.action.base.ErrorActionBean"/>
</c:if>

<c:set var="component" value="${actionBean.component}"/>

