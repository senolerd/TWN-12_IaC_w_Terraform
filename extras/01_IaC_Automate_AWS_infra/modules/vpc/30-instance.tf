resource "aws_instance" "podman_server" {
    ami = data.aws_ami.ubuntu.id
    key_name = var.keypair_name
    instance_type = var.instance_type
    subnet_id = aws_subnet.public_subnets.id
    vpc_security_group_ids = [ aws_security_group.deployment_SG.id ]
    region = var.region
    tags = {
      Name = "Podman Server"
    }
    user_data = <<-EOF
        #!/bin/bash
        apt update -y
        apt install podman -y
        su - ubuntu -c "loginctl enable-linger"
        # su - ubuntu -c "podman run -d --name app_server -p 8080:80 docker.io/nginx"
        EOF
}

data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  owners = ["099720109477"] # Canonical
}
