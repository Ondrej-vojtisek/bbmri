<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="index.title" var="title"/>
<s:layout-render name="/model/design2.jsp" title="${title}">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.LoginActionBean">
            <fieldset>
                <legend>Login</legend>
                <s:errors/>
                <table>
                    <tr>
                        <td><label for="z1">Researcher identifier</label></td>
                        <td><s:text id="z1" name="id"/></td>
                    </tr>
                    <tr>
                        <td><label for="z2">Password</label></td>
                        <td><s:password id="z2" name="password"/></td>
                    </tr>


                </table>
                <s:submit name="login">Log in</s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>