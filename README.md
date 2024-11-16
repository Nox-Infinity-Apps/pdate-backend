# P-DATE

## I. Công nghệ sử dụng:
### 1. Core framework
- Backend Framework: Spring Boot
- GraphQL by DGS GraphQL
### 2. Caching

- Redis

### 3. Database & ORM

- Hibernate 


### 4. Third service/part 

- Firebase Admin SDK
- Stream SDK
- Cloudflare R2
- Mapbox API
- Sentry: Debug tool

### 5. Virtualization & Deployment

- Docker
- Nginx

## II. Cấu trúc thư mục

```
|-modules : Module chính chứa các thành phần chia theo Hexagonal
    |- adapters : Đầu ra/vào của luồng data tới app
        |- Primary: Đầu vào từ http request
    |- app : Thư mục chứa java file khởi chạy ứng dụng + các env cho app
    |- application : Service trên tầng application (Chứa service với tác vụ cụ thể cho 1 mutation/query)
        |- (...) : Các thư mục chia theo tính năng/BLOC
            |- I(...).java : Interface các phương thức của Service
            |- (...)Service : Implementation của service
    |-core: Logic nghiệp vụ và các thành phần chính của ứng dụng
        |-Domains : Logic nghiệp vụ phần theo chức năng
        |-Entities : Entity của database
        |-Scripts: Một số file java chứa function cần dùng
    |-infrastructure: Chứa các service hạ tầng + cấu hình repository để dùng cho domain
        |-Configurations: Chứa các class config 
        |-ThirdServices: Các dịch vụ bên thứ ba
        |-Repositories: Chứa các repository tạo từng database (raw, jpa)
|-deployment : Docker compose file và các cấu hình dành cho deploy
|-gradle : Cấu hình gradle
|-build.gradle : Cấu hình build gradle
|-Makefile : Make file scripts
|- Dockerfile : Docker image file
```


