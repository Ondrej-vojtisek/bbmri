package cz.bbmri.service;

import cz.bbmri.entities.Module;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.2.14
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public interface ModuleService  {

    Module get(Long moduleId);
}