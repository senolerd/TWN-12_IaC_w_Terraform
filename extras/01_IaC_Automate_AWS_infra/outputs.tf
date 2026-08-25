output "my_IPv4" {
  value = "${module.vpc.my_ipv4} added to SSH access"
}

# output "my_IPv6" {
#   value = "${module.vpc.my_ipv6} added to SSH access"
# }


output "service_addr" {
  value = "http://${module.vpc.podman_server_ip}:8080"
}

output "server_addr" {
  value = module.vpc.podman_server_ip
}