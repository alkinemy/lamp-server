package lamp.admin.web.base.service;

import com.google.common.collect.Lists;
import lamp.admin.web.account.model.MenuItem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lamp.admin.web.MenuConstants.*;

@Service
public class MenuItemService {

	public List<MenuItem> getMenuItemList() {
		List<MenuItem> menuItems = new ArrayList<>();
		{

			//App
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(APP, "Applications", "/apps", "icon-bulb"));

				menuItems.add(MenuItem.of("APP", "App", "icon-layers", subMenuItems));
			}
			//Monitoring
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(MONITORING_WATCH_TARGET, "Watch Target", "/monitoring/watch-target", "icon-bulb"));
				menuItems.add(MenuItem.of(MONITORING, "Monitoring", "icon-layers", subMenuItems));
			}

			// Docker
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(DOCKER_APPS, "Application", "/docker/apps", "icon-bulb"));
				menuItems.add(MenuItem.of(DOCKER, "Docker", "icon-layers", subMenuItems));
			}

			// Host
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(HOSTS, "Host", "/hosts", "icon-bulb"));
				{

					MenuItem agentAppMenuItem  = MenuItem.of(AGENT_APP, "Agent App", null);
					MenuItem agentMenuItem = MenuItem.of(AGENT, "Agent", "/agents", "icon-bulb");
					agentMenuItem.setSubMenuItems(Lists.newArrayList(agentAppMenuItem));
					subMenuItems.add(agentMenuItem);

				}
				menuItems.add(MenuItem.of(HOST, "Host", "icon-layers", subMenuItems));
			}

			// Resource
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(APP_REPO, "App Repository", "/resource/repository", "icon-bulb"));
				subMenuItems.add(MenuItem.of(LOCAL_APP_FILE, "Local App File", "/resource/file/LOCAL", "icon-bulb"));

				menuItems.add(MenuItem.of(RESOURCE, "Resource", "icon-layers", subMenuItems));
			}

			//Account
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(ACCOUNT_APPROVAL, "Approval", "/account/approval", "icon-bulb"));
				menuItems.add(MenuItem.of(ACCOUNT, "Account", "icon-layers", subMenuItems));
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
