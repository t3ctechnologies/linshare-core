/*
 * LinShare is an open source filesharing software, part of the LinPKI software
 * suite, developed by Linagora.
 * 
 * Copyright (C) 2014 LINAGORA
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version, provided you comply with the Additional Terms applicable for
 * LinShare software by Linagora pursuant to Section 7 of the GNU Affero General
 * Public License, subsections (b), (c), and (e), pursuant to which you must
 * notably (i) retain the display of the “LinShare™” trademark/logo at the top
 * of the interface window, the display of the “You are using the Open Source
 * and free version of LinShare™, powered by Linagora © 2009–2014. Contribute to
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
package org.linagora.linshare.view.tapestry.pages.uploadrequest;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.SelectModelFactory;
import org.linagora.linshare.core.domain.vo.UploadRequestTemplateVo;
import org.linagora.linshare.core.domain.vo.UploadRequestVo;
import org.linagora.linshare.core.domain.vo.UserVo;
import org.linagora.linshare.core.exception.BusinessException;
import org.linagora.linshare.core.facade.FunctionalityFacade;
import org.linagora.linshare.core.facade.UploadRequestFacade;
import org.linagora.linshare.view.tapestry.beans.ShareSessionObjects;
import org.linagora.linshare.view.tapestry.services.BusinessMessagesManagementService;
import org.slf4j.Logger;

public class Create {

	/*
	 * Tapestry properties
	 */

	@SessionState
	@Property
	private ShareSessionObjects shareSessionObjects;

	@SessionState
	@Property
	private UserVo userVo;

	@Property
	private UploadRequestVo current;

	@Property
	private UploadRequestTemplateVo selected;

	@InjectComponent
	private Zone reloadZone;

	@InjectPage
	private Content content;

	/*
	 * Injected beans
	 */

	@Inject
	private Logger logger;

	@Inject
	private BeanModelSource beanModelSource;

	@Inject
	private Messages messages;

	@Inject
	private BusinessMessagesManagementService businessMessagesManagementService;

	@Inject
	private FunctionalityFacade functionalityFacade;

	@Inject
	private UploadRequestFacade uploadRequestFacade;

	public Object onActivate() {
		if (!functionalityFacade.isEnableUploadRequest(userVo
				.getDomainIdentifier())) {
			return org.linagora.linshare.view.tapestry.pages.Index.class;
		}
		try {
			BeanModel<UploadRequestVo> model = beanModelSource.createEditModel(
					UploadRequestVo.class, messages);
			current = uploadRequestFacade.getDefaultValue(userVo, model);
		} catch (BusinessException e) {
			logger.error("Cannot get default upload request value for user "
					+ userVo.getLsUuid());
			businessMessagesManagementService.notify(e);
			return Index.class;
		}
		return null;
	}

	@Log
	public Object onSuccess() throws BusinessException {
		uploadRequestFacade.createRequest(userVo, current);
		return Index.class;
	}

	@Log
	public Object onCanceled() throws BusinessException {
		return Index.class;
	}

	@Log
	public Object onValueChanged() {
		current = selected.toValue();
		return reloadZone;
	}

	/*
	 * Models + ValueEncoder
	 */

	public BeanModel<UploadRequestVo> getModel() {
		return current.getModel();
	}

	public List<UploadRequestTemplateVo> getSelectModel()
			throws BusinessException {
		return uploadRequestFacade.findAllTemplates(userVo);
	}

	public ValueEncoder<UploadRequestTemplateVo> getUploadRequestModelEncoder() {
		return new ValueEncoder<UploadRequestTemplateVo>() {
			@Override
			public String toClient(UploadRequestTemplateVo value) {
				return value.getUuid();
			}
 
			@Override
			public UploadRequestTemplateVo toValue(String id) {
				return uploadRequestFacade.findTemplateByUuid(id);
			}
		};
	}

	/*
	 * Exception Handling
	 */

	public Object onException(Throwable cause) {
		shareSessionObjects.addError(messages.get("global.exception.message"));
		logger.error(cause.getMessage());
		cause.printStackTrace();
		return this;
	}
}
