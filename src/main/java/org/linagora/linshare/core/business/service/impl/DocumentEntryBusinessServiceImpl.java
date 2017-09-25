/*
 * LinShare is an open source filesharing software, part of the LinPKI software
 * suite, developed by Linagora.
 * 
 * Copyright (C) 2015-2016 LINAGORA
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version, provided you comply with the Additional Terms applicable for
 * LinShare software by Linagora pursuant to Section 7 of the GNU Affero General
 * Public License, subsections (b), (c), and (e), pursuant to which you must
 * notably (i) retain the display of the “LinShare™” trademark/logo at the top
 * of the interface window, the display of the “You are using the Open Source
 * and free version of LinShare™, powered by Linagora © 2009–2015. Contribute to
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
package org.linagora.linshare.core.business.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.cxf.helpers.IOUtils;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampResponse;
import org.linagora.LinThumbnail.FileResource;
import org.linagora.LinThumbnail.FileResourceFactory;
import org.linagora.LinThumbnail.utils.Constants;
import org.linagora.LinThumbnail.utils.ImageUtils;
import org.linagora.linshare.core.business.service.DocumentEntryBusinessService;
import org.linagora.linshare.core.business.service.SignatureBusinessService;
import org.linagora.linshare.core.business.service.UploadRequestEntryBusinessService;
import org.linagora.linshare.core.dao.FileDataStore;
import org.linagora.linshare.core.domain.constants.FileMetaDataKind;
import org.linagora.linshare.core.domain.entities.Account;
import org.linagora.linshare.core.domain.entities.Document;
import org.linagora.linshare.core.domain.entities.DocumentEntry;
import org.linagora.linshare.core.domain.entities.ShareEntry;
import org.linagora.linshare.core.domain.entities.Signature;
import org.linagora.linshare.core.domain.entities.Thread;
import org.linagora.linshare.core.domain.entities.ThreadEntry;
import org.linagora.linshare.core.domain.entities.UploadRequestEntry;
import org.linagora.linshare.core.domain.objects.FileMetaData;
import org.linagora.linshare.core.exception.BusinessErrorCode;
import org.linagora.linshare.core.exception.BusinessException;
import org.linagora.linshare.core.exception.TechnicalErrorCode;
import org.linagora.linshare.core.exception.TechnicalException;
import org.linagora.linshare.core.repository.DocumentEntryRepository;
import org.linagora.linshare.core.repository.DocumentRepository;
import org.linagora.linshare.core.service.TimeStampingService;
import org.linagora.linshare.core.utils.AESCrypt;
import org.linagora.linshare.mongo.entities.WorkGroupDocument;
import org.linagora.linshare.mongo.entities.WorkGroupNode;
import org.linagora.linshare.mongo.entities.mto.AccountMto;
import org.linagora.linshare.mongo.repository.WorkGroupNodeMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentEntryBusinessServiceImpl implements DocumentEntryBusinessService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentEntryBusinessServiceImpl.class);

	private final FileDataStore fileDataStore;
	private final TimeStampingService timeStampingService;
	private final DocumentEntryRepository documentEntryRepository;
	private final DocumentRepository documentRepository;
	private final SignatureBusinessService signatureBusinessService;
	private final UploadRequestEntryBusinessService uploadRequestEntryBusinessService;
	private final boolean thumbEnabled;
	private final boolean pdfThumbEnabled;
	private final boolean deduplication;
	protected final WorkGroupNodeMongoRepository repository;

	public DocumentEntryBusinessServiceImpl(
			final FileDataStore fileSystemDao,
			final TimeStampingService timeStampingService,
			final DocumentEntryRepository documentEntryRepository,
			final DocumentRepository documentRepository,
			final SignatureBusinessService signatureBusinessService,
			final UploadRequestEntryBusinessService uploadRequestEntryBusinessService,
			final boolean thumbEnabled,
			final boolean pdfThumbEnabled,
			final boolean deduplication,
			final WorkGroupNodeMongoRepository repository) {
		super();
		this.fileDataStore = fileSystemDao;
		this.timeStampingService = timeStampingService;
		this.documentEntryRepository = documentEntryRepository;
		this.documentRepository = documentRepository;
		this.signatureBusinessService = signatureBusinessService;
		this.uploadRequestEntryBusinessService = uploadRequestEntryBusinessService;
		this.thumbEnabled = thumbEnabled;
		this.pdfThumbEnabled = pdfThumbEnabled;
		this.deduplication = deduplication;
		this.repository = repository;
	}

	@Override
	public DocumentEntry createDocumentEntry(Account owner, File myFile,
			Long size, String fileName, String comment,
			Boolean checkIfIsCiphered, String timeStampingUrl, String mimeType,
			Calendar expirationDate, boolean isFromCmis, String metadata) throws BusinessException {
		// add an entry for the file in DB
		DocumentEntry entity = null;
		try {
			Document document = createDocument(owner, myFile, size, fileName, timeStampingUrl, mimeType);
			if (comment == null)
				comment = "";
			DocumentEntry docEntry = new DocumentEntry(owner, fileName, comment, document);
			// We need to set an expiration date in case of file cleaner activation.
			docEntry.setExpirationDate(expirationDate);
			docEntry.setMetaData(metadata);

			//aes encrypt ? check headers
			if(checkIfIsCiphered) {
				docEntry.setCiphered(checkIfFileIsCiphered(fileName, myFile));
			}
			docEntry.setCmisSync(isFromCmis);
			entity = documentEntryRepository.create(docEntry);
			owner.getEntries().add(entity);
		} catch (BusinessException e) {
			logger.error("Could not add  " + fileName + " to user " + owner.getLsUuid() + ", reason : ", e);
			throw new TechnicalException(TechnicalErrorCode.COULD_NOT_INSERT_DOCUMENT, "couldn't register the file in the database");
		}
		return entity;
	}

	@Override
	public byte[] getTimeStamp(String fileName, File tempFile, String timeStampingUrl) throws BusinessException {
		if(timeStampingUrl == null) {
			return null;
		}

		byte[] timestampToken = null;
		FileInputStream fis = null;

		try{
			fis = new FileInputStream(tempFile);
			TimeStampResponse resp =  timeStampingService.getTimeStamp(timeStampingUrl, fis);
			timestampToken  = resp.getEncoded();
		} catch (TSPException e) {
			logger.error(e.toString());
			throw new BusinessException(BusinessErrorCode.FILE_TIMESTAMP_NOT_COMPUTED,"TimeStamp on file is not computed", new String[] {fileName});
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
			throw new BusinessException(BusinessErrorCode.FILE_TIMESTAMP_NOT_COMPUTED,"TimeStamp on file is not computed", new String[] {fileName});
		} catch (IOException e) {
			logger.error(e.toString());
			throw new BusinessException(BusinessErrorCode.FILE_TIMESTAMP_NOT_COMPUTED,"TimeStamp on file is not computed", new String[] {fileName});
		} catch (URISyntaxException e) {
			logger.error(e.toString());
			throw new BusinessException(BusinessErrorCode.FILE_TIMESTAMP_WRONG_TSA_URL,"The Tsa Url is empty or invalid", new String[] {fileName});
		} finally {
			try {
				if (fis != null)
					fis.close();
				fis = null;
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}
		return timestampToken ;
	}

	@Override
	public InputStream getDocumentThumbnailStream(DocumentEntry entry) {
		Document doc = entry.getDocument();
		return getDocumentThumbnailStream(doc);
	}

	private InputStream getDocumentThumbnailStream(Document doc) {
		String thmbUuid = doc.getThmbUuid();
		if (thmbUuid != null && thmbUuid.length()>0) {
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.THUMBNAIL, doc);
			InputStream stream = null;
			try {
				stream = fileDataStore.get(metadata);
			} catch (TechnicalException ex) {
				logger.debug(ex.getMessage(), ex);
			}
			return stream;
		}
		return null;
	}

	@Override
	public InputStream getThreadEntryThumbnailStream(WorkGroupDocument entry) {
		Document doc = documentRepository.findByUuid(entry.getDocumentUuid());
		String thmbUuid = doc.getThmbUuid();
		if (thmbUuid != null && thmbUuid.length()>0) {
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.THUMBNAIL, doc);
			InputStream stream = null;
			try {
				stream = fileDataStore.get(metadata);
			} catch (TechnicalException ex) {
				logger.debug(ex.getMessage(), ex);
			}
			return stream;
		}
		return null;
	}

	@Override
	public InputStream getDocumentStream(DocumentEntry entry) {
		String UUID = entry.getDocument().getUuid();
		if (UUID!=null && UUID.length()>0) {
			logger.debug("retrieve from jackrabbity : " + UUID);
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.DATA, entry.getDocument());
			InputStream stream = fileDataStore.get(metadata);
			return stream;
		}
		return null;
	}

	@Override
	public DocumentEntry find(String uuid) {
		return documentEntryRepository.findById(uuid);
	}

	@Override
	public List<DocumentEntry> findAllMyDocumentEntries(Account owner) {
		return documentEntryRepository.findAllMyDocumentEntries(owner);
	}

	@Override
	public DocumentEntry renameDocumentEntry(DocumentEntry entry, String newName) throws BusinessException {
		entry.setName(newName);
		entry.setModificationDate(new GregorianCalendar());
		return documentEntryRepository.update(entry);
	}

	@Override
	public DocumentEntry updateFileProperties(DocumentEntry entry, String newName, String fileComment, String meta) throws BusinessException {
		entry.setBusinessName(newName);
		entry.setBusinessComment(fileComment);
		entry.setBusinessMetaData(meta);
		entry.setModificationDate(new GregorianCalendar());
		return documentEntryRepository.update(entry);
	}

	@Override
	public DocumentEntry updateDocumentEntry(Account owner,
			DocumentEntry docEntry, File myFile, Long size, String fileName,
			Boolean checkIfIsCiphered, String timeStampingUrl, String mimeType,
			Calendar expirationDate) throws BusinessException {

		String sha256sum = SHA256CheckSumFileStream(myFile);
		FileMetaData metadata = null;
		FileMetaData metadataThmb = null;
		// want a timestamp on doc ? if timeStamping url is null, time stamp will be null
		try {
			Document document = null;
			List<Document> documents = documentRepository.findBySha256Sum(sha256sum);
			if (documents.isEmpty()) {
				// Storing file
				metadata = new FileMetaData(FileMetaDataKind.DATA, mimeType, size, fileName);
				metadata.setFileName(fileName);
				metadata = fileDataStore.add(myFile, metadata);

				// Computing and storing thumbnail
				metadataThmb = computeAndStoreThumbnail(owner, myFile, metadata, fileName);
				byte[] timestampToken = getTimeStamp(fileName, myFile, timeStampingUrl);

				document = new Document(metadata);
				document.setThmbUuid(metadataThmb.getUuid());
				document.setTimeStamp(timestampToken);
				document.setSha256sum(sha256sum);
				document = documentRepository.create(document);
			} else {
				document = documents.get(0);
			}
			docEntry.setName(fileName);
			docEntry.setDocument(document);
			docEntry.setExpirationDate(expirationDate);
			docEntry.setSize(size);
			docEntry.setType(mimeType);
			docEntry.setSha256sum(sha256sum);
			//aes encrypt ? check headers
			if(checkIfIsCiphered) {
				docEntry.setCiphered(checkIfFileIsCiphered(fileName, myFile));
			}
			docEntry.setModificationDate(new GregorianCalendar());
			documentEntryRepository.update(docEntry);
			return docEntry;
		} catch (BusinessException e) {
			logger.error("Could not add  " + fileName + " to user " + owner.getAccountRepresentation() + ", reason : ", e);
			if (metadata != null)	fileDataStore.remove(metadata);
			if (metadataThmb != null)	fileDataStore.remove(metadataThmb);
			throw new TechnicalException(TechnicalErrorCode.COULD_NOT_INSERT_DOCUMENT, "couldn't register the file in the database");
		}
	}

	@Override
	public void deleteDocumentEntry(DocumentEntry documentEntry) throws BusinessException {
		UploadRequestEntry uploadRequestEntry = uploadRequestEntryBusinessService.findRelative(documentEntry);
		if (uploadRequestEntry != null) {
			uploadRequestEntry.setDocumentEntry(null);
			uploadRequestEntryBusinessService.update(uploadRequestEntry);
		}
		logger.debug("Deleting document entry: " + documentEntry.getUuid());
		Account owner = documentEntry.getEntryOwner();
		owner.getEntries().remove(documentEntry);
		documentEntryRepository.delete(documentEntry);
	}

	@Override
	public WorkGroupDocument createWorkGroupDocument(Account actor, Thread workGroup, File myFile, Long size, String fileName, Boolean checkIfIsCiphered, String timeStampingUrl, String mimeType, WorkGroupNode nodeParent) throws BusinessException {
		if (exists(workGroup, fileName, nodeParent)) {
			throw new BusinessException(BusinessErrorCode.WORK_GROUP_DOCUMENT_ALREADY_EXISTS,
					"Can not create a new document, it already exists.");
		}
		Document document = createDocument(workGroup, myFile, size, fileName, timeStampingUrl, mimeType);
		WorkGroupDocument node = new WorkGroupDocument(actor, fileName, document, workGroup, nodeParent);
		//aes encrypt ? check headers
		if (checkIfIsCiphered) {
			node.setCiphered(checkIfFileIsCiphered(fileName, myFile));
		}
		node.setCreationDate(new Date());
		node.setModificationDate(new Date());
		node.setUploadDate(new Date());
		node.setPathFromParent(nodeParent);
		node.setLastAuthor(new AccountMto(actor, true));
		try {
			node = repository.insert(node);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new BusinessException(BusinessErrorCode.WORK_GROUP_DOCUMENT_ALREADY_EXISTS,
					"Can not create a new document, it already exists.");
		}		
		return node;
	}

	@Override
	public WorkGroupDocument copy(Account actor, Thread workgroup, WorkGroupNode nodeParent, String documentUuid,
			String name) throws BusinessException {
		if (exists(workgroup, name, nodeParent)) {
			throw new BusinessException(BusinessErrorCode.WORK_GROUP_DOCUMENT_ALREADY_EXISTS,
					"Can not create a new document, it already exists.");
		}
		Document document = documentRepository.findByUuid(documentUuid);
		if (document == null) {
			throw new BusinessException(BusinessErrorCode.NO_SUCH_ELEMENT,
					"Can not create a new document, mising underlying document.");
		}
		Document createDocument = createDocument(workgroup, document, name);
		WorkGroupDocument node = new WorkGroupDocument(actor, name, createDocument, workgroup, nodeParent);
		node.setCreationDate(new Date());
		node.setModificationDate(new Date());
		node.setUploadDate(new Date());
		node.setPathFromParent(nodeParent);
		try {
			node = repository.insert(node);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new BusinessException(BusinessErrorCode.WORK_GROUP_DOCUMENT_ALREADY_EXISTS,
					"Can not create a new document, it already exists.");
		}
		return node;
	}

	@Override
	public DocumentEntry copyFromThreadEntry(Account owner,
			ThreadEntry entry, Calendar expirationDate)
			throws BusinessException {
		String name = entry.getName();
		Document document = entry.getDocument();
		DocumentEntry docEntry = new DocumentEntry(owner, name, entry.getComment(), document);
		// We need to set an expiration date in case of file cleaner activation.
		docEntry.setExpirationDate(expirationDate);
		docEntry.setMetaData(entry.getMetaData());
		docEntry.setCiphered(entry.getCiphered());
		docEntry = documentEntryRepository.create(docEntry);
		owner.getEntries().add(docEntry);
		return docEntry;
	}

	@Override
	public DocumentEntry copyFromShareEntry(Account owner,
			ShareEntry entry, Calendar expirationDate) throws BusinessException {
		String name = entry.getName();
		Document document = entry.getDocumentEntry().getDocument();
		DocumentEntry docEntry = new DocumentEntry(owner, name, entry.getComment(), document);
		// We need to set an expiration date in case of file cleaner activation.
		docEntry.setExpirationDate(expirationDate);
		docEntry.setMetaData(entry.getMetaData());
		docEntry.setCiphered(entry.getDocumentEntry().getCiphered());
		docEntry = documentEntryRepository.create(docEntry);
		owner.getEntries().add(docEntry);
		return docEntry;
	}

	@Override
	public InputStream getDocumentStream(WorkGroupDocument entry) {
		String UUID = entry.getDocumentUuid();
		if (UUID!=null && UUID.length()>0) {
			Document document = documentRepository.findByUuid(UUID);
			logger.debug("retrieve from jackrabbit : " + UUID);
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.DATA, document);
			InputStream stream = fileDataStore.get(metadata);
			return stream;
		}
		return null;
	}

	private Document createDocument(Account owner, File myFile, Long size, String fileName, String timeStampingUrl, String mimeType) throws BusinessException {
		String sha256sum = SHA256CheckSumFileStream(myFile);
		List<Document> documents = documentRepository.findBySha256Sum(sha256sum);
		if (documents.isEmpty() || !deduplication) {
			// Storing file
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.DATA, mimeType, size, fileName);
			metadata = fileDataStore.add(myFile, metadata);
			// Computing and storing thumbnail
			FileMetaData metadataThmb = computeAndStoreThumbnail(owner, myFile, metadata, fileName);
			try {
				// want a timestamp on doc ?
				byte[] timestampToken = null;
				if(timeStampingUrl != null) {
					timestampToken = getTimeStamp(fileName, myFile, timeStampingUrl);
				}
				// add an document for the file in DB
				Document document = new Document(metadata);
				document.setSha256sum(sha256sum);
				if (metadataThmb != null)
					document.setThmbUuid(metadataThmb.getUuid());
				document.setTimeStamp(timestampToken);
				return documentRepository.create(document);
			} catch (Exception e) {
				if (metadata != null)
					fileDataStore.remove(metadata);
				if (metadataThmb != null)
					fileDataStore.remove(metadataThmb);
				throw e;
			}
		} else {
			// we return the first one, the others will be removed by time (expiration, users, ...)
			return documents.get(0);
		}
	}

	protected Document createDocument(Account owner, Document srcDocument, String fileName) throws BusinessException {
		boolean createIt = false;
		Document document = null;
		List<Document> documents = documentRepository.findBySha256Sum(srcDocument.getSha256sum());
		if (documents.isEmpty() || !deduplication) {
			createIt = true;
		} else {
			// we return the first one, the others will be removed by time (expiration, users, ...)
			document = documents.get(0);
			if (!fileDataStore.exists(new FileMetaData(FileMetaDataKind.DATA, document))) {
				createIt = true;
			}
		}

		if (createIt) {
			FileMetaData metadataSrc = new FileMetaData(FileMetaDataKind.DATA, srcDocument);
			// copy document
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.DATA, srcDocument.getType(), srcDocument.getSize(), fileName);
			try (InputStream docStream = fileDataStore.get(metadataSrc)) {
				metadata = fileDataStore.add(docStream, metadata);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
				throw new BusinessException("Can not create a copy of existing document.");
			}
			// dirty copy thumbnail
			FileMetaData metadataThmb = null;
			try (InputStream inputStream = getDocumentThumbnailStream(srcDocument)) {
				if (inputStream != null) {
					File tempThumbFile = null;
					try {
						tempThumbFile = File.createTempFile("linthumbnail", owner + "_thumb.png");
						tempThumbFile.createNewFile();
						try (FileOutputStream fos = new FileOutputStream(tempThumbFile)) {
							IOUtils.copyAndCloseInput(inputStream, fos);
							metadataThmb = computeAndStoreThumbnail(owner, tempThumbFile, metadata, fileName);
						}
					} catch (Exception e) {
						logger.error("Can not create a copy thumbnail of existing document.");
						logger.error(e.getMessage(), e);
						if (tempThumbFile != null) {
							tempThumbFile.delete();
						}
					}
				}
			} catch (IOException e1) {
				logger.error("Can not create a copy thumbnail of existing document.");
				logger.error(e1.getMessage(), e1);
			}
			document = new Document(metadata);
			document.setSha256sum(srcDocument.getSha256sum());
			document.setSha1sum(srcDocument.getSha1sum());
			if (metadataThmb != null)
				document.setThmbUuid(metadataThmb.getUuid());
			document = documentRepository.create(document);
		}
		return document;
	}

	@Override
	public void deleteDocument(Document document) throws BusinessException {
		// delete old thumbnail in JCR
		String oldThumbUuid = document.getThmbUuid();
		if (oldThumbUuid != null && oldThumbUuid.length() > 0) {
			logger.debug("suppression of Thumb, Uuid : " + oldThumbUuid);
			FileMetaData metadata = new FileMetaData(FileMetaDataKind.THUMBNAIL, document, "image/png");
			fileDataStore.remove(metadata);
		}

		// remove old document in JCR
		logger.debug("suppression of doc, Uuid : " + document.getUuid());
		FileMetaData metadata = new FileMetaData(FileMetaDataKind.DATA, document);
		fileDataStore.remove(metadata);

		//clean all signatures ...
		Set<Signature> signatures = document.getSignatures();
		for (Signature signature : signatures) {
			signatureBusinessService.deleteSignature(signature);
		}
		signatures.clear();
		documentRepository.update(document);

		// remove old document from database
		documentRepository.delete(document);
	}

	private FileMetaData computeAndStoreThumbnail(Account owner, File tempFile, FileMetaData metadata, String fileName) {
		if (!thumbEnabled || (!pdfThumbEnabled && metadata.getMimeType().contains("pdf"))) {
			logger.warn("Thumbnail generation is disabled.");
			return null;
		}

		FileMetaData ret = null;
		FileResourceFactory fileResourceFactory = FileResourceFactory.getInstance();
//		FileResource fileResource = fileResourceFactory.getFileResource(tempFile, fileName);
		FileResource fileResource = fileResourceFactory.getFileResource(tempFile);
		InputStream fisThmb = null;
		BufferedImage bufferedImage=null;
		File tempThumbFile = null;
		if (fileResource != null) {
			try {
				try {
					bufferedImage = fileResource.generateThumbnailImage();
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
					logger.debug(e.toString());
				}
				if (bufferedImage != null) {
					fisThmb = ImageUtils.getInputStreamFromImage(bufferedImage, "png");
					tempThumbFile = File.createTempFile("linthumbnail", owner+"_thumb.png");
					tempThumbFile.createNewFile();

					if (bufferedImage!=null)
						ImageIO.write(bufferedImage, Constants.THMB_DEFAULT_FORMAT, tempThumbFile);

					if (logger.isDebugEnabled()) {
						logger.debug("5.1)start insert of thumbnail in jack rabbit:" + tempThumbFile.getName());
					}
					ret = new FileMetaData(FileMetaDataKind.THUMBNAIL, "image/png", tempThumbFile.length(), metadata.getFileName());
					ret = fileDataStore.add(tempThumbFile, ret);
				}
			} catch (FileNotFoundException e) {
				logger.error(e.toString());
				// if the thumbnail generation fails, it's not big deal, it has not to block
				// the entire process, we just don't have a thumbnail for this document
			} catch (IOException e) {
				logger.error(e.toString());
			} finally {
				try {
					if (fisThmb != null)
						fisThmb.close();
				} catch (IOException e) {
					// Do nothing Happy java :)
					logger.error(e.toString());
				}
				if(tempThumbFile!=null){
					tempThumbFile.delete();
				}
			}
		}
		return ret;
	}

	private boolean checkIfFileIsCiphered(String fileName, File tempFile) throws BusinessException {
		boolean testheaders = false;
		if(fileName.endsWith(".aes")){
			try {
				AESCrypt aestest = new AESCrypt();
				testheaders = aestest.ckeckFileHeader(tempFile.getAbsolutePath());
			} catch (IOException e) {
				throw new BusinessException(BusinessErrorCode.FILE_ENCRYPTION_UNDEFINED,"undefined encryption format");
			} catch (GeneralSecurityException e) {
				throw new BusinessException(BusinessErrorCode.FILE_ENCRYPTION_UNDEFINED,"undefined encryption format");
			}

			if(!testheaders)
				throw new BusinessException(BusinessErrorCode.FILE_ENCRYPTION_UNDEFINED,"undefined encryption format");
		}
		return testheaders;
	}

	@Override
	public long getRelatedEntriesCount(DocumentEntry documentEntry) {
		return documentEntryRepository.getRelatedEntriesCount(documentEntry);
	}

	@Override
	public long getUsedSpace(Account owner) throws BusinessException {
		return documentEntryRepository.getUsedSpace(owner);
	}

	@Override
	public String SHA256CheckSumFileStream(File file) {
		try(InputStream fs = new FileInputStream(file)) {
			return SHA256CheckSumFileStream(fs);
		} catch (IOException e) {
			logger.error("Could not found the file : " + file.getName(), e);
			throw new BusinessException(BusinessErrorCode.FILE_UNREACHABLE, "Coul not found the file " + file.getName());
		}
	}

	@Override
	public String SHA256CheckSumFileStream(InputStream fis) throws IOException {
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] dataBytes = new byte[1024];

			int nread = 0;
			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
			byte[] mdbytes = md.digest();
			// convert the byte to hex format
			for (int i = 0; i < mdbytes.length; i++) {
				hexString.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			throw new TechnicalException(e.getMessage());
		}
	}

	@Override
	public void update(DocumentEntry docEntry) throws BusinessException {
		docEntry.setModificationDate(new GregorianCalendar());
		documentEntryRepository.update(docEntry);
	}

	@Override
	public DocumentEntry findMoreRecentByName(Account owner, String fileName)
			throws BusinessException {
		return documentEntryRepository.findMoreRecentByName(owner, fileName);
	}

	@Override
	public void syncUniqueDocument(Account owner, String fileName)
			throws BusinessException {
		documentEntryRepository.syncUniqueDocument(owner, fileName);
	}

	@Override
	public List<DocumentEntry> findAllMySyncEntries(Account owner)
			throws BusinessException {
		return documentEntryRepository.findAllMySyncEntries(owner);
	}

	@Override
	public List<String> findAllExpiredEntries() {
		return documentEntryRepository.findAllExpiredEntries();
	}

	protected boolean exists(Thread workGroup, String fileName, WorkGroupNode nodeParent) {
		List<WorkGroupNode> nodes = repository.findByWorkGroupAndParentAndName(workGroup.getLsUuid(),
				nodeParent.getUuid(), fileName);
		if (nodes != null && !nodes.isEmpty()) {
			return true;
		}
		return false;
	}
}
