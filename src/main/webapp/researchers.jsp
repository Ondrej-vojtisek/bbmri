<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="index.title" var="title" />
    <s:layout-render name="/model/design.jsp" title="${title}">
    <s:layout-component name="body">


        <s:useActionBean var="ab" beanclass="bbmri.model.ResearcherActionBean"/>

        <table border="1">
        <c:forEach var="z" items="${ab.researchers}">
            <tr>
                <td><c:out value="${z.id}" /></td>
                <td><c:out value="${z.name}" /></td>
                <td><c:out value="${z.surname}" /></td>
            </tr>
            </c:forEach>
        </table>


        <s:form beanclass="bbmri.model.ResearcherActionBean">
            <fieldset><legend><f:message key="index.newAccount"/></legend>
            <s:errors/>
            <table>
                <tr>
                    <td><label for="z1"><f:message key="index.Name"/></label></td>
                    <td><s:text id="z1" name="researcher.name"/></td>
                </tr>
                <tr>
                    <td><label for="z2"><f:message key="index.Surname"/></label></td>
                    <td><s:text id="z2" name="researcher.surname"/></td>
                </tr>
                <tr>
                    <td><label for="z3">Password</label></td>
                    <td><s:text id="z3" name="researcher.password"/></td>
                </tr>

            </table>
            <s:submit name="pridej"><f:message key="add"/></s:submit>
            </fieldset>
       </s:form>



         <s:useActionBean var="ab" beanclass="bbmri.model.ResearcherActionBean"/>
         <s:form beanclass="bbmri.model.ResearcherActionBean">
            <fieldset><legend>Delete account</legend>
            <s:errors/>

             <s:select name="id">
                <s:option value="">Select One</s:option>
                <s:options-collection collection="${ab.researchers}" label="name" value="id"/>
            </s:select>
            <s:submit name="delete"><f:message key="delete"/></s:submit>
            </fieldset>
       </s:form>




    </s:layout-component>
</s:layout-render>