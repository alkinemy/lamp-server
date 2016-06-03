package lamp.admin.web.app.controller;



//@Slf4j
//@MenuMapping(MenuConstants.APP_TEMPLATE)
//@Controller
//@RequestMapping("/app/template")
public class AppTemplateController {

//	@Autowired
//	private AppTemplateService appTemplateService;
//
//	@Autowired
//	private AppInstallScriptService appInstallScriptService;
//	@Autowired
//	private AppRepoService appRepoService;
//
//	@RequestMapping(path = "", method = RequestMethod.GET)
//	public String list(Model model, Pageable pageable) {
//		Page<AppTemplateDto> page = appTemplateService.getAppTemplateDtoList(pageable);
//		model.addAttribute("page", page);
//		return "app/template/list";
//	}
//
//	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
//	public String view(@PathVariable("id") String id, Model model) {
//		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDto(id);
//		model.addAttribute("appTemplate", appTemplateDto);
//
//		List<AppInstallScriptDto> appInstallScriptDtos = appInstallScriptService.getAppInstallScriptDtoList(id);
//		model.addAttribute("appInstallScripts", appInstallScriptDtos);
//
//		return "app/template/view";
//	}
//
//	@RequestMapping(path = "/create", method = RequestMethod.GET)
//	public String create(@ModelAttribute("editForm") AppTemplateCreateForm editForm, Model model) {
//
//		editForm.setWorkDirectory("${appDirectory}");
//		editForm.setPidFile("${workDirectory}/${artifactId}.pid");
//		editForm.setStartCommandLine("./${artifactId}.sh start");
//		editForm.setStopCommandLine("./${artifactId}.sh stop");
//
//		return createForm(editForm, model);
//	}
//
//	protected String createForm(@ModelAttribute("editForm") AppTemplateCreateForm editForm, Model model) {
//		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);
//
//		if (editForm.getRepositoryId() != null) {
//			AppRepo appRepo = appRepoService.getAppRepo(editForm.getRepositoryId());
//			model.addAttribute("appRepository", appRepo);
//		}
//
//		AppResourceType templateType = editForm.getResourceType();
//
//		if (!AppResourceType.NONE.equals(templateType)) {
//			List<AppRepoDto> appRepoList = appRepoService.getAppRepoListByType(templateType);
//			model.addAttribute("appRepositoryList", appRepoList);
//		}
//
//		model.addAttribute("commandShellList", CommandShell.values());
//
//		model.addAttribute("parametersTypes", ParametersType.values());
//
//		return "app/template/edit";
//	}
//
//	@RequestMapping(path = "/create", method = RequestMethod.POST)
//	public String create(@Valid @ModelAttribute("editForm") AppTemplateCreateForm editForm,
//			BindingResult bindingResult, Model model,
//			RedirectAttributes redirectAttributes) {
//		if (bindingResult.hasErrors()) {
//			return createForm(editForm, model);
//		}
//		appTemplateService.insertAppTemplate(editForm);
//
//		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));
//
//
//		return "redirect:/app/template";
//	}
//
//
//	@RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
//	public String update(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppTemplateUpdateForm editForm, Model model) {
//
//		AppTemplateUpdateForm updateForm = appTemplateService.getAppTemplateUpdateForm(id);
//		BeanUtils.copyProperties(updateForm, editForm);
//
//		return updateForm(id, editForm, model);
//	}
//
//	protected String updateForm(String id,
//								AppTemplateUpdateForm editForm, Model model) {
//		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);
//
//		if (editForm.getRepositoryId() != null) {
//			AppRepo appRepo = appRepoService.getAppRepo(editForm.getRepositoryId());
//			model.addAttribute("appRepository", appRepo);
//		}
//
//		AppResourceType templateType = editForm.getResourceType();
//		if (!AppResourceType.NONE.equals(templateType)) {
//			List<AppRepoDto> appRepoList = appRepoService.getAppRepoListByType(templateType);
//			model.addAttribute("appRepositoryList", appRepoList);
//		}
//
//		EnumSet<CommandShell> commandShellList = EnumSet.allOf(CommandShell.class);
//		model.addAttribute("commandShellList", commandShellList);
//
//		model.addAttribute("parametersTypes", ParametersType.values());
//
//		return "app/template/edit";
//	}
//
//	@RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
//	public String update(@PathVariable("id") String id,
//						 @Valid @ModelAttribute("editForm") AppTemplateUpdateForm editForm,
//			BindingResult bindingResult, Model model,
//			RedirectAttributes redirectAttributes) {
//		if (bindingResult.hasErrors()) {
//			return updateForm(id, editForm, model);
//		}
//		appTemplateService.updateAppTemplate(id, editForm);
//
//		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));
//
//		return "redirect:/app/template";
//	}
//
//	@RequestMapping(path = "/delete", method = RequestMethod.GET)
//	public String delete(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
//
//		appTemplateService.deleteAppTemplate(id);
//
//		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS));
//
//		return "redirect:/app/template";
//	}
}
