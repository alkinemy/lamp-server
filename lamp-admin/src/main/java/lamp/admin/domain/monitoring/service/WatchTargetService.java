package lamp.admin.domain.monitoring.service;

import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.monitoring.model.WatchTarget;
import lamp.admin.domain.monitoring.model.WatchTargetCreateForm;
import lamp.admin.domain.monitoring.model.WatchTargetDto;
import lamp.admin.domain.monitoring.model.WatchTargetUpdateForm;
import lamp.admin.domain.monitoring.repository.WatchTargetRepository;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WatchTargetService {

	@Autowired
	private WatchTargetRepository watchTargetRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public Collection<WatchTarget> getWatchTargetList() {
		return watchTargetRepository.findAll();
	}

	public List<WatchTargetDto> getWatchTargetDtoList() {
		return smartAssembler.assemble(watchTargetRepository.findAll(), WatchTargetDto.class);
	}

	public Page<WatchTargetDto> getWatchTargetDtoList(Pageable pageable) {
		Page<WatchTarget> page = watchTargetRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, WatchTargetDto.class);
	}


	public WatchTargetDto getWatchTargetDto(String id) {
		WatchTarget WatchTarget = getWatchTarget(id);
		return smartAssembler.assemble(WatchTarget, WatchTargetDto.class);
	}

	public WatchTarget getWatchTarget(String id) {
		return getWatchTargetOptional(id).orElseThrow(() -> Exceptions.newException(LampErrorCode.WATCH_TARGET_NOT_FOUND, id));
	}

	public Optional<WatchTarget> getWatchTargetOptional(String id) {
		WatchTarget WatchTarget = watchTargetRepository.findOne(id);
		return Optional.ofNullable(WatchTarget);
	}


	@Transactional
	public WatchTarget insertWatchTarget(WatchTargetCreateForm editForm) {
		WatchTarget WatchTarget = smartAssembler.assemble(editForm, WatchTarget.class);
		return insertWatchTarget(WatchTarget);
	}

	@Transactional
	public WatchTarget insertWatchTarget(WatchTarget WatchTarget) {
		return watchTargetRepository.save(WatchTarget);
	}

	public WatchTargetUpdateForm getWatchTargetUpdateForm(String id) {
		WatchTarget WatchTarget = getWatchTarget(id);
		return smartAssembler.assemble(WatchTarget, WatchTargetUpdateForm.class);
	}

	@Transactional
	public WatchTarget updateWatchTarget(String id, WatchTargetUpdateForm editForm) {
		WatchTarget WatchTarget = getWatchTarget(id);
		smartAssembler.populate(editForm, WatchTarget);
		return watchTargetRepository.save(WatchTarget);
	}

	@Transactional
	public void deleteWatchTarget(String id) {
		WatchTarget WatchTarget = getWatchTarget(id);
		watchTargetRepository.delete(WatchTarget);
	}
}
