output "my_ipv4" {
  value = local.my_ipv4
}

# output "my_ipv6" {
#   value = local.my_ipv6
# }

output "podman_server_ip" {
  value = aws_instance.podman_server.public_ip
}