
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }
    http = {
      source  = "hashicorp/http"
      version = "~> 3.0"
    }
    # helm = {
    #   source = "hashicorp/helm"
    #   version = "3.2.0"
    # }
    # kubernetes = {
    #   source = "hashicorp/kubernetes"
    #   version = "3.2.1"
    # }
  }
#   backend "s3" {
#     bucket         = "XXX"
#     key            = "dev/terraform.tfstate"
#     region         = "us-east-1"
#     encrypt        = true
#  }
}

provider "aws" {
  region = "us-east-1"
}

# provider "kubernetes" {
#     host                   = module.eks.cluster.endpoint
#     cluster_ca_certificate = base64decode(module.eks.cluster.certificate_authority[0].data)
#     # token                  = module.eks.cluster_auth.token
         
#     exec {
#       api_version = "client.authentication.k8s.io/v1"
#       command     = "aws"
#       args = [
#           "eks",
#           "get-token",
#           "--cluster-name",
#           module.eks.cluster.name,
#           "--region",
#           var.region
#         ]
#     }      
# }

# provider "helm" {
  
#   kubernetes = {
#     host                   = module.eks.cluster.endpoint
#     cluster_ca_certificate = base64decode(module.eks.cluster.certificate_authority[0].data)
#     # token                  = module.eks.cluster_auth.token
#     exec = {
#       api_version = "client.authentication.k8s.io/v1"
#       command     = "aws"
#       args = [
#           "eks",
#           "get-token",
#           "--cluster-name",
#           module.eks.cluster.name,
#           "--region",
#           var.region
#         ]
#     }
#   }
# }


