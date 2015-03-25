package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.entity.Project;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/project/{$event}/{id}")
public class ProjectActionBean  extends ComponentActionBean {
}
