<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="index.title" var="title" />
    <s:layout-render name="/model/design.jsp" title="${title}">


    <s:layout-component name="body">

        <s:useActionBean var="actionBean" beanclass="bbmri.model.ResearcherActionBean"/>

        <table border="1">

        <c:forEach items="${actionBean.researchers}" var="z">
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

            </table>
            <s:submit name="pridej"><f:message key="add"/></s:submit>
            </fieldset>
       </s:form>


         <s:form beanclass="bbmri.model.ResearcherActionBean">
            <fieldset><legend>Delete account</legend>
            <s:errors/>
            <table>
                <tr>
                    <td><label for="z1">Id</label></td>
                    <td><s:text id="z1" name="researcher.id"/></td>
                </tr>

            </table>
            <s:submit name="delete"><f:message key="delete"/></s:submit>
            </fieldset>
       </s:form>


    </s:layout-component>
</s:layout-render>