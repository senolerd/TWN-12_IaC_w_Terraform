variable "project_name" {
  type    = string
}

variable "region" {
  type = string
}

variable "vpc_cidr" {
  type    = string
}

variable "subnets" {
  type = map(object({
    cidr      = string
    az        = string
  }))
}

variable "instance_type" { 
  type = string
}

variable "keypair_name" { 
  type = string 
}









