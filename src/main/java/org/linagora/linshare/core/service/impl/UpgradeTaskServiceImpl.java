/*
 * LinShare is an open source filesharing software, part of the LinPKI software
 * suite, developed by Linagora.
 * 
 * Copyright (C) 2017 LINAGORA
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version, provided you comply with the Additional Terms applicable for
 * LinShare software by Linagora pursuant to Section 7 of the GNU Affero General
 * Public License, subsections (b), (c), and (e), pursuant to which you must
 * notably (i) retain the display of the “LinShare™” trademark/logo at the top
 * of the interface window, the display of the “You are using the Open Source
 * and free version of LinShare™, powered by Linagora © 2009–2017. Contribute to
 * Linshare R&D by subscribing to an Enterprise offer!” infobox and in the
 * e-mails sent with the Program, (ii) retain all hypertext links between
 * LinShare and linshare.org, between linagora.com and Linagora, and (iii)
 * refrain from infringing Linagora intellectual property rights over its
 * trademarks and commercial brands. Other Additional Terms apply, see
 * <http://www.linagora.com/licenses/> for more details.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Affero General Public License and
 * its applicable Additional Terms for LinShare along with this program. If not,
 * see <http://www.gnu.org/licenses/> for the GNU Affero General Public License
 * version 3 and <http://www.linagora.com/licenses/> for the Additional Terms
 * applicable to LinShare software.
 */
package org.linagora.linshare.core.service.impl;

import java.util.List;

import org.linagora.linshare.core.business.service.UpgradeTaskBusinessService;
import org.linagora.linshare.core.domain.constants.UpgradeTaskType;
import org.linagora.linshare.core.domain.entities.Account;
import org.linagora.linshare.core.domain.entities.UpgradeTask;
import org.linagora.linshare.core.exception.BusinessErrorCode;
import org.linagora.linshare.core.exception.BusinessException;
import org.linagora.linshare.core.service.UpgradeTaskService;

public class UpgradeTaskServiceImpl extends GenericAdminServiceImpl implements UpgradeTaskService {

	protected UpgradeTaskBusinessService businessService;

	public UpgradeTaskServiceImpl(UpgradeTaskBusinessService businessService) {
		super();
		this.businessService = businessService;
	}

	@Override
	public UpgradeTask find(Account actor, UpgradeTaskType identifier) {
		preChecks(actor);
		UpgradeTask task = businessService.find(identifier);
		if (task == null) {
			throw new BusinessException(BusinessErrorCode.UPGRADE_TASK_NOT_FOUND, "Can not find upgrade task  : " + identifier );
		}
		return task;
	}

	@Override
	public List<UpgradeTask> findAll(Account actor) {
		preChecks(actor);
		return businessService.findAll();
	}

	@Override
	public UpgradeTask update(Account actor, UpgradeTask upgradeTask) throws BusinessException {
		preChecks(actor);
		return businessService.update(upgradeTask);
	}

	@Override
	protected void preChecks(Account actor) {
		super.preChecks(actor);
		if (!actor.isRoot()) {
			throw new BusinessException(BusinessErrorCode.UPGRADE_TASK_FORBIDDEN, "You are not allowed to use this service");
		}
	}
}
