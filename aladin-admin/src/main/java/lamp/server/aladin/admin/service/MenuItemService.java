package lamp.server.aladin.admin.service;

import lamp.server.aladin.admin.domain.MenuItem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lamp.server.aladin.admin.MenuConstants.*;

@Service
public class MenuItemService {

	public List<MenuItem> getMenuItemList() {
		List<MenuItem> menuItems = new ArrayList<>();
		{
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(TARGET_SERVER, "Server", "/target-server", "icon-bulb"));
				subMenuItems.add(MenuItem.of(AGENT, "Agent", "/agent", "icon-bulb"));

				menuItems.add(MenuItem.of("AGENT_TOP", "Agent", "icon-layers", subMenuItems));
			}
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(APP_REPO, "App Repository", "/app-repository", "icon-bulb"));
				subMenuItems.add(MenuItem.of(APP_FILE,"App File", "/app-file", "icon-bulb"));

				menuItems.add(MenuItem.of("APP", "App", "icon-layers", subMenuItems));
			}
		}
		return menuItems;
	}

	public MenuItem getMenuItem(String menuItemId) {
		List<MenuItem> menuItems = getMenuItemList();
		Map<String, MenuItem> menuItemMap = new HashMap<>();
		populateMenuItem(menuItemMap, menuItems);
		return menuItemMap.get(menuItemId);
	}

	protected void populateMenuItem(Map<String, MenuItem> menuItemMap, List<MenuItem> menuItems) {
		for (MenuItem menuItem : menuItems) {
			menuItemMap.put(menuItem.getId(), menuItem);
			if (CollectionUtils.isNotEmpty(menuItem.getSubMenuItems())) {
				populateMenuItem(menuItemMap, menuItem.getSubMenuItems());
			}
		}
	}
}
