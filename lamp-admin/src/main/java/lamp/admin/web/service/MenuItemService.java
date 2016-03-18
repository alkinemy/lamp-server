package lamp.admin.web.service;

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
			//Agent
			{
				List<MenuItem> subMenuItems = new ArrayList<>();

				{
					MenuItem agentInstall  = MenuItem.of(AGENT_INSTALL, "Agent Install", null);
					MenuItem agentStart  = MenuItem.of(AGENT_START, "Agent Start", null);
					MenuItem agentStop  = MenuItem.of(AGENT_STOP, "Agent Stop", null);

					MenuItem targetServer = MenuItem.of(TARGET_SERVER, "Target Server", "/server/target-server", "icon-bulb");
					targetServer.setSubMenuItems(Lists.newArrayList(agentInstall, agentStart, agentStop));
					subMenuItems.add(targetServer);
				}
				{

					MenuItem agentAppMenuItem  = MenuItem.of(AGENT_APP, "Agent App", null);
					MenuItem availableAgentMenuItem = MenuItem.of(AGENT, "Agent", "/server/agent", "icon-bulb");
					availableAgentMenuItem.setSubMenuItems(Lists.newArrayList(agentAppMenuItem));
					subMenuItems.add(availableAgentMenuItem);

				}
				subMenuItems.add(MenuItem.of(SSH_KEY, "SSH Key", "/ssh-key", "icon-bulb"));
				subMenuItems.add(MenuItem.of(AGENT_EVENT, "Agent Event", "/agent/event", "icon-bulb"));

				menuItems.add(MenuItem.of(SERVER, "Server", "icon-layers", subMenuItems));
			}
			//App
			{
				List<MenuItem> subMenuItems = new ArrayList<>();
				subMenuItems.add(MenuItem.of(MANAGED_APP, "Managed App", "/app", "icon-bulb"));
				subMenuItems.add(MenuItem.of(APP_REPO, "App Repository", "/app/repository", "icon-bulb"));
				subMenuItems.add(MenuItem.of(APP_TEMPLATE, "App Template", "/app/template", "icon-bulb"));
				subMenuItems.add(MenuItem.of(LOCAL_APP_FILE, "Local App File", "/app/file/LOCAL", "icon-bulb"));

				menuItems.add(MenuItem.of("APP", "App", "icon-layers", subMenuItems));
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
