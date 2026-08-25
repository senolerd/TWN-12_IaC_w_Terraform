resource "aws_security_group" "deployment_SG" {
  region = var.region
  name   = "${var.project_name}_SG"
  vpc_id = aws_vpc.project_vpc.id
  tags = {
    "Name" = "${var.project_name}_SG"
  }
}

resource "aws_vpc_security_group_ingress_rule" "http-ingress" {
  security_group_id = aws_security_group.deployment_SG.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 8080
  to_port           = 8080
  ip_protocol       = "tcp"
  description       = "${var.project_name} http access from outside"
  tags = {
    Name = "http_access_for_yall"
  }
}

resource "aws_vpc_security_group_ingress_rule" "https-ingress" {
  security_group_id = aws_security_group.deployment_SG.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 443
  to_port           = 443
  ip_protocol       = "tcp"
  description       = "${var.project_name} https access from outside"
  tags = {
    Name = "https access_yall"
  }
}

resource "aws_vpc_security_group_ingress_rule" "ssh_ingress_ipv4" {
  security_group_id = aws_security_group.deployment_SG.id
  cidr_ipv4         = local.my_ipv4
  from_port         = 22
  to_port           = 22
  ip_protocol       = "tcp"
  description       = "${var.project_name} ssh access from terrafrom installer IPv4"
  tags = {
    Name = "my_ipv4"
  }
}

# resource "aws_vpc_security_group_ingress_rule" "ssh_ingress_ipv6" {
#   security_group_id = aws_security_group.deployment_SG.id
#   cidr_ipv6         = local.my_ipv6
#   from_port         = 22
#   to_port           = 22
#   ip_protocol       = "tcp"
#   description       = "${var.project_name} ssh access from terrafrom installer IPv6"
#   tags = {
#     Name = "my_ipv6"
#   }
# }

resource "aws_vpc_security_group_egress_rule" "name" {
  security_group_id = aws_security_group.deployment_SG.id
  cidr_ipv4 = "0.0.0.0/0"
  from_port = -1
  to_port = -1
  ip_protocol = -1
  description = "Allow all egress"
}


data "http" "myipv4" {
  url = "https://ipv4.icanhazip.com"
  retry {
    attempts = 2
    min_delay_ms = 1000
  }  
}
# data "http" "myipv6" {
#   url = "https://icanhazip.com"
#   retry {
#     attempts = 2
#     min_delay_ms = 1000
#   }
# }

locals {
  my_ipv4 = "${chomp(data.http.myipv4.response_body)}/32"
  # my_ipv6 = "${chomp(data.http.myipv6.response_body)}/128"
}
