<!--
 ! Excerpted from "Stripes: and Java Web Development is Fun Again",
 ! published by The Pragmatic Bookshelf.
 ! Copyrights apply to this code. It may not be used to create training material,
 ! courses, books, articles, and the like. Contact us if you are in doubt.
 ! We make no guarantees that this code is fit for any purpose.
 ! Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
-->
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%-- START:form --%>
<stripes:form partial="true"
  beanclass="cz.bbmri.action.DashboardActionBean">
  <%-- START:models --%>
  <stripes:select name="samples">
    <stripes:option value="" label="..."/>
    <stripes:options-collection collection="${actionBean.samples}" label="id"/>
  </stripes:select>
  <%-- END:models --%>
</stripes:form>
<%-- END:form --%>
