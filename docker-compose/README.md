## Hướng dẫn Deploy ứng dụng
#### Requirement
- Docker running trên nền hệ điều hành với câu lệnh sau
  - ```docker --version```
  - ```docker compose version```
- Chạy Dockerfile của ứng dụng
- Tồn tại images của client và backend với câu lệnh sau
  - ```docker images```
#### Deploy
- Câu lệnh chạy hệ thống
```bash
docker compose up -d
```
- Câu lệnh rebuild ứng dụng
```
docker compose up -d --build {server-name}
```
- Câu lệnh dừng hệ thống
```bash
docker compose down
```
- Reset từng ứng dụng
```docker restart {container-name}```
- Lưu ý: Khi image có sự thay đổi về version với file compose.yaml, Cập nhật file compose.yaml với version mong muốn

#### Thao tác với ứng dụng
  - ```docker exec -it {container-name} sh``` # Truy cập ứng dụng
  - ```exit```                            # Thoát ứng dụng nhập câu lệnh
  - ```docker ps```                       # Kiểm tra ứng dụng đang hoạt động
  - ```docker ps -a```                    # Kiểm tra ứng dụng đang và ngừng hoạt động
  - ```docker logs -f {container-name}``` # Kiểm tra logs của ứng dụng, -f để theo dõi logs theo thời gian thực.
  - ```docker inspect {container-name}``` # Kiểm tra thông tin container
  - ```docker system prune -a```          # Xoá toàn bộ
  - ```docker stop {container-name}```    # Dừng container
  - ```docker rm {container-name}```      # Xóa container
  - ```docker rmi {image-name}```         # Xóa image Docker
  - ```docker compose stop {ten_service}``` # Dừng service hiện tại
  - ```docker compose up -d {ten_service}``` # Chạy service hiện tại