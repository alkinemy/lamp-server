package lamp.server.aladin.admin.domain;

import lamp.server.aladin.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString(exclude = "parentMenuItem")
public class MenuItem {

	private String id;
	private String icon;
	private String title;
	private String href;

	private MenuItem parentMenuItem;
	private List<MenuItem> subMenuItems;

	public MenuItem() {
	}

	public List<MenuItem> getBreadcrumb() {
		List<MenuItem> breadcrumb = new ArrayList<>();
		if (parentMenuItem != null) {
			breadcrumb.addAll(parentMenuItem.getBreadcrumb());
		}
		breadcrumb.add(this);
		return breadcrumb;
	}

	public boolean isLeaf() {
		return CollectionUtils.isEmpty(subMenuItems);
	}

	public boolean isSelected(String selectedMenuItemId) {
		return Objects.equals(id, selectedMenuItemId);
	}

	public boolean isSubMenuItemId(String selectedMenuItemId) {
		if (StringUtils.isBlank(selectedMenuItemId)) {
			return false;
		}
		if (selectedMenuItemId.equals(id)) {
			return true;
		}
		if (subMenuItems == null) {
			return false;
		}
		for (MenuItem subMenuItem : subMenuItems) {
			if (subMenuItem.isSubMenuItemId(selectedMenuItemId)) {
				return true;
			}
		}
		return false;
	}

	public static MenuItem of(String id, String title, String href) {
		return of(id, null, title, href);
	}

	public static MenuItem of(String id, String title, String href, String icon) {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(id);
		menuItem.setTitle(title);
		menuItem.setHref(href);
		menuItem.setIcon(icon);
		return menuItem;
	}

	public static MenuItem of(String id, String title, String icon, List<MenuItem> subMenuItems) {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(id);
		menuItem.setTitle(title);
		menuItem.setIcon(icon);
		menuItem.setSubMenuItems(subMenuItems);
		if (CollectionUtils.isNotEmpty(subMenuItems)) {
			for (MenuItem subMenuItem : subMenuItems) {
				subMenuItem.setParentMenuItem(menuItem);
			}
		}
		return menuItem;
	}

}
