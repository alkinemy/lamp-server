package lamp.server.aladin.common.assembler;

import lamp.server.aladin.common.assembler.*;

import java.util.List;

public interface ListAssembler<F, T> extends lamp.server.aladin.common.assembler.Assembler<F, T> {

	List<T> assemble(List<F> f);

}
