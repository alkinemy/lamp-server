package lamp.admin.domain.old.app.model;

import lombok.Getter;

public enum CommandShell {

	NONE("")
	, SH("/bin/sh -c")
	, BASH("/bin/bash -c")
	, KSH("/bin/ksh -c")
	, CMD("cmd /c")
	, POWERSHELL("powershell -Command")
	;

	@Getter
	private String shell;

	CommandShell(String shell) {
		this.shell = shell;
	}
}
