datacenter = "dc1"
data_dir = "/consul/data"
server = true             # ✅ CHỈ DÀNH CHO SERVER
bootstrap_expect = 1      # ✅ CHỈ DÀNH CHO SERVER
client_addr = "0.0.0.0"
# Thêm cấu hình ACL vào file này (nếu bạn chưa có):
acl {
  enabled = true
  default_policy = "deny"
  tokens {
    master = "f18cfb7d-5a36-2a24-7c15-d3316e450d07"
  }
}
ui_config {
  enabled = true
}