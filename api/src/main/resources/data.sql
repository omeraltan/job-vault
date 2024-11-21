-- data.sql

INSERT INTO candidate (first_name, last_name, email, phone, position_type, military_status, notice_period, cv_path, created_at, created_by)
VALUES
    ('Ahmet', 'Yılmaz', 'ahmet.yilmaz@example.com', '555-123-4567', 'BACKEND_DEVELOPER', 'DONE', 'FIVE_DAYS', '/resumes/ahmet_yilmaz.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Ayşe', 'Kaya', 'ayse.kaya@example.com', '555-234-5678', 'FRONTEND_DEVELOPER', 'DONE', 'TEN_DAYS', '/resumes/ayse_kaya.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Mehmet', 'Demir', 'mehmet.demir@example.com', '555-345-6789', 'FULL_STACK_DEVELOPER', 'NOT_DONE', 'ONE_WEEK', '/resumes/mehmet_demir.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Fatma', 'Çelik', 'fatma.celik@example.com', '555-456-7890', 'BACKEND_DEVELOPER', 'DONE', 'TWO_WEEKS', '/resumes/fatma_celik.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Ali', 'Güzel', 'ali.guzel@example.com', '555-567-8901', 'FRONTEND_DEVELOPER', 'NOT_DONE', 'ONE_MONTH', '/resumes/ali_guzel.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Zeynep', 'Aydın', 'zeynep.aydin@example.com', '555-678-9012', 'BACKEND_DEVELOPER', 'DONE', 'THREE_WEEKS', '/resumes/zeynep_aydin.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Burak', 'Kurt', 'burak.kurt@example.com', '555-789-0123', 'FULL_STACK_DEVELOPER', 'DONE', 'FIVE_DAYS', '/resumes/burak_kurt.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Elif', 'Öztürk', 'elif.ozturk@example.com', '555-890-1234', 'BACKEND_DEVELOPER', 'NOT_DONE', 'TEN_DAYS', '/resumes/elif_ozturk.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Kadir', 'Sarı', 'kadir.sari@example.com', '555-901-2345', 'FRONTEND_DEVELOPER', 'DONE', 'ONE_WEEK', '/resumes/kadir_sari.pdf', CURRENT_TIMESTAMP, 'SYSTEM'),
    ('Merve', 'Can', 'merve.can@example.com', '555-012-3456', 'BACKEND_DEVELOPER', 'DONE', 'ONE_MONTH', '/resumes/merve_can.pdf', CURRENT_TIMESTAMP, 'SYSTEM');
