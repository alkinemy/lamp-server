package lamp.server.aladin.common.assembler;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListAssembler<F, T> extends lamp.server.aladin.common.assembler.AbstractAssembler<F, T> implements ListAssembler<F, T> {

	@Override
	public final List<T> assemble(List<F> fromList) {
		if (fromList == null) {
			return null;
		}
		List<T> list = new ArrayList<>();
		for (F f : fromList) {
			list.add(assemble(f));
		}
		return list;
	}

}
