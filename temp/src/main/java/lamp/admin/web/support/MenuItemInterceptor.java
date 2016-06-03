package lamp.admin.web.support;

import lamp.admin.web.account.model.MenuItem;
import lamp.admin.web.base.service.MenuItemService;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Component
public class MenuItemInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MenuItemService menuItemService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		List<MenuItem> menuItemList = menuItemService.getMenuItemList();
		request.setAttribute("MENU_ITEMS", menuItemList);
		log.debug("MENU_ITEMS = {}", menuItemList);

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			MenuMapping menuMapping = handlerMethod.getMethodAnnotation(MenuMapping.class);
			if (menuMapping == null) {
				menuMapping = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), MenuMapping.class);
			}

			if (menuMapping != null) {
				String menuItemId = menuMapping.value();

				MenuItem selectedMenuItem = menuItemService.getMenuItem(menuItemId);
				request.setAttribute("SELECTED_MENU_ITEM_ID", menuItemId);
				request.setAttribute("SELECTED_MENU_ITEM", selectedMenuItem);
			}
		}

		return true;
	}

}
