INSERT INTO mail_layout (id, domain_abstract_id, description, visible, layout, creation_date, modification_date, uuid, readonly, messages_french, messages_english) VALUES (1, 1, 'Default HTML layout', true, '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', now(), now(), '15044750-89d1-11e3-8d50-5404a683a462', true, 'productName=LinShare', 'productName=LinShare');
INSERT INTO mail_config (id, mail_layout_id, domain_abstract_id, name, visible, uuid, creation_date, modification_date, readonly) VALUES (1, 1, 1, 'Default mail config', true, '946b190d-4c95-485f-bfe6-d288a2de1edd', now(), now(), false);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (4, 1, 'Password reset', true, 3, 'Your password has been reset', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '753d57a8-4fcc-4346-ac92-f71828aca77c', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (2, 1, 'Registered user downloaded a file', true, 1, 'A user ${actorRepresentation} has just downloaded a file you made available for sharing', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '403e5d8b-bc38-443d-8b94-bab39a4460af', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (3, 1, 'New guest', true, 2, 'Your LinShare account has been sucessfully created', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'a1ca74a5-433d-444a-8e53-8daa08fa0ddb', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (5, 1, 'Shared document was updated', true, 4, 'A user ${actorRepresentation} has just modified a shared file you still have access to', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '09a50c58-b430-4ccf-ab3e-0257c463d8df', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (6, 1, 'Shared document was deleted', true, 5, 'A user ${actorRepresentation} has just deleted a shared file you had access to!', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '554a3a2b-53b1-4ec8-9462-2d6053b80078', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (7, 1, 'Shared document is soon to be outdated', true, 6, 'A LinShare workspace is about to be deleted', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'e7bf56c2-b015-4e64-9f07-3c7e2f3f9ca8', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (21, 1, 'Upload request will be expired', true, 20, 'The upload request: ${subject}, will expire', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'd43b22d6-d915-41cc-99e4-9c9db66c5aac', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (8, 1, 'Document is soon to be outdated', true, 7, 'A shared file is about to be deleted!', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '1507e9c0-c1e1-4e0f-9efb-506f63cbba97', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (10, 1, 'New sharing with password protection', true, 9, 'A user ${actorRepresentation} has just made a file available to you!', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '1e972f43-619c-4bd6-a1bd-10667b80af74', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (22, 1, 'Upload request will be expired', true, 21, 'The upload request: ${subject}, will expire', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '0bea7e7c-e2e9-44ff-bbb3-7e28967a4d67', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (23, 1, 'Upload request is expired', true, 22, 'The upload request: ${subject}, is expired', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '0cd705f3-f1f5-450d-bfcd-f2f5a60c57f8', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (11, 1, 'New sharing of a cyphered file', true, 10, 'A user ${actorRepresentation} has just made a file available to you!', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'fef9a3f1-6011-46cd-8d39-6bd1bc02f899', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (12, 1, 'New sharing with password protection of a cyphered file', true, 11, 'A user ${actorRepresentation} has just made a file available to you!', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '2da85945-7793-43f4-b547-eacff15a6f88', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (13, 1, 'New upload proposition', true, 12, 'A user ${actorRepresentation} has send to you an upload proposition: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'dd7d6a36-03b6-48e8-bfb5-3c2d8dc227fd', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (14, 1, 'Upload proposition rejected', true, 13, 'A user ${actorRepresentation} has rejected your upload proposition: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '62af93dd-0b19-4376-bc76-08b7a97fc0f2', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (15, 1, 'Upload request updated', true, 14, 'A user ${actorRepresentation} has updated upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '40f36a3b-39ea-4723-a292-9c86e2ee8f94', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (16, 1, 'Upload request activated', true, 15, 'A user ${actorRepresentation} has activated upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '817ae032-9022-4c22-97a3-cfb5ce50817c', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (17, 1, 'Upload proposition filtered', true, 16, 'An upload proposition has been filtered: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'd692674c-e797-49f1-a415-1df7ea5c8fee', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (18, 1, 'Upload request created', true, 17, 'A user ${actorRepresentation} has created upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '40a74e4e-a663-4ad2-98ef-1e5d70d3536c', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (19, 1, 'Upload request acknowledgement', true, 18, 'A user ${actorRepresentation} has upload a file for upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '5ea27e5b-9260-4ce1-b1bd-27372c5b653d', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (20, 1, 'Upload request reminder', true, 19, 'A user ${actorRepresentation} reminds you have an upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '0d87e08d-d102-42b9-8ced-4d49c21ce126', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (24, 1, 'Upload request is expired', true, 23, 'The upload request: ${subject}, is expired', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '7412940b-870b-4f58-877c-9955a423a5f3', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (25, 1, 'Upload request closed', true, 24, 'A user ${actorRepresentation} has just closed upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '6c0c1214-0a77-46d0-92c5-c41d225bf9aa', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (26, 1, 'Upload request closed', true, 25, 'A user ${actorRepresentation} has just closed upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '1956ca27-5127-4f42-a41d-81a72a325aae', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (27, 1, 'Upload request deleted', true, 26, 'A user ${actorRepresentation} has just deleted an upload request', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '690f1bbc-4f99-4e70-a6cd-44388e3e2c86', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (28, 1, 'Upload request error: no space left', true, 27, 'A user ${actorRepresentation} has just tried to upload a file but server had no space left', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '48fee30b-b2d3-4f85-b9ee-22044f9dbb4d', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (30, 1, 'Upload request file deleted', true, 29, 'A user ${actorRepresentation} has deleted a file for upload request: ${subject}', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '88b90304-e9c9-11e4-b6b4-5404a6202d2c', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (31, 1, 'Share creation acknowledgement', true, 30, '[SHARE ACKNOWLEDGEMENT] Shared on ${date}.', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '01e0ac2e-f7ba-11e4-901b-08002722e7b1', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (32, 1, 'Share creation acknowledgement', true, 31, '[SHARE ACKNOWLEDGEMENT] ${subject}. Shared on ${date}.', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '2209b038-e1e7-11e4-8d2d-3b2a506425c0', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (33, 1, 'Undownloaded shared documents alert', true, 32, '[Undownloaded shared documents alert] ${subject} Shared on ${date}.', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', 'eb291876-53fc-419b-831b-53a480399f7c', now(), now(), true, NULL, NULL);
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (1, 1, 'Anonymous user downloaded a file', true, 0, '[(#{subject(${shareRecipient.mail})})]', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '938f91ab-b33c-4184-900f-c8a595fc6cd9', now(), '2017-01-18 11:05:23.92', true, 'subject=[Translate in french] An unknown user {0} has just downloaded a file you made available for sharing.', 'subject=An unknown user {0} has just downloaded a file you made available for sharing.');
INSERT INTO mail_content (id, domain_abstract_id, description, visible, mail_content_type, subject, body, uuid, creation_date, modification_date, readonly, messages_french, messages_english) VALUES (9, 1, 'New sharing', true, 8, '[(#{subject(${shareRecipient.firstName},${shareRecipient.lastName})})]', '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>layout</body></html>', '250e4572-7bb9-4735-84ff-6a8af93e3a42', now(), now(), true, 'subject=[Translate in french] An user {0}  {1} has just downloaded a file you made available for sharing.', 'subject=An user {0} {1} has just downloaded a file you made available for sharing.');
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (1, 0, 1, 1, 0, 'd6868568-f5bd-4677-b4e2-9d6924a58871', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (2, 0, 2, 1, 1, '4f3c4723-531e-449b-a1ae-d304fd3d2387', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (3, 0, 3, 1, 2, '81041673-c699-4849-8be4-58eea4507305', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (4, 0, 4, 1, 3, '85538234-1fc1-47a2-850d-7f7b59f1640e', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (5, 0, 5, 1, 4, '796a98eb-0b97-4756-b23e-74b5a939c2e3', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (6, 0, 6, 1, 5, 'ed70cc00-099e-4c44-8937-e8f51835000b', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (7, 0, 7, 1, 6, 'f355793b-17d4-499c-bb2b-e3264bc13dbd', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (8, 0, 8, 1, 7, '5a6764fc-350c-4f10-bdb0-e95ca7607607', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (10, 0, 10, 1, 9, 'fa59abad-490b-4cd5-9a31-3c3302fc4a18', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (11, 0, 11, 1, 10, '5bd828fa-d25e-47fa-9c0d-1bb84304e692', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (12, 0, 12, 1, 11, 'a9096a7e-949c-4fae-aedf-2347c40cd999', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (13, 0, 13, 1, 12, '1216ca54-f510-426c-a12b-8158efa21619', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (14, 0, 14, 1, 13, '9f87c53d-80e5-4e10-b571-d0c9f9c35017', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (15, 0, 15, 1, 14, '454e3e88-7129-4e98-a79a-e119cb94bd07', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (16, 0, 16, 1, 15, '0a8251dd-9514-4b7b-bf47-c398c00ba21b', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (17, 0, 17, 1, 16, 'e3b99efb-875c-4c63-bd5c-8f121d75876b', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (18, 0, 18, 1, 17, 'e37cbade-db93-487d-96ee-dc491ce63035', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (19, 0, 19, 1, 18, '8d707581-3920-4d82-a8ba-f7984afc54ca', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (20, 0, 20, 1, 19, '64b5df7b-b197-49a7-b0af-aaac2c2f8d79', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (21, 0, 21, 1, 20, 'fd6011cf-e4cf-478d-835b-75b25e024b81', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (22, 0, 22, 1, 21, 'e4439f5b-380b-4a78-86a7-764f15ff599d', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (23, 0, 23, 1, 22, '7a560359-fa35-4ffd-ac1d-1d9ceef1b1e0', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (24, 0, 24, 1, 23, '2b038721-fe6e-4406-b5de-c4c84a964df8', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (25, 0, 25, 1, 24, '822b3ede-daea-4b60-a8a2-2216c7d36fea', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (26, 0, 26, 1, 25, 'd8316b6b-f6c8-408b-ac7d-1ebea767912e', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (27, 0, 27, 1, 26, '7642b888-3bd8-4f8c-b65c-81b61e512137', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (28, 0, 28, 1, 27, '9bf9d474-fd10-48da-843c-dfadebd2b455', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (30, 0, 30, 1, 29, 'ec270da7-e9cb-11e4-b6b4-5404a6202d2c', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (31, 0, 31, 1, 30, '447217e4-e1ee-11e4-8a45-fb8c68777bdf', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (32, 0, 32, 1, 31, '1837a6f0-e8c7-11e4-b36a-08002722e7b1', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (33, 0, 33, 1, 32, 'bfcced12-7325-49df-bf84-65ed90ff7f59', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (9, 0, 9, 1, 8, 'befd8182-88a6-4c72-8bae-5fcb7a79b8e7', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (51, 1, 1, 1, 0, 'd0af96a7-6a9c-4c3f-8b8c-7c8e2d0449e1', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (52, 1, 2, 1, 1, '28e5855a-c0e7-40fc-8401-9cf25eb53f03', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (53, 1, 3, 1, 2, '41d0f03d-57dd-420e-84b0-7908179c8329', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (54, 1, 4, 1, 3, '72c0fff4-4638-4e98-8223-df27f8f8ea8b', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (55, 1, 5, 1, 4, '8b7f57c1-b4a1-4896-8e19-d3ebf3af4831', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (56, 1, 6, 1, 5, '6fbabf1a-58c0-49b9-859e-d24b0af38c87', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (57, 1, 7, 1, 6, 'b85fc62f-d9eb-454b-9289-fec5eab51a76', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (58, 1, 8, 1, 7, '25540d2d-b3b8-46a9-811b-0549ad300fe0', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (59, 1, 9, 1, 8, '72ae03e7-5865-433c-a2be-a95c655a8e17', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (60, 1, 10, 1, 9, 'e2af2ff6-585b-4cdc-a887-1755e42fcde6', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (61, 1, 11, 1, 10, '1ee1c8bc-75e9-4fbe-a34b-893a86704ec9', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (62, 1, 12, 1, 11, '12242aa8-b75e-404d-85df-68e7bb8c04af', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (63, 1, 13, 1, 12, '4f2ad41c-3969-461d-a6dc-8f692a1738e9', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (64, 1, 14, 1, 13, '362cf576-30ab-41a5-85d0-3d9175935b14', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (65, 1, 15, 1, 14, '35b81d85-0ee7-44f9-b478-20c8429c2b6d', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (66, 1, 16, 1, 15, '92e0a55e-e4e8-43c9-94f0-0d4e74d5748f', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (67, 1, 17, 1, 16, 'eb8a1b1e-758d-4261-8616-8ead644f70b0', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (68, 1, 18, 1, 17, '50ae2621-556c-446d-a399-55ed799022c3', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (69, 1, 19, 1, 18, '6580009b-36fd-472d-9937-41d0097ead91', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (70, 1, 20, 1, 19, 'ed471d9b-6f64-4d36-97cb-654b73579fe9', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (71, 1, 21, 1, 20, '86fdc43c-5fd7-4aba-b01a-90fccbfb5489', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (72, 1, 22, 1, 21, 'ea3f9814-6da9-49bf-94e5-7ff2c789e07b', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (73, 1, 23, 1, 22, 'f9455b1d-3582-4998-8675-bc0a8137fc73', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (74, 1, 24, 1, 23, '8f91e46b-1cee-45bc-8712-23ea0298db87', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (75, 1, 25, 1, 24, 'e5a9f689-c005-47c2-958f-b68071b1bf6f', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (76, 1, 26, 1, 25, 'a7994bd1-bd67-4cc6-93f3-be935c1cdb67', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (77, 1, 27, 1, 26, '5e1fb460-1efc-497c-96d8-6adf162cbc4e', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (78, 1, 28, 1, 27, '2daaea2a-1b13-48b4-89a6-032f7e034a2d', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (80, 1, 30, 1, 29, 'd6e18c3b-e9cb-11e4-b6b4-5404a6202d2c', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (81, 1, 31, 1, 30, '8f579a8a-e352-11e4-99b3-08002722e7b1', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (82, 1, 32, 1, 31, '2d3a0e80-e8c7-11e4-8349-08002722e7b1', false);
INSERT INTO mail_content_lang (id, language, mail_content_id, mail_config_id, mail_content_type, uuid, readonly) VALUES (83, 1, 33, 1, 32, 'fa7a23cb-f545-45b4-b9dc-c39586cb2398', false);
INSERT INTO mail_footer (id, domain_abstract_id, description, visible, footer, creation_date, modification_date, uuid, readonly, messages_french, messages_english) VALUES (1, 1, 'footer html', true, '<!DOCTYPE html><html xmlns:th="http://www.thymeleaf.org"><body>footer</body></html>', now(), now(), 'e85f4a22-8cf2-11e3-8a7a-5404a683a462', true, '', '');
INSERT INTO mail_footer_lang (id, mail_config_id, mail_footer_id, language, uuid, readonly) VALUES (1, 1, 1, 0, 'bf87e580-fb25-49bb-8d63-579a31a8f81e', false);
INSERT INTO mail_footer_lang (id, mail_config_id, mail_footer_id, language, uuid, readonly) VALUES (2, 1, 1, 1, 'a6c8ee84-b5a8-4c96-b148-43301fbccdd9', false);
UPDATE domain_abstract SET mailconfig_id = 1;
UPDATE mail_footer SET readonly = true;
UPDATE mail_layout SET readonly = true;
UPDATE mail_content SET readonly = true;
