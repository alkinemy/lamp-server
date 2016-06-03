package lamp.admin.web.app.controller;

//@Slf4j
//@MenuMapping(MenuConstants.APP_TEMPLATE)
//@Controller
//@RequestMapping("/app/template/{id}")
public class AppTemplateDeployController {

//	@Autowired
//	private AppTemplateService appTemplateService;
//
//	@Autowired
//	private AppInstallScriptService appInstallScriptService;
//
//	@Autowired
//	private AppRepoService appRepoService;
//
//	@Autowired
//	private TargetServerService targetServerService;
//
//	@Autowired
//	private AppFacadeService appFacadeService;
//
//
//	@RequestMapping(path = "/deploy", method = RequestMethod.GET)
//	public String deploy(@PathVariable("id") String templateId,
//						 @ModelAttribute("editForm") AppTemplateDeployForm editForm,
//						 Model model, HttpMethod httpMethod) {
//		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);
//
//		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDtoOptional(templateId);
//
//		List<AppInstallScriptDto> appInstallScriptDtoList = appInstallScriptService.getAppInstallScriptDtoList(appTemplateDto.getId());
//		model.addAttribute("appInstallScripts", appInstallScriptDtoList);
//
//		model.addAttribute("appTemplate", appTemplateDto);
//		model.addAttribute("parametersTypes", ParametersType.values());
//
//		List<String> versions = appRepoService.getVersions(appTemplateDto.getRepositoryId(), appTemplateDto.getGroupId(), appTemplateDto.getArtifactId());
//		model.addAttribute("versions", versions);
//
//
//		List<TargetServerDto> targetServerDtoList = targetServerService.getTargetServerDtoList();
//		model.addAttribute("targetServers", targetServerDtoList);
//
//		if (httpMethod.equals(HttpMethod.GET)) {
//			editForm.setId("");
//			editForm.setName(appTemplateDto.getName());
//
//			editForm.setParametersType(appTemplateDto.getParametersType());
//			editForm.setParameters(appTemplateDto.getParameters());
//			editForm.setVersion(versions.stream().findFirst().orElse(null));
//		}
//		return "app/template/deploy";
//	}
//
//	@RequestMapping(path = "/deploy", method = RequestMethod.POST)
//	public String deploy(@PathVariable("id") String templateId,
//						 @Valid @ModelAttribute("editForm") AppTemplateDeployForm editForm,
//						 Model model,
//						 BindingResult bindingResult,
//						 RedirectAttributes redirectAttributes) {
//		int serverSize = editForm.getTargetServerIds() != null ? editForm.getTargetServerIds().size() : 0;
//		if (StringUtils.isNotBlank(editForm.getId())) {
//			String[] ids = StringUtils.split(editForm.getId(), ',');
//			if (ids.length == serverSize) {
//				editForm.setIds(ids);
//			} else {
//				bindingResult.rejectValue("id", "AppTemplateDeployForm.id", "아이디 갯수는 서버 갯수와 일치해야합니다.");
//			}
//		} else {
//			editForm.setIds(new String[serverSize]);
//		}
//
//		if (bindingResult.hasErrors()) {
//			return deploy(templateId, editForm, model, HttpMethod.POST);
//		}
//
//		try {
//			editForm.setTemplateId(templateId);
//			appFacadeService.deployApp(editForm);
//
//			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));
//
//			return "redirect:/app/template";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return deploy(templateId, editForm, model, HttpMethod.POST);
//		}
//
//	}


}
