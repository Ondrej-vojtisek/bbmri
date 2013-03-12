<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>

<table>
    <tr>
        <th><s:label for="z1" name="project.name"/></th>
        <td><s:text id="z1" name="project.name"/></td>
    </tr>
    <tr>
        <th><s:label for="z2" name="project.fundingOrganization"/></th>
        <td><s:text id="z2" name="project.fundingOrganization"/></td>
    </tr>
    <tr>
        <th>
            <p><s:label for="z3" name="project.annotation"/></p>
        </th>
    </tr>

</table>

<s:textarea id="z3" name="project.annotation"></s:textarea>



