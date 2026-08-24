
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
  }
  backend "s3" {
    bucket         = "terraform-states-916371b2c66c"
    key            = "01/terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
 }
}

provider "aws" {
  region = "us-east-1"
}
