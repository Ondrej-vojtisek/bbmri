<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="biobanks.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.BiobankActionBean"/>
<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">


        <fieldset>
            <legend><f:message key="biobanks.listOfBanks"/></legend>
            <table border="1">
                <tr>
                    <td><f:message key="id"/></td>
                    <td><f:message key="address"/></td>
                    <td><f:message key="biobank.operator"/></td>
                </tr>
                <c:forEach items="${ab.biobanks}" var="z">
                    <tr>
                        <td><c:out value="${z.id}"/></td>
                        <td><c:out value="${z.address}"/></td>
                        <td><c:out value="${z.admin}"/></td>

                    </tr>
                </c:forEach>
            </table>
        </fieldset>

        <s:useActionBean var="ab" beanclass="bbmri.action.BiobankActionBean"/>
        <s:form beanclass="bbmri.action.BiobankActionBean">
            <fieldset>
                <legend><f:message key="biobank.add.new"/></legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1"><f:message key="address"/></label></td>
                        <td><s:text id="z1" name="biobank.address"/></td>
                    </tr>
                </table>
                <s:submit name="create"><f:message key="add"/></s:submit>
            </fieldset>

        </s:form>

    </s:layout-component>
</s:layout-render>