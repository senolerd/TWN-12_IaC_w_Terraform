output "Environment" {
  value = "${var.environment}"
}

output "eks_cluster_name" {
  value = module.eks.cluster.name
}