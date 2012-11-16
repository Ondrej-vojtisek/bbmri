<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>


<f:message key="researchers.title" var="title"/>
<s:useActionBean var="ab" beanclass="bbmri.action.ResearcherActionBean"/>


<s:layout-render name="/model/design.jsp" title="${title}" logged="${ab.loggedResearcher.name}">
    <s:layout-component name="body">
        <c:choose>
            <c:when test="${ab.loggedResearcher.online==true}">


                <c:choose>
                    <c:when test="${ab.loggedResearcher.biobank!=null}">
                        <table border="1">
                            <tr>
                                <th><f:message key="id"/></th>
                                <th><f:message key="name"/></th>
                                <th><f:message key="surname"/></th>
                            </tr>

                            <c:forEach var="z" items="${ab.researchers}">
                                <tr>
                                    <td><c:out value="${z.id}"/></td>
                                    <td><c:out value="${z.name}"/></td>
                                    <td><c:out value="${z.surname}"/></td>
                                </tr>
                            </c:forEach>
                        </table>

                        <s:form beanclass="bbmri.action.ResearcherActionBean">
                            <fieldset>
                                <legend><f:message key="index.newAccount"/></legend>
                                <%@include file="/form/createResearcherForm.jsp" %>
                                <s:submit name="create"><f:message key="add"/></s:submit>
                            </fieldset>
                        </s:form>


                        <s:useActionBean var="ab" beanclass="bbmri.action.ResearcherActionBean"/>
                        <s:form beanclass="bbmri.action.ResearcherActionBean">
                            <fieldset>
                                <legend><f:message key="researchers.delete_account"/></legend>
                                <s:errors/>

                                <s:select name="id">
                                    <s:option value=""><f:message key="select_one"/></s:option>
                                    <s:options-collection collection="${ab.researchers}" label="name" value="id"/>
                                </s:select>
                                <s:submit name="delete"><f:message key="delete"/></s:submit>
                            </fieldset>
                        </s:form>
                    </c:when>
                    <c:otherwise>
                        <p><f:message key="you_dont_have_sufficient_rights"/></p>
                        <br/>
                    </c:otherwise>
                </c:choose>


            </c:when>
            <c:otherwise>
                <p><f:message key="you_dont_have_sufficient_rights"/></p>
                <br/>
            </c:otherwise>
        </c:choose>
    </s:layout-component>
</s:layout-render>