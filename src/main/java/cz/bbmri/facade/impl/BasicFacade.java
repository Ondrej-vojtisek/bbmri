package cz.bbmri.facade.impl;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
class BasicFacade {

//  protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private BiobankService biobankService;
//
//    @Autowired
//    private ProjectAdministratorService projectAdministratorService;
//
//    @Autowired
//    private BiobankAdministratorService biobankAdministratorService;
//
//    public static void notNull(final Object o) throws IllegalArgumentException {
//        if (o == null) {
//            throw new IllegalArgumentException("Object can't be a null object");
//        }
//    }
//
//    public static void objectNotFound(final Object o, final Long id) throws IllegalArgumentException {
//        if (o == null) {
//            throw new IllegalArgumentException("Object can't be a null object. " +
//                    "It means that given identifier isn't present in database. ID: " + id);
//        }
//    }
//
//    public static void fatalError(ValidationErrors errors) {
//        errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.dbg.fatal"));
//    }
//
//    /* Definition here because it is useful at more facades when we need to send notification */
//    public List<User> getProjectAdministratorsUsers(Long projectId) {
//       Project projectDB = projectService.get(projectId);
//       // Project projectDB = projectService.eagerGet(projectId, true, false, false);
//        List<User> users = new ArrayList<User>();
//        for (ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()) {
//            users.add(projectAdministrator.getUser());
//        }
//        return users;
//    }
//
//    /* Definition here because it is useful at more facades when we need to send notification */
//    public List<User> getOtherProjectWorkers(Project project, Long excludedUserId) {
//        Project projectDB = projectService.get(project.getId());
//      //  Project projectDB = projectService.eagerGet(project.getId(), true, false, false);
//        Set<ProjectAdministrator> projectAdministrators = projectDB.getProjectAdministrators();
//
//        if (excludedUserId != null) {
//              ProjectAdministrator paExclude = projectAdministratorService.get(project.getId(), excludedUserId);
//              if (projectAdministrators.contains(paExclude)) {
//                  projectAdministrators.remove(paExclude);
//              }
//          }
//
//        List<User> users = new ArrayList<User>();
//        for (ProjectAdministrator pa : projectAdministrators) {
//            users.add(pa.getUser());
//        }
//
//        return users;
//    }
//
//    public List<User> getOtherBiobankAdministrators(Biobank biobank, Long excludedUserId) {
//        Biobank biobankDB = biobankService.get(biobank.getId());
//        Set<BiobankAdministrator> biobankAdministrators = biobankDB.getBiobankAdministrators();
//
//        if (excludedUserId != null) {
//            BiobankAdministrator baExclude = biobankAdministratorService.get(biobank.getId(), excludedUserId);
//            if (biobankAdministrators.contains(baExclude)) {
//                biobankAdministrators.remove(baExclude);
//            }
//        }
//
//        List<User> users = new ArrayList<User>();
//        for (BiobankAdministrator ba : biobankAdministrators) {
//            users.add(ba.getUser());
//        }
//
//        return users;
//    }




}
