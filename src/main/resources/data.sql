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
(Title, Content, IsVerifiedAll, PublicVisibility, ReferenceDocumentId, Version, Subversion, CreatedAt, UpdatedAt) VALUES
(N'Panduan Penggunaan Aplikasi',
 N'Panduan ini menjelaskan langkah demi langkah cara menggunakan aplikasi kami mulai dari registrasi, login, hingga fitur-fitur utama yang tersedia. Pengguna akan mendapatkan pemahaman lengkap agar dapat memaksimalkan penggunaan aplikasi.',
 1, 1, NULL, 1, 0, GETDATE(), NULL),

(N'Panduan Penggunaan Aplikasi (Revisi)',
 N'Dalam revisi ini terdapat penambahan fitur notifikasi real-time dan perbaikan pada proses registrasi pengguna yang sebelumnya mengalami kendala pada beberapa perangkat.',
 1, 1, 1, 2, 0, DATEADD(day, 30, GETDATE()), NULL),

(N'Panduan Penggunaan Aplikasi (Revisi Minor)',
 N'Revisi minor ini meliputi perbaikan typo di beberapa halaman dan peningkatan tampilan antarmuka agar lebih responsif pada perangkat mobile.',
 1, 1, 2, 2, 1, DATEADD(day, 45, GETDATE()), DATEADD(day, 45, GETDATE())),

(N'FAQ Aplikasi',
 N'Bagian ini berisi kumpulan pertanyaan yang sering diajukan oleh pengguna beserta jawabannya. Meliputi cara reset password, kendala saat login, dan panduan penggunaan fitur tertentu.',
 1, 1, NULL, 1, 0, DATEADD(day, -10, GETDATE()), NULL),

(N'Kebijakan Privasi',
 N'Kami berkomitmen untuk melindungi data pribadi pengguna. Dokumen ini menjelaskan bagaimana data dikumpulkan, digunakan, dan dijaga keamanannya sesuai dengan regulasi yang berlaku.',
 1, 1, NULL, 1, 0, DATEADD(day, -20, GETDATE()), NULL),

(N'Syarat dan Ketentuan Penggunaan',
 N'Syarat dan ketentuan ini harus disetujui oleh pengguna sebelum menggunakan aplikasi. Dokumen mencakup hak dan kewajiban pengguna serta batasan tanggung jawab dari pihak pengembang.',
 1, 1, NULL, 1, 0, DATEADD(day, -30, GETDATE()), NULL),

(N'Panduan Instalasi',
 N'Panduan ini menjelaskan cara instalasi aplikasi pada berbagai perangkat termasuk komputer desktop dan perangkat mobile, lengkap dengan langkah-langkah troubleshooting untuk masalah umum.',
 1, 1, NULL, 1, 0, DATEADD(day, -40, GETDATE()), NULL),

(N'Catatan Rilis Versi 1.0',
 N'Rilis versi 1.0 ini menghadirkan fitur utama aplikasi seperti pendaftaran pengguna, dashboard interaktif, dan sistem notifikasi. Juga terdapat dokumentasi terkait bug yang telah diperbaiki.',
 1, 1, NULL, 1, 0, DATEADD(day, -50, GETDATE()), NULL),

(N'Catatan Rilis Versi 1.1',
 N'Versi 1.1 membawa peningkatan performa, perbaikan bug, dan tambahan fitur keamanan yang lebih ketat untuk melindungi data pengguna.',
 1, 1, 8, 1, 1, DATEADD(day, -20, GETDATE()), DATEADD(day, -20, GETDATE())),

(N'Panduan Penggunaan Fitur Baru',
 N'Dokumentasi lengkap penggunaan fitur terbaru aplikasi, termasuk cara mengaktifkan, konfigurasi, dan tips pemanfaatan agar fitur dapat berjalan optimal.',
 1, 1, NULL, 1, 0, DATEADD(day, -15, GETDATE()), NULL);

INSERT INTO [Arsipku].[Document]
(Title, Content, IsVerifiedAll, PublicVisibility, ReferenceDocumentId, Version, Subversion, CreatedAt, UpdatedAt) VALUES
(N'Panduan Backup Data',
 N'Panduan lengkap untuk melakukan backup data secara rutin agar data penting aplikasi Anda tetap aman dan dapat dipulihkan saat terjadi kegagalan sistem.',
 1, 1, NULL, 1, 0, DATEADD(day, -60, GETDATE()), NULL),

(N'Panduan Restore Data',
 N'Langkah-langkah melakukan restore data dari backup yang telah dibuat sebelumnya, termasuk opsi untuk restore parsial maupun penuh.',
 1, 1, NULL, 1, 0, DATEADD(day, -58, GETDATE()), NULL),

(N'Panduan Keamanan Aplikasi',
 N'Dokumentasi ini menjelaskan langkah-langkah pengamanan aplikasi dari ancaman eksternal, termasuk konfigurasi firewall dan penggunaan SSL.',
 1, 1, NULL, 1, 0, DATEADD(day, -55, GETDATE()), NULL),

(N'Panduan Monitoring Sistem',
 N'Cara melakukan monitoring aplikasi dan infrastruktur untuk mendeteksi masalah sebelum berdampak ke pengguna.',
 1, 1, NULL, 1, 0, DATEADD(day, -53, GETDATE()), NULL),

(N'Panduan Pemecahan Masalah Umum',
 N'Daftar masalah yang sering muncul beserta solusi dan cara mengatasinya agar aplikasi dapat berjalan lancar tanpa gangguan.',
 1, 1, NULL, 1, 0, DATEADD(day, -50, GETDATE()), NULL),

(N'Panduan Integrasi API',
 N'Dokumentasi untuk developer mengenai cara mengintegrasikan API aplikasi dengan sistem eksternal.',
 1, 1, NULL, 1, 0, DATEADD(day, -48, GETDATE()), NULL),

(N'Panduan Penggunaan Mobile App',
 N'Panduan lengkap penggunaan aplikasi versi mobile, termasuk fitur khusus dan cara mengatasi kendala yang umum terjadi.',
 1, 1, NULL, 1, 0, DATEADD(day, -45, GETDATE()), NULL),

(N'Panduan Upgrade Versi',
 N'Prosedur dan langkah yang harus diikuti untuk melakukan upgrade versi aplikasi tanpa mengganggu operasional.',
 1, 1, NULL, 1, 0, DATEADD(day, -42, GETDATE()), NULL),

(N'Panduan Migrasi Data',
 N'Langkah-langkah migrasi data dari sistem lama ke sistem baru secara aman dan efektif.',
 1, 1, NULL, 1, 0, DATEADD(day, -40, GETDATE()), NULL),

(N'Panduan Pengelolaan User',
 N'Dokumentasi tentang cara mengelola akun pengguna, pengaturan peran, dan hak akses dalam aplikasi.',
 1, 1, NULL, 1, 0, DATEADD(day, -38, GETDATE()), NULL);

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
