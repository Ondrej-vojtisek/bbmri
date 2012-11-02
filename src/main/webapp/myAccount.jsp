<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="myAccount.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.AccountActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">

        <table border="1">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Surname</td>
                <td>Online</td>
            </tr>
            <tr>
                <td><c:out value="${ab.loggedResearcher.id}"/></td>
                <td><c:out value="${ab.loggedResearcher.name}"/></td>
                <td><c:out value="${ab.loggedResearcher.surname}"/></td>
                <td><c:out value="${ab.loggedResearcher.online}"/></td>
            </tr>
        </table>

        <s:form beanclass="bbmri.action.AccountActionBean">
            <fieldset>
                <legend>Update credentials</legend>
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
                <s:submit name="update">Save</s:submit>
            </fieldset>
        </s:form>


    </s:layout-component>
</s:layout-render>