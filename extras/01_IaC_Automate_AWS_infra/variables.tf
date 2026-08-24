
variable "region" { type = string }
variable "vpc_cidr" { type = string }
variable "project_name" { type = string }
variable "keypair_name" { type = string }

variable "instance_type" { 
  type = string
}

variable "subnets" {
  type = map(object({
    cidr      = string
    az        = string
  }))
}




