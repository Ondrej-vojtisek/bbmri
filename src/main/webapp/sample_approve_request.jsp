<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}">
    <s:layout-component name="primary_menu">
        <li><s:link href="/project_my_projects.jsp"><f:message key="projects"/></s:link></li>
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
            <li><s:link href="/biobank_all.jsp"><f:message key="biobanks_all"/></s:link></li>
            <c:if test="${ab.loggedUser.administrator}">
                <li><s:link href="/biobank_create.jsp"><f:message key="biobank_create"/></s:link></li>
            </c:if>
            <c:if test="${ab.loggedUser.biobank != null}">
                <li class="active"><s:link href="/sample_approve_request.jsp"><f:message key="approve_sample_request"/></s:link></li>
                <li><s:link href="/sample_create.jsp"><f:message key="sample_create"/></s:link></li>
                <li><s:link href="/sample_release.jsp"><f:message key="sample.release"/></s:link></li>
                <li><s:link href="/sample_released.jsp"><f:message key="sample.released"/></s:link></li>
                <li><s:link href="/sample_amortize.jsp"><f:message key="sample.amortize"/></s:link></li>
            </c:if>
        </c:if>
    </s:layout-component>

    <s:layout-component name="body">
        <s:form beanclass="bbmri.action.SampleQuestionActionBean">
            <table id="sortableTable">
                <thead>
                <tr>
                    <th><s:label name="project.name"/></th>
                    <th><s:label name="project.owner"/></th>
                    <th><s:label name="sampleQuestion.specification"/></th>
                </tr>

                </thead>
                <tbody>

                <c:forEach items="${ab.sampleQuestions}" var="sampleQuestion">
                    <tr>
                        <td><c:out value="${sampleQuestion.project.name}"/></td>
                        <td><c:out value="${sampleQuestion.project.owner.wholeName}"/></td>
                        <td><c:out value="${sampleQuestion.specification}"/></td>
                        <td>
                            <s:link beanclass="bbmri.action.SampleQuestionActionBean" event="detail">
                                <s:param name="sampleQuestion.id" value="${sampleQuestion.id}"/><f:message
                                    key="detail"/></s:link>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </s:form>
    </s:layout-component>
</s:layout-render>