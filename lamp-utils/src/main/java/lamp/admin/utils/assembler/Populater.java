package lamp.admin.utils.assembler;

public interface Populater<S, T> {

	void populate(S source, T target);

}
