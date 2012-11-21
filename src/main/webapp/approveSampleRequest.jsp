<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean">

             <fieldset>
                    <table border="1">
                   <tr>
                        <th><f:message key="request.id"/></th>
                        <th><f:message key="request.project"/></th>
                        <th><f:message key="request.requestState"/></th>
                        <th><f:message key="request.sample"/></th>
                    </tr>

                    <c:forEach items="${ab.requests}" var="request">
                        <tr>
                            <td><c:out value="${request.id}"/></td>
                            <td><c:out value="${request.project.id}"/></td>
                            <td><c:out value="${request.requestState}"/></td>
                            <td><c:out value="${request.sample.id}"/></td>
                            <td>
                               <s:link beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean" event="approve">
                               <s:param name="request.id" value="${request.id}"/><f:message key="approve"/></s:link>
                            </td>
                            <td>
                               <s:link beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean" event="deny">
                               <s:param name="request.id" value="${request.id}"/><f:message key="deny"/></s:link>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>

         </s:form>
    </s:layout-component>
</s:layout-render>