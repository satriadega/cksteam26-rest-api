-- INSERT SEEDER HERE!

SET IDENTITY_INSERT [Arsipku].[UserAccount] ON
;

INSERT INTO [Arsipku].[UserAccount]
    ([UserId], [Username], [Name], [Email], [Password], [StatusNotification], [HasNotification], [NotificationCounter], [NotificationType], [IsVerified], [Token], [CreatedAt], [UpdatedAt])
VALUES
    (1, N'admin', N'Administrator', N'admin@example.com', N'hashedpassword1', 1, 0, 0, 0, 1, NULL, GETDATE(), NULL)
;

INSERT INTO [Arsipku].[UserAccount]
    ([UserId], [Username], [Name], [Email], [Password], [StatusNotification], [HasNotification], [NotificationCounter], [NotificationType], [IsVerified], [Token], [CreatedAt], [UpdatedAt])
VALUES
    (2, N'johndoe', N'John Doe', N'john.doe@example.com', N'hashedpassword2', 1, 1, 2, 1, 0, N'123456', GETDATE(), NULL)
;

INSERT INTO [Arsipku].[UserAccount]
  ([UserId], [Username], [Name], [Email], [Password], [StatusNotification], [HasNotification], [NotificationCounter], [NotificationType], [IsVerified], [Token], [CreatedAt], [UpdatedAt])
VALUES
  (3, N'janedoe', N'Jane Doe', N'jane.doe@example.com', N'hashedpassword3', 1, 0, 0, 0, 1, NULL, GETDATE(), NULL)
;

SET IDENTITY_INSERT [Arsipku].[UserAccount] OFF
;

-- INSERT SEEDER HERE!

SET IDENTITY_INSERT [Arsipku].[Organization] ON
;

INSERT INTO [Arsipku].[Organization]
    ([OrganizationId], [OrganizationName], [PublicVisibility], [CreatedAt], [UpdatedAt])
VALUES
    (1, N'Hexxx', 1, GETDATE(), NULL)
;

INSERT INTO [Arsipku].[Organization]
    ([OrganizationId], [OrganizationName], [PublicVisibility], [CreatedAt], [UpdatedAt])
VALUES
    (2, N'Openzzs', 0, GETDATE(), NULL)
;

INSERT INTO [Arsipku].[Organization]
    ([OrganizationId], [OrganizationName], [PublicVisibility], [CreatedAt], [UpdatedAt])
VALUES
    (3, N'Example Corp', 1, GETDATE(), NULL)
;

SET IDENTITY_INSERT [Arsipku].[Organization] OFF
;

-- INSERT SEEDER HERE!

INSERT INTO [Arsipku].[UserOrganization] (UserId, OrganizationId, OrganizationOwner, CreatedAt) VALUES (1, 1, 1, GETDATE());
INSERT INTO [Arsipku].[UserOrganization] (UserId, OrganizationId, OrganizationOwner, CreatedAt) VALUES (2, 1, 0, GETDATE());
INSERT INTO [Arsipku].[UserOrganization] (UserId, OrganizationId, OrganizationOwner, CreatedAt) VALUES (2, 2, 1, GETDATE());

-- Seeder for Document table

INSERT INTO [Arsipku].[Document]
(Title, Content, IsVerifiedAll, PublicVisibility, ReferenceDocumentId, Version, Subversion, CreatedAt, UpdatedAt)
VALUES
(N'Panduan Penggunaan Aplikasi', N'Ini adalah panduan lengkap penggunaan aplikasi kami untuk pengguna baru.', 1, 1, NULL, 1, 0, GETDATE(), NULL);

INSERT INTO [Arsipku].[Document]
(Title, Content, IsVerifiedAll, PublicVisibility, ReferenceDocumentId, Version, Subversion, CreatedAt, UpdatedAt)
VALUES
(N'Panduan Penggunaan Aplikasi (Revisi)', N'Perubahan di bagian registrasi pengguna dan notifikasi.', 1, 1, 1, 2, 0, DATEADD(day, 30, GETDATE()), NULL);

INSERT INTO [Arsipku].[Document]
(Title, Content, IsVerifiedAll, PublicVisibility, ReferenceDocumentId, Version, Subversion, CreatedAt, UpdatedAt)
VALUES
(N'Panduan Penggunaan Aplikasi (Revisi Minor)', N'Perbaikan typo dan update tampilan antarmuka.', 1, 1, 2, 2, 1, DATEADD(day, 45, GETDATE()), DATEADD(day, 45, GETDATE()));

-- Seeder for Annotation

INSERT INTO [Arsipku].[Annotation]
(DocumentId, OwnerUserId, IsVerified, SelectedText, StartNo, EndNo, Description, CreatedAt, UpdatedAt)
VALUES
(1, 1, 1, N'panduan lengkap penggunaan aplikasi', 11, 43, N'Bagian penting panduan awal', GETDATE(), NULL);

INSERT INTO [Arsipku].[Annotation]
(DocumentId, OwnerUserId, IsVerified, SelectedText, StartNo, EndNo, Description, CreatedAt, UpdatedAt)
VALUES
(2, 2, 0, N'registrasi pengguna', 16, 34, N'Fokus pada proses registrasi', DATEADD(day, 10, GETDATE()), NULL);

INSERT INTO [Arsipku].[Annotation]
(DocumentId, OwnerUserId, IsVerified, SelectedText, StartNo, EndNo, Description, CreatedAt, UpdatedAt)
VALUES
(3, 2, 1, N'update tampilan', 14, 28, N'Perubahan UI terbaru', DATEADD(day, 15, GETDATE()), DATEADD(day, 15, GETDATE()));

-- Seeder for Tag

INSERT INTO [Arsipku].[Tag]
(AnnotationId, TagName, CreatedAt, UpdatedAt)
VALUES
(1, N'Panduan', GETDATE(), NULL);

INSERT INTO [Arsipku].[Tag]
(AnnotationId, TagName, CreatedAt, UpdatedAt)
VALUES
(2, N'Registrasi', DATEADD(day, 10, GETDATE()), NULL);

INSERT INTO [Arsipku].[Tag]
(AnnotationId, TagName, CreatedAt, UpdatedAt)
VALUES
(3, N'UI', DATEADD(day, 15, GETDATE()), DATEADD(day, 15, GETDATE()));

-- Seeder for UserDocumentPosition

SET IDENTITY_INSERT [Arsipku].[UserDocumentPosition] ON;

INSERT INTO [Arsipku].[UserDocumentPosition]
(UserDocumentVerifierId, DocumentId, UserId, IsVerified, Position, CreatedAt, UpdatedAt)
VALUES
(1, 1, 1, 1, N'OWNER', GETDATE(), NULL);

INSERT INTO [Arsipku].[UserDocumentPosition]
(UserDocumentVerifierId, DocumentId, UserId, IsVerified, Position, CreatedAt, UpdatedAt)
VALUES
(2, 1, 2, 0, N'VERIFIER', GETDATE(), NULL);

INSERT INTO [Arsipku].[UserDocumentPosition]
(UserDocumentVerifierId, DocumentId, UserId, IsVerified, Position, CreatedAt, UpdatedAt)
VALUES
(3, 2, 2, 1, N'OWNER', DATEADD(day, 30, GETDATE()), NULL);

SET IDENTITY_INSERT [Arsipku].[UserDocumentPosition] OFF;

-- Seeder for ListApplianceDocumentVerifier

SET IDENTITY_INSERT [Arsipku].[ListApplianceDocumentVerifier] ON;

INSERT INTO [Arsipku].[ListApplianceDocumentVerifier]
(ListApplianceDocumentVerifierId, DocumentId, UserId, IsAccepted, CreatedAt)
VALUES
(1, 1, 2, 0, GETDATE());

INSERT INTO [Arsipku].[ListApplianceDocumentVerifier]
(ListApplianceDocumentVerifierId, DocumentId, UserId, IsAccepted, CreatedAt)
VALUES
(2, 2, 2, 1, DATEADD(day, 10, GETDATE()));

INSERT INTO [Arsipku].[ListApplianceDocumentVerifier]
(ListApplianceDocumentVerifierId, DocumentId, UserId, IsAccepted, CreatedAt)
VALUES
(3, 1, 1, 1, DATEADD(day, -5, GETDATE()));

SET IDENTITY_INSERT [Arsipku].[ListApplianceDocumentVerifier] OFF;

-- Seeder for notification

SET IDENTITY_INSERT [Arsipku].[Notification] ON;

INSERT INTO [Arsipku].[Notification] (NotificationId, UserId, type, isRead)
VALUES
(1, 1, N'VERIFIER', 0),
(2, 2, N'APPLIANCE', 0),
(3, 1, N'VERIFIER', 1),
(4, 3, N'APPLIANCE', 0);

SET IDENTITY_INSERT [Arsipku].[Notification] OFF;
