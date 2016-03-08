package lamp.common.utils.assembler;

public interface Populater<S, T> {

	void populate(S source, T target);

}
