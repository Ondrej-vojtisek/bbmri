<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="myProjects.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.MyProjectsActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <fieldset>
            <legend><f:message key="projects_in_which_im_involved"/></legend>
            <table border="1">
                <tr>
                    <td><f:message key="id"/></td>
                    <td><f:message key="project.name"/></td>
                    <td><f:message key="description"/></td>
                    <td><f:message key="state"/></td>
                </tr>
                <c:forEach items="${ab.projects}" var="z">
                    <tr>
                        <td><c:out value="${z.id}"/></td>
                        <td><c:out value="${z.name}"/></td>
                        <td><c:out value="${z.description}"/></td>
                        <td><c:out value="${z.projectState}"/></td>

                    </tr>
                </c:forEach>
            </table>
        </fieldset>

        <s:useActionBean var="ab" beanclass="bbmri.action.MyProjectsActionBean"/>
        <s:form beanclass="bbmri.action.MyProjectsActionBean">
            <fieldset>
                <legend><f:message key="project.add_new"/></legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1"><f:message key="project.name"/></label></td>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <td><label for="z2"><f:message key="description"/></label></td>
                        <td><s:text id="z2" name="project.description"/></td>
                    </tr>
                </table>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>

        </s:form>


        <s:form beanclass="bbmri.action.MyProjectsActionBean">
            <fieldset>
                <legend><f:message key="project.chose_your_project"/></legend>
                <s:errors/>
                <s:select name="projectId">
                    <s:option value=""><f:message key="select_one"/></s:option>
                    <s:options-collection collection="${ab.projects}" label="name" value="id"/>
                </s:select>
                <s:submit name="selectForEdit"><f:message key="select"/></s:submit>

                <p><f:message key="project.assigned_researchers"/></p>
                <table border="1">
                    <tr>
                        <td><f:message key="id"/></td>
                        <td><f:message key="name"/></td>
                        <td><f:message key="surname"/></td>
                    </tr>

                    <c:forEach items="${ab.researchers}" var="res">
                        <tr>
                            <td><c:out value="${res.id}"/></td>
                            <td><c:out value="${res.name}"/></td>
                            <td><c:out value="${res.surname}"/></td>
                        </tr>
                    </c:forEach>
                </table>
                <fieldset>
                    <legend><f:message key="project.remove_researcher"/></legend>
                    <s:select name="researcherId">
                        <s:option value=""><f:message key="select_one"/></s:option>
                        <s:options-collection collection="${ab.researchers}" label="name" value="id"/>
                    </s:select>
                    <s:submit name="removeResearcherFromProject"><f:message key="remove"/></s:submit>
                </fieldset>

                <fieldset>
                    <legend><f:message key="project.assign_researcher"/></legend>
                    <s:useActionBean var="abResearcher" beanclass="bbmri.action.ResearcherActionBean"/>
                    <s:select name="researcherId">
                        <s:option value=""><f:message key="select_one"/></s:option>
                        <s:options-collection collection="${abResearcher.researchers}" label="name" value="id"/>
                    </s:select>
                    <s:submit name="assignResearcher"><f:message key="project.assign"/></s:submit>
                </fieldset>

                <fieldset>
                    <legend><f:message key="project.update"/></legend>
                    <s:errors/>
                    <table>
                        <tr>
                            <td><label for="z1"><f:message key="project.name"/></label></td>
                            <td><s:text id="z1" name="project.name"/></td>
                        </tr>
                        <tr>
                            <td><label for="z2"><f:message key="description"/></label></td>
                            <td><s:text id="z2" name="project.description"/></td>
                        </tr>

                    </table>
                    <s:submit name="update"><f:message key="save"/></s:submit>
                </fieldset>
            </fieldset>


        </s:form>
    </s:layout-component>
</s:layout-render>