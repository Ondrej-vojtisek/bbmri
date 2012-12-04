<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="signin" var="title"/>
<s:layout-render name="/model/design2.jsp" title="${title}">
    <s:layout-component name="body">

        <s:form beanclass="bbmri.action.LoginActionBean">
            <fieldset>
                <legend><f:message key="signin"/></legend>
                <s:errors/>
                <table>
                    <tr>
                        <th><label for="z1"><f:message key="researcher_identifier"/></label></th>
                        <td><s:text id="z1" name="id"/></td>
                    </tr>
                    <tr>
                        <th><label for="z2"><f:message key="password"/></label></th>
                        <td><s:password id="z2" name="password"/></td>
                    </tr>
                </table>
                <s:submit name="login"><f:message key="signin"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>