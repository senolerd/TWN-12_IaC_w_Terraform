# Update terraform.tfvars for new deployment environment 

module "vpc" {
  source              = "./modules/vpc"
  project_name        = var.project_name
  region              = var.region
  subnets             = var.subnets
  vpc_cidr            = var.vpc_cidr
  instance_type = var.instance_type
  keypair_name = var.keypair_name
}