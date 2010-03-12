/*
 *    This file is part of Linshare.
 *
 *   Linshare is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of
 *   the License, or (at your option) any later version.
 *
 *   Linshare is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public
 *   License along with Foobar.  If not, see
 *                                    <http://www.gnu.org/licenses/>.
 *
 *   (c) 2008 Groupe Linagora - http://linagora.org
 *
*/
package org.linagora.linShare.core.Facade.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.linagora.linShare.core.Facade.ShareFacade;
import org.linagora.linShare.core.domain.LogAction;
import org.linagora.linShare.core.domain.entities.Contact;
import org.linagora.linShare.core.domain.entities.Document;
import org.linagora.linShare.core.domain.entities.SecuredUrl;
import org.linagora.linShare.core.domain.entities.Share;
import org.linagora.linShare.core.domain.entities.ShareLogEntry;
import org.linagora.linShare.core.domain.entities.User;
import org.linagora.linShare.core.domain.entities.UserType;
import org.linagora.linShare.core.domain.objects.SuccessesAndFailsItems;
import org.linagora.linShare.core.domain.transformers.impl.DocumentTransformer;
import org.linagora.linShare.core.domain.transformers.impl.ShareTransformer;
import org.linagora.linShare.core.domain.vo.DocumentVo;
import org.linagora.linShare.core.domain.vo.GroupVo;
import org.linagora.linShare.core.domain.vo.ShareDocumentVo;
import org.linagora.linShare.core.domain.vo.UserVo;
import org.linagora.linShare.core.exception.BusinessException;
import org.linagora.linShare.core.exception.TechnicalErrorCode;
import org.linagora.linShare.core.exception.TechnicalException;
import org.linagora.linShare.core.repository.DocumentRepository;
import org.linagora.linShare.core.repository.LogEntryRepository;
import org.linagora.linShare.core.repository.UserRepository;
import org.linagora.linShare.core.service.DocumentService;
import org.linagora.linShare.core.service.NotifierService;
import org.linagora.linShare.core.service.ShareService;
import org.linagora.linShare.core.service.UserService;
import org.linagora.linShare.core.utils.FileUtils;
import org.linagora.linShare.view.tapestry.services.Templating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShareFacadeImpl implements ShareFacade {

	private static final Logger logger = LoggerFactory.getLogger(ShareFacadeImpl.class);
	
	private final ShareService shareService;
	
	private final DocumentTransformer documentTransformer;
	
	private final ShareTransformer shareTransformer;
	
	private final UserRepository<User> userRepository;

	private final DocumentRepository documentRepository;
	
	private final NotifierService notifierService;
	
	private final UserService userService;

    private final DocumentService documentService;
    
    
    private final Templating templating;
    
    
	
	public ShareFacadeImpl(
			final ShareService shareService,
			final DocumentTransformer documentTransformer,
			final ShareTransformer shareTransformer,
			final UserRepository<User> userRepository,
			final DocumentRepository documentRepository,
			final NotifierService mailNotifierService,
			final UserService userService,
            final DocumentService documentService,
            final Templating templating) {
		super();
		this.shareService = shareService;
		this.documentTransformer = documentTransformer;
		this.shareTransformer = shareTransformer;
		this.userRepository = userRepository;
		this.documentRepository = documentRepository;
		this.notifierService=mailNotifierService;
		this.userService = userService;
        this.documentService = documentService;
        this.templating=templating;
	}

	
	public SuccessesAndFailsItems<ShareDocumentVo> createSharing(UserVo owner, List<DocumentVo> documents,
			List<UserVo> recipients, String comment, Calendar expirationDate) throws BusinessException {
		
		List<User> recipientsList = new ArrayList<User>();
		
		for (UserVo userVo : recipients) {
			try {
				recipientsList.add(userService.findAndCreateUser(userVo.getMail()));
			} catch (BusinessException e) {
				logger.error("Could not find the recipient " + userVo.getMail() + " in the database nor in the ldap");
				throw e;
			}
		}
		
		List<Document> docList = new ArrayList<Document>();
		for (DocumentVo documentVo : documents) {
			docList.add(documentRepository.findById(documentVo.getIdentifier()));
		}
		SuccessesAndFailsItems<Share> successAndFails = shareService.shareDocumentsToUser(docList, 
				userRepository.findByLogin(owner.getLogin()),
				recipientsList,
				comment,
				expirationDate);
		
		
		SuccessesAndFailsItems<ShareDocumentVo> results = new SuccessesAndFailsItems<ShareDocumentVo>();
		results.setFailsItem(shareTransformer.disassembleList(successAndFails.getFailsItem()));
		results.setSuccessesItem(shareTransformer.disassembleList(successAndFails.getSuccessesItem()));
		
		return results;
	}

	public SuccessesAndFailsItems<ShareDocumentVo> createSharingWithMail(
			UserVo owner, List<DocumentVo> documents, List<UserVo> recipients,
			String comment, String messageInternal, String messageInternalTxt,
			String messageGuest, String messageGuestTxt, String subject,
			Calendar expirationDate) throws BusinessException {
		
		SuccessesAndFailsItems<ShareDocumentVo> result = createSharing(owner,documents,recipients, comment, expirationDate);
		
		//Sending the mails
		List<UserVo> successfullRecipient = new ArrayList<UserVo>();
		for (ShareDocumentVo successfullSharing : result.getSuccessesItem()) {
			if (!successfullRecipient.contains(successfullSharing.getReceiver())) {
				successfullRecipient.add(successfullSharing.getReceiver());
			}
			
		}
		for(UserVo userVo : successfullRecipient){
			logger.debug("Sending sharing notification to user " + userVo.getLogin());
			if (userVo.isGuest()) {
				notifierService.sendNotification(owner.getMail(),userVo.getMail(), subject, messageGuest,messageGuestTxt);
			} else {
				notifierService.sendNotification(owner.getMail(),userVo.getMail(), subject, messageInternal,messageInternalTxt);
			}
		}
		return result;
	}
	

	public List<ShareDocumentVo> getAllSharingReceivedByUser(UserVo recipient) {
		User userRecipient = userRepository.findByLogin(recipient.getLogin());
		if (userRecipient==null) {
			throw new TechnicalException(TechnicalErrorCode.USER_INCOHERENCE, "Could not find the user");
		}
		
		return shareTransformer.disassembleList(new ArrayList<Share>(userRecipient.getReceivedShares()));		
	}

	public List<Share> getSharingsByUserAndFile(UserVo sender, DocumentVo document) {
		User userSender = userRepository.findByLogin(sender.getLogin());
		if (userSender==null) {
			throw new TechnicalException(TechnicalErrorCode.USER_INCOHERENCE, "Could not find the user");
		}
		
		Set<Share> shares = shareService.getSentSharesByUser(userSender);

		List<Share> sharingsOfDocument = new ArrayList<Share>();
		for (Share share : shares) {			
			if (share.getDocument().getIdentifier().equalsIgnoreCase(document.getIdentifier())) {
				sharingsOfDocument.add(share);
			}
		}
		
		return sharingsOfDocument;
	}

	public void deleteSharing(ShareDocumentVo share, UserVo actor) throws BusinessException {
		User actorUser = userRepository.findByLogin(actor.getLogin());
		
		Share shareToDelete = shareTransformer.assemble(share);
	
		shareService.deleteShare(shareToDelete, actorUser);
		
		shareService.refreshShareAttributeOfDoc(shareToDelete.getDocument());
		
	}

    public void createLocalCopy(ShareDocumentVo shareDocumentVo, UserVo userVo) throws BusinessException {
        Share share = shareTransformer.assemble(shareDocumentVo);
		User user = userRepository.findByLogin(userVo.getLogin());
        
        // create a copy of the document :
        documentService.duplicateDocument(share.getDocument(), user);
		
        // remove the share :
        shareService.removeReceivedShareForUser(share, user, user);
        
        //log the copy
        shareService.logLocalCopyOfDocument(share, user);
		
        // no more sharing of this file?
		shareService.refreshShareAttributeOfDoc(share.getDocument());
    }


    public SuccessesAndFailsItems<ShareDocumentVo> createSharingWithMailUsingRecipientsEmail(
			UserVo owner, List<DocumentVo> documents,
			List<String> recipientsEmail, String comment, String subject,
			String linShareUrlInternal, String linShareUrlAnonymous, boolean secureSharing,
			String sharedTemplateContent, String sharedTemplateContentTxt,
			String passwordSharedTemplateContent,
			String passwordSharedTemplateContentTxt)
			throws BusinessException {
		return createSharingWithMailUsingRecipientsEmailAndExpiryDate(owner, documents, recipientsEmail, comment, subject, linShareUrlInternal, linShareUrlAnonymous, secureSharing, sharedTemplateContent, sharedTemplateContentTxt, passwordSharedTemplateContent, passwordSharedTemplateContentTxt, null);
	}


    public SuccessesAndFailsItems<ShareDocumentVo> createSharingWithMailUsingRecipientsEmailAndExpiryDate(
			UserVo owner, List<DocumentVo> documents,
			List<String> recipientsEmail, String comment, String subject,
			String linShareUrlInternal, String linShareUrlAnonymous, boolean secureSharing,
			String sharedTemplateContent, String sharedTemplateContentTxt,
			String passwordSharedTemplateContent,
			String passwordSharedTemplateContentTxt, Calendar expiryDateSelected)
			throws BusinessException {
		User u = null;
		
		List<UserVo> knownRecipients = new ArrayList<UserVo>();
		List<Contact> unKnownRecipientsEmail = new ArrayList<Contact>();
		
		
		//Setting parameters and values for supply the template.
		Map<String,String> templateParams=new HashMap<String, String>();
		
		//TEMPLATE sharedTemplateContent
		templateParams.put("${message}", comment);
		templateParams.put("${firstName}", owner.getFirstName());
		templateParams.put("${lastName}", owner.getLastName());
		templateParams.put("${number}", new Integer(documents.size()).toString());
		templateParams.put("${url}", linShareUrlInternal); //modified later for anonymous user and guest
		templateParams.put("${urlparam}", "");
		
		StringBuffer names = new StringBuffer();
		StringBuffer namesTxt = new StringBuffer();
		
		for (DocumentVo oneDoc : documents) {
			names.append("<li>"+oneDoc.getFileName()+"</li>");
			namesTxt.append(oneDoc.getFileName()+"\n");
		}
		
		templateParams.put("${documentNames}", names.toString());
		templateParams.put("${documentNamesTxt}", namesTxt.toString());
		
		String messageForInternalUser = templating.getMessage(sharedTemplateContent, templateParams);
		String messageForInternalUserTxt = templating.getMessage(sharedTemplateContentTxt, templateParams);
		templateParams.put("${url}", linShareUrlAnonymous);
		String messageForGuestUser = templating.getMessage(sharedTemplateContent, templateParams);
		String messageForGuestUserTxt = templating.getMessage(sharedTemplateContentTxt, templateParams);
		
		// find known and unknown recipients of the share
		for (String mail : recipientsEmail) {
			u = userService.findUser(mail);
			if(u!=null){
				knownRecipients.add(new UserVo(u));}
			else {
				unKnownRecipientsEmail.add(new Contact(mail));
			}
		}
		
		if(unKnownRecipientsEmail.size()>0){ //secureUrl for these users (no need to have an account to activate sharing)
			
			List<Document> docList = new ArrayList<Document>();
			for (DocumentVo documentVo : documents) {
				docList.add(documentRepository.findById(documentVo.getIdentifier()));
			}
			
			SecuredUrl securedUrl =  null;
			String messageForSecureUrl = null;
			String messageForSecureUrlTxt = null;
			String password = null;
			
			if(secureSharing) {
				//generate password for this sharing 
				password = userService.generatePassword();
				//set the password associated with this secured url in mail
				templateParams.put("${password}", password);
			} 
			
			//password is null for unprotected secured url
			securedUrl = shareService.shareDocumentsWithSecuredUrlToUser(owner, docList, password, unKnownRecipientsEmail, expiryDateSelected);
			
			
			//compose the secured url to give in mail
			StringBuffer httpUrlBase = new StringBuffer();
			httpUrlBase.append(linShareUrlAnonymous);
			if(!linShareUrlAnonymous.endsWith("/")) httpUrlBase.append("/");
			httpUrlBase.append(securedUrl.getUrlPath());
			if(!securedUrl.getUrlPath().endsWith("/")) httpUrlBase.append("/");
			httpUrlBase.append(securedUrl.getAlea());
			
			//securedUrl must be ended with a "/" if no parameter (see urlparam)
			templateParams.put("${url}", httpUrlBase.toString());
			
			
			for (Contact oneContact : unKnownRecipientsEmail) {
				
				//give email as a parameter, useful to quickly know who is here
				templateParams.put("${urlparam}", "?email=" + oneContact.getMail());
				
				//fill the template with given values 
				if(secureSharing) {
					messageForSecureUrl = templating.getMessage(passwordSharedTemplateContent, templateParams);
					messageForSecureUrlTxt = templating.getMessage(passwordSharedTemplateContentTxt, templateParams);
				} else {
					messageForSecureUrl = templating.getMessage(sharedTemplateContent, templateParams);
					messageForSecureUrlTxt = templating.getMessage(sharedTemplateContentTxt, templateParams);
				}
				
				notifierService.sendNotification(owner.getMail(),oneContact.getMail(), subject, messageForSecureUrl,messageForSecureUrlTxt);
			}
			
		}
		
		//keep old method to share with user referenced in db
		return createSharingWithMail(owner, documents, knownRecipients, comment, messageForInternalUser, messageForInternalUserTxt,messageForGuestUser, messageForGuestUserTxt, subject, expiryDateSelected);
	}
    
    
    public void sendDownloadNotification(ShareDocumentVo sharedDocument, UserVo currentUser, String subject, String downloadTemplateContent,String downloadTemplateContentTxt) {
    	//Setting parameters and values for supply the template.
		Map<String,String> templateParams=new HashMap<String, String>();
		
		UserVo owner = sharedDocument.getSender();
		
		//TEMPLATE sharedTemplateContent
		templateParams.put("${firstName}", owner.getFirstName());
		templateParams.put("${lastName}", owner.getLastName());

		//currentUser who is doing the download is the recipient
		templateParams.put("${recipientFirstName}", currentUser.getFirstName());
		templateParams.put("${recipientLastName}", currentUser.getLastName());
		
		templateParams.put("${documentNames}", "<li>" + sharedDocument.getFileName() + "</li>") ;
		templateParams.put("${documentNamesTxt}", sharedDocument.getFileName());
		
		String messageForDownloadedTemplateContent = templating.getMessage(downloadTemplateContent, templateParams);
		String messageForDownloadedTemplateContentTxt = templating.getMessage(downloadTemplateContentTxt, templateParams);
		notifierService.sendNotification(currentUser.getMail(),owner.getMail(), subject, messageForDownloadedTemplateContent, messageForDownloadedTemplateContentTxt);
    }
    
    
    public void sendSharedUpdateDocNotification(DocumentVo currentDoc, UserVo currentUser, String url, String urlInternal, String fileSizeTxt,String oldFileName, String subject, String sharedUpdateDocTemplateContent,String sharedUpdateDocTemplateContentTxt) throws BusinessException{
    	
    	//Setting parameters and values for supply the template.
		Map<String,String> templateParams=new HashMap<String, String>();
		
		//TEMPLATE sharedUpdateDocTemplateContent
		templateParams.put("${firstName}", currentUser.getFirstName());
		templateParams.put("${lastName}", currentUser.getLastName());
		templateParams.put("${fileOldName}", oldFileName);
		
		templateParams.put("${fileName}", currentDoc.getFileName());
		templateParams.put("${fileSize}", fileSizeTxt)  ;

		templateParams.put("${mimeType}", currentDoc.getType());
		
		//process message
		String messageForsharedUpdateDocTemplateContent = null;
		String messageForsharedUpdateDocTemplateContentTxt = null;

    	
    	//1) share with secured url, notification to users.
    	
    	Document doc = documentRepository.findById(currentDoc.getIdentifier());
    	
    	List<SecuredUrl> urls = shareService.getSecureUrlLinkedToDocument(doc);
    	
    	String urlBase = url;
    	if (!(urlBase.charAt(urlBase.length()-1)=='/')) {
    		urlBase = urlBase.concat("/");
		}
    	for (SecuredUrl securedUrl : urls) {
			List<Contact> recipients = securedUrl.getRecipients();
			String urlDownload = urlBase.concat(securedUrl.getUrlPath()+"/");
			urlDownload = urlDownload.concat(securedUrl.getAlea());
			
			templateParams.put("${url}", urlDownload);
			
    		for (Contact contact : recipients) {
    			templateParams.put("${urlparam}", "?email="+contact.getMail());
    			
    			messageForsharedUpdateDocTemplateContent = templating.getMessage(sharedUpdateDocTemplateContent, templateParams);
    			messageForsharedUpdateDocTemplateContentTxt = templating.getMessage(sharedUpdateDocTemplateContentTxt, templateParams);
    			
    			notifierService.sendNotification(currentUser.getMail(),contact.getMail(), subject, messageForsharedUpdateDocTemplateContent, messageForsharedUpdateDocTemplateContentTxt);
			}
		}

		templateParams.put("${urlparam}", "");
		
    	//2) normal share, notification to guest and internal user
		List<Share> listShare = shareService.getSharesLinkedToDocument(doc);
		
		for (Share share : listShare) {
			if (share.getReceiver().getUserType().equals(UserType.GUEST)) {
				templateParams.put("${url}", url);
			} else {
				templateParams.put("${url}", urlInternal);
			}
			
			messageForsharedUpdateDocTemplateContent = templating.getMessage(sharedUpdateDocTemplateContent, templateParams);
			messageForsharedUpdateDocTemplateContentTxt = templating.getMessage(sharedUpdateDocTemplateContentTxt, templateParams);
			
			notifierService.sendNotification(currentUser.getMail(),share.getReceiver().getMail(), subject, messageForsharedUpdateDocTemplateContent, messageForsharedUpdateDocTemplateContentTxt);
		}
    	
    }
    
    public SuccessesAndFailsItems<ShareDocumentVo> createSharingWithGroups(
    		UserVo owner, List<DocumentVo> documents, List<GroupVo> recipients)
    		throws BusinessException {
    	List<User> groupUserObjectsList = new ArrayList<User>();
		
		for (GroupVo groupVo : recipients) {
			try {
				groupUserObjectsList.add(userService.findAndCreateUser(groupVo.getGroupLogin()));
			} catch (BusinessException e) {
				logger.error("Could not find the recipient " + groupVo.getGroupLogin() + " in the database");
				throw e;
			}
		}

		
		List<Document> docList = new ArrayList<Document>();
		for (DocumentVo documentVo : documents) {
			docList.add(documentRepository.findById(documentVo.getIdentifier()));
		}
		Calendar expiryDate = GregorianCalendar.getInstance();
		expiryDate.add(Calendar.YEAR, 3);
		SuccessesAndFailsItems<Share> successAndFails = shareService.shareDocumentsToUser(docList, 
				userRepository.findByLogin(owner.getLogin()),
				groupUserObjectsList, "", expiryDate);
		
		
		SuccessesAndFailsItems<ShareDocumentVo> results = new SuccessesAndFailsItems<ShareDocumentVo>();
		results.setFailsItem(shareTransformer.disassembleList(successAndFails.getFailsItem()));
		results.setSuccessesItem(shareTransformer.disassembleList(successAndFails.getSuccessesItem()));
		
		return results;
    }

}
