<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_all.jsp"><f:message key="projects"/></s:link></li>
        <li><s:link href="/biobank_all.jsp"><f:message key="biobanks"/></s:link></li>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/user_all.jsp"><f:message key="users"/></s:link></li>
        </c:if>
        <c:if test="${ab.loggedUser.administrator}">
            <li><s:link href="/changeAdministrator.jsp"><f:message key="change_administrator"/></s:link></li>
        </c:if>
    </s:layout-component>

    <s:layout-component name="secondary_menu">
        <c:if test="${ab.loggedUser.biobank != null}">
            <li><s:link href="/biobank_all.jsp"><f:message key="all"/></s:link></li>
            <c:if test="${ab.loggedUser.administrator}">
                <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
            </c:if>
            <c:if test="${ab.loggedUser.biobank != null}">
                <li class="active"><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
                <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
                <li><s:link href="/sample_release.jsp"><f:message key="sample.release"/></s:link></li>
            </c:if>
        </c:if>
    </s:layout-component>


    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean">

            <fieldset>
                <table>
                    <tr>
                        <th><s:label name="request.id"/></th>
                        <th><s:label name="request.project"/></th>
                        <th><s:label name="request.requestState"/></th>
                        <th><s:label name="request.sample"/></th>
                        <th colspan="2"><s:label name="actions"/></th>
                    </tr>

                    <c:forEach items="${ab.requests}" var="request">
                        <tr>
                            <td><c:out value="${request.id}"/></td>
                            <td><c:out value="${request.project.id}"/></td>
                            <td><c:out value="${request.requestState}"/></td>
                            <td><c:out value="${request.sample.id}"/></td>
                            <td>
                                <s:link beanclass="bbmri.action.SampleRequest.ApproveRequestActionBean" event="approve">
                                    <s:param name="request.id" value="${request.id}"/><f:message
                                        key="approve"/></s:link>
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