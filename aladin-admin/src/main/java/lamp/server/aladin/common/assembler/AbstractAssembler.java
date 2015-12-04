package lamp.server.aladin.common.assembler;

import lamp.server.aladin.common.assembler.*;

public abstract class AbstractAssembler<F, T> implements lamp.server.aladin.common.assembler.Assembler<F, T> {

	public final T assemble(F f) {
		if (f == null) {
			return null;
		}
		return doAssemble(f);
	}

	protected abstract T doAssemble(F f);

}
