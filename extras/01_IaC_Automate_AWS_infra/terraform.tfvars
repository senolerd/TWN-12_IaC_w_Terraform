project_name = "my-proj"
region       = "us-east-1"
vpc_cidr = "10.10.0.0/16"
subnets = {
  public = { cidr = "10.10.1.0/24", az = "us-east-1a"}
  private = { cidr = "10.10.2.0/24", az = "us-east-1a"}
}

instance_type = "t3.micro"
keypair_name = "senol-mac"