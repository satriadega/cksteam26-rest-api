#!/bin/bash
set -e

# --- Config ---
DOCKERHUB_USERNAME="satriadega"
IMAGE_NAME="arsipku-backend"
SECRET_FILE="my_secret.txt"

# --- Build image with BuildKit and secret ---
export DOCKER_BUILDKIT=1
echo "--- Building Docker image ---"
docker build \
  --secret id=encryption_key,src=$SECRET_FILE \
  -t $DOCKERHUB_USERNAME/$IMAGE_NAME:latest .

# --- Push to Docker Hub ---
echo "--- Pushing Docker image to Docker Hub ---"
docker login
docker push $DOCKERHUB_USERNAME/$IMAGE_NAME:latest

echo "--- Build & Push complete. You can now run the container separately ---"
