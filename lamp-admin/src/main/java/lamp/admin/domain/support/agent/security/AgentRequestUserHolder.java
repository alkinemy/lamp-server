package lamp.admin.domain.support.agent.security;

import java.util.Objects;

public class AgentRequestUserHolder {

	private static final ThreadLocal<AgentRequestUser> CONTEXT_HOLDER = new ThreadLocal<>();

	public static void clear() {
		CONTEXT_HOLDER.remove();
	}

	public static AgentRequestUser getRequestUser() {
		AgentRequestUser ctx = CONTEXT_HOLDER.get();

		if (ctx == null) {
			ctx = createDefaultRequestUser();
			CONTEXT_HOLDER.set(ctx);
		}

		return ctx;
	}

	public static void setRequestUser(AgentRequestUser requestUser) {
		Objects.requireNonNull(requestUser, "Only non-null RequestUser instances are permitted");

		CONTEXT_HOLDER.set(requestUser);
	}

	public static AgentRequestUser createDefaultRequestUser() {
		return AgentRequestUser.of("SYSTEM", null);
	}
}
