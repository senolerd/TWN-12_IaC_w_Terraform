#### Does
- Installing VPC with security group for http access to all, ssh access to terrafom's IP
- EC2 instance and installing podman with user_data, then running a nginx server on it

#### Prerequisites
- Terraform CLI installed
- AWS credentials configured (e.g. via `aws configure` or environment variables) with permissions to manage VPC/EKS resources

#### Usage
```bash
terraform init
terraform plan  -var-file="terraform.tfvars"
terraform apply -var-file="terraform.tfvars"