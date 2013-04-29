<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<f:message key="samples.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.SampleQuestionActionBean"/>
<s:layout-render name="/layout_content.jsp" title="${title}" logged="${ab.loggedUser.name}"
                 primarymenu="biobank"
                 biobank="${ab.loggedUser.biobank}"
                 administrator="${ab.loggedUser.administrator}"
                 secondarymenu="sample_approve_request">

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