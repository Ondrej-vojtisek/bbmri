<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<s:useActionBean var="ab" beanclass="bbmri.action.MyProjectsActionBean"/>
<s:layout-render name="/model/design.jsp" title="Projekty" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <table border="1">
            <p>My projects</p>
            <c:forEach items="${ab.projects}" var="z">
                <tr>
                    <td><c:out value="${z.id}"/></td>
                    <td><c:out value="${z.name}"/></td>
                    <td><c:out value="${z.description}"/></td>
                    <td><c:out value="${z.projectState}"/></td>

                </tr>
            </c:forEach>
        </table>

        <s:useActionBean var="ab" beanclass="bbmri.action.MyProjectsActionBean"/>
        <s:form beanclass="bbmri.action.MyProjectsActionBean">
            <fieldset>
                <legend>New project</legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1">Name</label></td>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <td><label for="z2">Description</label></td>
                        <td><s:text id="z2" name="project.description"/></td>
                    </tr>
                </table>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>

        </s:form>


        <s:form beanclass="bbmri.action.MyProjectsActionBean">
            <fieldset>
                <legend>Choose your project</legend>
                <s:errors/>
                <s:select name="projectId">
                    <s:option value="">Select from your projects</s:option>
                    <s:options-collection collection="${ab.projects}" label="name" value="id"/>
                </s:select>
                <s:submit name="selectForEdit">Select</s:submit>

                <table border="1">
                    <p>Researchers which are assigned</p>
                    <c:forEach items="${ab.researchers}" var="res">
                        <tr>
                            <td><c:out value="${res.id}"/></td>
                            <td><c:out value="${res.name}"/></td>

                        </tr>
                    </c:forEach>
                </table>
                <fieldset>
                    <legend>Remove researcher from project</legend>
                    <s:select name="researcherId">
                        <s:option value="">Select One</s:option>
                        <s:options-collection collection="${ab.researchers}" label="name" value="id"/>
                    </s:select>
                    <s:submit name="removeResearcherFromProject">Remove researcher</s:submit>
                </fieldset>

                <fieldset>
                    <legend>Assign researcher to project</legend>
                    <s:useActionBean var="abResearcher" beanclass="bbmri.action.ResearcherActionBean"/>
                    <s:select name="researcherId">
                        <s:option value="">Select One</s:option>
                        <s:options-collection collection="${abResearcher.researchers}" label="name" value="id"/>
                    </s:select>
                    <s:submit name="assignResearcher">Assign researcher</s:submit>
                </fieldset>

                <fieldset>
                <legend>Change project</legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1">Name</label></td>
                        <td><s:text id="z1" name="project.name"/></td>
                    </tr>
                    <tr>
                        <td><label for="z2">Description</label></td>
                        <td><s:text id="z2" name="project.description"/></td>
                    </tr>

                </table>
                <s:submit name="update">Save</s:submit>
            </fieldset>
            </fieldset>



        </s:form>
    </s:layout-component>
</s:layout-render>