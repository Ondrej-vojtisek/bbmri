<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="sample.request" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ReleaseSampleActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.ReleaseSampleActionBean">
            <fieldset>
                <table border="1">
                    <tr>
                        <th><f:message key="samples.TNM"/></th>
                        <th><f:message key="samples.pTNM"/></th>
                        <th><f:message key="samples.grading"/></th>
                    </tr>

                    <c:forEach items="${ab.samples}" var="sample">
                        <tr>
                            <td><c:out value="${sample.TNM}"/></td>
                            <td><c:out value="${sample.pTNM}"/></td>
                            <td><c:out value="${sample.grading}"/></td>
                            <td><s:link beanclass="bbmri.action.ReleaseSampleActionBean" event="release">
                                <s:param name="sample.id" value="${sample.id}"/><f:message
                                    key="sample.release"/></s:link>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>