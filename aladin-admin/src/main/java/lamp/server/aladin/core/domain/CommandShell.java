package lamp.server.aladin.core.domain;

public enum CommandShell {

	NONE("")
	, SH("/bin/sh -c")
	, BASH("/bin/bash -c")
	, KSH("/bin/ksh -c")
	, CMD("cmd /c")
	, POWERSHELL("powershell -Command")
	;

	private String shell;

	CommandShell(String shell) {
		this.shell = shell;
	}
}
