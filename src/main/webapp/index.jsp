<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/model/design.jsp" title="Hlavní stránka">
    <s:layout-component name="body">
 
        <s:useActionBean var="ab" beanclass="bbmri.model.ZaznamActionBean"/>
 
        <table border="1">
        <c:forEach var="z" items="${ab.zaznamy}">
            <tr>
                <td><c:out value="${z.jmeno}" /></td>
                <td><c:out value="${z.pocet}" /></td>
            </tr>
            </c:forEach>
        </table>
         <s:useActionBean var="pokus" beanclass="bbmri.model.ZaznamActionBean"/>
        <s:form beanclass="bbmri.model.ZaznamActionBean">
            <fieldset><legend>Nový záznam</legend>
            <s:errors/>
            <table>
                <tr>
                    <td><label for="z1">jméno:</label></td>
                    <td><s:text id="z1" name="zaznam.jmeno" /></td>
                </tr>

                <tr>
                    <td><label for="z2">počet:</label></td>
                    <td><s:text id="z2" name="zaznam.pocet" /></td>
                </tr>

                <tr>
                    <td><label for="z3">delka:</label></td>
                    <td><s:text id="z3" name="pokus" /></td>
                </tr>

            </table>
            <s:submit name="pridej">Přidej</s:submit>
            </fieldset>
         </s:form>
    </s:layout-component>
</s:layout-render>                                       /