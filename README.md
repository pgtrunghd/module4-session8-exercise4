# module4-session8-exercise4 — Doctor-Service Rate Limiter

Microservice quản lý bác sĩ (port 8082), kết nối PostgreSQL `doctor_db` và đăng ký vào Eureka Server (http://localhost:8761/eureka/).

- Chạy: `DB_PASSWORD=<mật_khẩu_PostgreSQL> ./gradlew bootRun` (cần tạo sẵn DB `doctor_db` và chạy Eureka Server)
- API: `GET /api/v1/doctors` trả về danh sách bác sĩ gồm `id`, `name`, `specialization`
- API: `GET /api/v1/doctors/search?name=An` tìm bác sĩ theo tên, không phân biệt chữ hoa chữ thường. Rate Limiter cho phép 5 request trong 10 giây; request thứ 6 trả HTTP 429.
